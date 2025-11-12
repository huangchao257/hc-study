package com.hc.api.compreface;

import com.hc.api.compreface.model.AddFaceDRO;
import com.hc.api.compreface.model.RecognizeRespDRO;
import jakarta.annotation.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述: CompreFace客户端
 *
 * @author HuangChao
 * @since 2025/11/10
 */
@Component
public class CompreFaceClient {

    @Resource
    private RestTemplate restTemplate;

    public RecognizeRespDRO recognize(String imageUrl, String threshold) throws Exception{
        String url = CompreFaceConsts.COMPRE_FACE_RECOGNITION_RECOGNIZE_PATH;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("det_prob_threshold", threshold);


        // 下载网络图片并转换为字节数组
        byte[] imageBytes = downloadImageFromUrl(imageUrl);

        // 创建 multipart 请求体
        LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file", new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "face-image.jpg";
            }
        });

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("x-api-key", CompreFaceConsts.COMPRE_FACE_API_KEY);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<RecognizeRespDRO> responseEntity = restTemplate.postForEntity(builder.toUriString(), entity, RecognizeRespDRO.class);
        return responseEntity.getBody();

    }

    public String addSubject(String subjectName) {
        String url = CompreFaceConsts.COMPRE_FACE_RECOGNITION_SUBJECTS_PATH;

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", CompreFaceConsts.COMPRE_FACE_API_KEY);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("subject", subjectName);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(url, entity, String.class);
    }
    public String deleteAllSubjects() {
        String url = CompreFaceConsts.COMPRE_FACE_RECOGNITION_SUBJECTS_PATH;

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", CompreFaceConsts.COMPRE_FACE_API_KEY);

        // 创建HttpEntity
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // 使用exchange方法并返回String类型
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        return response.getBody();
    }


    public AddFaceDRO addFace(String subjectName, String imageUrl) {
        try {
            // 构建URL和查询参数
            String url = CompreFaceConsts.COMPRE_FACE_RECOGNITION_SUBJECTS_FACES_PATH;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("subject", subjectName);

            // 下载网络图片并转换为字节数组
            byte[] imageBytes = downloadImageFromUrl(imageUrl);

            // 创建 multipart 请求体
            LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("file", new ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() {
                    return "face-image.jpg";
                }
            });

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("x-api-key", CompreFaceConsts.COMPRE_FACE_API_KEY);

            HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            // 发送请求
            return restTemplate.postForEntity(builder.toUriString(), entity, AddFaceDRO.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException("添加人脸失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从URL下载图片
     *
     * @param imageUrl 图片URL
     * @return 图片字节数组
     * @throws Exception 下载异常
     */
    private byte[] downloadImageFromUrl(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

}
