package com.hc.api.yinshi;

import com.hc.api.compreface.CompreFaceClient;
import com.hc.api.compreface.model.RecognizeDRO;
import com.hc.api.compreface.model.RecognizeRespDRO;
import com.hc.api.yinshi.model.YsAlarmMsg;
import com.hc.db.mapper.UserFaceRecognitionRecordMapper;
import com.hc.db.mapper.YsAlarmRecordMapper;
import com.hc.db.model.UserFaceRecognitionRecordEntity;
import com.hc.db.model.YsAlarmRecordEntity;
import com.hc.util.LocalDateTimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 类描述: 萤石预警队列处理
 *
 * @author HuangChao
 * @since 2025/11/13
 */
@Slf4j
@Component
public class YsAlarmQueueProcessor {

    @Resource
    private CompreFaceClient compreFaceClient;
    @Resource
    private UserFaceRecognitionRecordMapper recognitionRecordMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private YsAlarmRecordMapper ysAlarmRecordMapper;

    private final BlockingQueue<YsAlarmMsg> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public YsAlarmQueueProcessor() {
        startProcessing();
    }

    public void addTask(YsAlarmMsg task) {
        try {
            queue.offer(task);
        } catch (Exception e) {
            log.error("添加失败", e);
        }
    }

    private void startProcessing() {
        executorService.submit(this::processTasks);
    }

    private void processTasks() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(200L);
                YsAlarmMsg msg = queue.take();
                for (YsAlarmMsg.PictureInfo pictureInfo : msg.getPictureList()) {
                    // 下载图片到 项目 file/ys 目录下
                    String localImagePath = ""; // 用于保存本地图片路径
                    String imageUrl = pictureInfo.getUrl();
                    try {
                        // 构建目标文件路径
                        String fileName = UUID.randomUUID() + ".jpg"; // 可根据实际格式调整扩展名
                        Path targetPath = Paths.get("file/ys1", fileName);

                        // 创建父目录（如果不存在）
                        if (!Files.exists(targetPath.getParent())) {
                            Files.createDirectories(targetPath.getParent());
                        }

                        byte[] imageBytes = downloadImageWithHttpURLConnection(imageUrl);
                        // 检查数据大小
                        if (imageBytes.length > 1024) {  // 大于1KB才认为有效
                            Files.write(targetPath, imageBytes);
                            localImagePath = targetPath.toString();
                            log.info("图片已成功下载至: {}, 大小: {} bytes", targetPath.toAbsolutePath(), imageBytes.length);
                        } else {
                            log.warn("下载的图片数据过小 ({}) bytes，可能无效: {}", imageBytes.length, imageUrl);
                        }
                    } catch (Exception e) {
                        log.error("下载图片失败: {}", imageUrl, e);
                    }
                    // 数据落库
                    YsAlarmRecordEntity entity = new YsAlarmRecordEntity();
                    entity.setAlarmTime(LocalDateTimeUtil.convertLdtToDate(LocalDateTime.parse(msg.getAlarmTime())));
                    entity.setDevSerial(msg.getDevSerial());
                    entity.setAlarmType(msg.getAlarmType());
                    entity.setImgPath(localImagePath); // 保存图片路径
                    entity.setImgUrl(pictureInfo.getUrl());
                    ysAlarmRecordMapper.insert(entity);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("处理图片下载任务异常", e);
            }
        }
    }

    private byte[] downloadImageWithHttpURLConnection(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return outputStream.toByteArray();
            }
        } else {
            throw new IOException("HTTP响应码: " + responseCode);
        }
    }


    /**
     * 描述: compreFace 人脸识别处理
     *
     * @param ysAlarmMsg 告警信息
     * @author HuangChao
     * @since 2025/11/13
     */
    private void compreFaceHandle(YsAlarmMsg ysAlarmMsg) throws Exception {
        for (YsAlarmMsg.PictureInfo pictureInfo : ysAlarmMsg.getPictureList()) {
            //  调用模型接口
            RecognizeRespDRO recognize = compreFaceClient.recognize(pictureInfo.getUrl(), "0.5");

            List<UserFaceRecognitionRecordEntity> entityList = new ArrayList<>();

            UserFaceRecognitionRecordEntity baseEntity = new UserFaceRecognitionRecordEntity();
            baseEntity.setAlarmTime(LocalDateTimeUtil.convertLdtToDate(LocalDateTime.parse(ysAlarmMsg.getAlarmTime())));
            baseEntity.setDevSerial(ysAlarmMsg.getDevSerial());
            baseEntity.setImgUrl(pictureInfo.getUrl());
            baseEntity.setChannelName(ysAlarmMsg.getChannelName());
            baseEntity.setSubject("");
            baseEntity.setProbability(BigDecimal.ZERO);
            if (Objects.nonNull(recognize) && !CollectionUtils.isEmpty(recognize.getResult())) {
                for (RecognizeDRO recognizeDRO : recognize.getResult()) {
                    for (RecognizeDRO.RecognizeSubjectDRO subject : recognizeDRO.getSubjects()) {
                        UserFaceRecognitionRecordEntity entity = new UserFaceRecognitionRecordEntity();
                        BeanUtils.copyProperties(baseEntity, entity);
                        entityList.add(entity);
                        entity.setSubject(subject.getSubject());
                        entity.setProbability(new BigDecimal(subject.getSimilarity()));
                    }
                }
            } else {
                entityList.add(baseEntity);
            }
            // 保存处理结构
            recognitionRecordMapper.insertBatch(entityList);
        }
    }
}
