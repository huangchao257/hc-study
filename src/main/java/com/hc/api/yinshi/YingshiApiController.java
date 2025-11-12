package com.hc.api.yinshi;

import com.alibaba.fastjson2.JSON;
import com.hc.api.compreface.CompreFaceClient;
import com.hc.api.compreface.model.RecognizeDRO;
import com.hc.api.compreface.model.RecognizeRespDRO;
import com.hc.api.yinshi.model.WebhookMessage;
import com.hc.api.yinshi.model.YsAlarmMsg;
import com.hc.config.ThreadPoolConfig;
import com.hc.db.mapper.UserFaceRecognitionRecordMapper;
import com.hc.db.model.UserFaceRecognitionRecordEntity;
import com.hc.util.LocalDateTimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * 类描述: 萤石Api控制层
 *
 * @author: HuangChao
 * @since: 2025/03/04
 */
@Slf4j
@RestController
@RequestMapping("/yingshi")
public class YingshiApiController {


    @Resource(name = ThreadPoolConfig.THREAD_POOL_NAME)
    private Executor executor;
    @Resource
    private CompreFaceClient compreFaceClient;
    @Resource
    private UserFaceRecognitionRecordMapper recognitionRecordMapper;
    @Resource
    private RestTemplate restTemplate;


    private static final List<String> DEVICE_SERIAL_LIST = Arrays.asList("K11309194", "K32415959", "F13801651", "K95874882", "K85701832", "K75917525", "K95875414", "K85701757", "K95874914", "G89302978", "BC0599057", "BB3594501", "BA1245154", "G49618157", "L42063996", "BC3266731", "BC6058457", "BC4184013", "K75917539", "K75916694", "K95875031", "L42468297", "L47332869", "G14026632", "K75916195", "L47332513", "BF0222717", "BF0222691", "BC4779901", "BF3768768", "BF3768279", "BF3768872");

    @RequestMapping(value = "/webhook")
    public ResponseEntity<String> webhook(@RequestHeader HttpHeaders header, @RequestBody String body) {
        WebhookMessage receiveMessage = null;
        log.info("消息获取时间:{}, 请求头:{},请求体:{}", System.currentTimeMillis(), JSON.toJSONString(header), body);
        try {
            receiveMessage = JSON.parseObject(body, WebhookMessage.class);
            //todo:对收到的消息进行处理,最好发送到其他中间件,或者写到数据库中,不要影响回调地址的处理

            WebhookMessage finalReceiveMessage = receiveMessage;
            executor.execute(() -> {
                handleAlarm(finalReceiveMessage);
            });

        } catch (Exception e) {
            log.error("消息获取时间:{}, 请求头:{},请求体:{}", System.currentTimeMillis(), JSON.toJSONString(header), body, e);
        }
        //必须进行返回
        Map<String, String> result = new HashMap<>(1);
        assert receiveMessage != null;
        String messageId = receiveMessage.getHeader().getMessageId();
        result.put("messageId", messageId);
        final ResponseEntity<String> resp = ResponseEntity.ok(JSON.toJSONString(result));
        log.info("返回的信息:{}", JSON.toJSONString(result));
        return resp;
    }

    private void handleAlarm(WebhookMessage finalReceiveMessage) {
        try {
            // 非告警信息不处理
            if (!Objects.equals(YingShiConsts.MSG_TYPE_ALARM, finalReceiveMessage.getHeader().getType())) {
                log.info("非告警信息:{}", finalReceiveMessage.getHeader().getType());
                return;
            }

            // 识别是否有效序列号
            if (!DEVICE_SERIAL_LIST.contains(finalReceiveMessage.getHeader().getDeviceId())) {
                log.info("无效的序列号:{}", finalReceiveMessage.getHeader().getDeviceId());
                return;
            }

            // 解析body字段
            YsAlarmMsg ysAlarmMsg = JSON.parseObject(finalReceiveMessage.getBody(), YsAlarmMsg.class);
            if (!YingShiConsts.ALARM_TYPE_LIST.contains(ysAlarmMsg.getAlarmType())) {
                log.info("无效的告警类型:{}", ysAlarmMsg.getAlarmType());
                return;
            }

            if (CollectionUtils.isEmpty(ysAlarmMsg.getPictureList())) {
                return;
            }
            log.info("识别有效信息:{}", JSON.toJSONString(ysAlarmMsg));
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

        } catch (Exception e) {
            log.error("处理告警信息异常", e);
        }
    }

    @GetMapping("/init-device-set")
    public String initDeviceSet() {
        for (String deviceSerial : DEVICE_SERIAL_LIST) {

            try {
                String url = YingShiConsts.DEFENCE_SET;
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("accessToken", "at.7o949rjmb9e2t4wtbpkrrkqb568qa7th-7eh7e3qrq6-1vcerle-gs5lewdk0")
                        .queryParam("deviceSerial", deviceSerial)
                        .queryParam("isDefence", 1)
                        ;

                // 设置请求头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                // 创建HttpEntity
                HttpEntity<?> entity = new HttpEntity<>(headers);

                ResponseEntity<String> responseEntity = restTemplate.postForEntity(builder.toUriString(), entity, String.class);
                System.out.println(responseEntity.getBody());
            } catch (Exception e) {
                log.error("设备[{}]开通不放异常", deviceSerial, e);
            }
        }
        return "success";
    }
}
