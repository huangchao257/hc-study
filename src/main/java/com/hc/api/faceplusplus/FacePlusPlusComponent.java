package com.hc.api.faceplusplus;

import jakarta.annotation.Resource;
import org.apache.poi.util.StringUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 类描述: 旷视 face++ 接口调用
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
@Component
public class FacePlusPlusComponent {
//    @NacosValue(value = "${megvii.face.plus.plus.app.key:YsA2C6NmbYPXn4xqTdo9DlEgdRJrj8Mb}", autoRefreshed = true)
    public String appKey;

//    @NacosValue(value = "${megvii.face.plus.plus.app.secret:WYk31nXPAOBoySttRQMGpFvBMjUbytUN}", autoRefreshed = true)
    public String appSecret;

    @Resource
    private RestTemplate restTemplate;

    public Map createFaceSet(String outerId, String displayName) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("api_key", appKey);
        paramMap.add("api_secret", appSecret);
        paramMap.add("outer_id", outerId);
        paramMap.add("display_name", displayName);
        return restTemplate.postForObject(FacePlusPlusApiConsts.CREATE_FACE_SET_URL, paramMap, Map.class);
    }

    public Map deleteFaceSet(String faceSetToken) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("api_key", appKey);
        paramMap.add("api_secret", appSecret);
        paramMap.add("faceset_token", faceSetToken);
        return restTemplate.postForObject(FacePlusPlusApiConsts.DELETE_FACE_SET_URL, paramMap, Map.class);
    }

    public Map addFace(String outerId, String faceSetToken, String faceTokens) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("api_key", appKey);
        paramMap.add("api_secret", appSecret);
        if (StringUtil.isNotBlank(outerId)) {
            paramMap.add("outer_id", outerId);
        }
        paramMap.add("face_set_token", faceSetToken);
        paramMap.add("face_tokens", faceTokens);
        return restTemplate.postForObject(FacePlusPlusApiConsts.ADD_FACE_URL, paramMap, Map.class);
    }

    public Map detect(File file) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("api_key", appKey);
        body.add("api_secret", appSecret);
        body.add("image_file", new FileSystemResource(file));

        // 创建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        //发送请求
        ResponseEntity<Map> response = restTemplate.exchange(
                FacePlusPlusApiConsts.DETECT_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class);
        return response.getBody();
    }


    public Map search(String outerId, String faceSetToken, String faceToken) {

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建请求体
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("api_key", appKey);
        paramMap.add("api_secret", appSecret);
        paramMap.add("outer_id", outerId);
        paramMap.add("face_set_token", faceSetToken);
        paramMap.add("face_token", faceToken);
//        paramMap.add("return_result_count", 5);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(paramMap, headers);

        //发送请求
        ResponseEntity<Map> response = restTemplate.exchange(
                FacePlusPlusApiConsts.SEARCH_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class);
        return response.getBody();
    }

    public Map getFaceSetDetail(String outerId, String faceSetToken) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("api_key", appKey);
        paramMap.add("api_secret", appSecret);
        if (StringUtil.isNotBlank(outerId)) {
            paramMap.add("outer_id", outerId);
        }
        paramMap.add("face_set_token", faceSetToken);
        return restTemplate.postForObject(FacePlusPlusApiConsts.FACE_GET_DETAIL_URL, paramMap, Map.class);
    }

}
