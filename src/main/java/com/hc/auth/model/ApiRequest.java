package com.hc.auth.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述: api请求参数实体
 *
 * @author: HuangChao
 * @since: 2022/12/30 14:58
 */
public class ApiRequest {

    /**
     * 基础请求url
     */
    private String baseUrl;

    /**
     * token
     */
    private String token;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 时间戳
     */
    private long timestamp;

    public ApiRequest(String baseUrl, String token, String appId, long timestamp) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    /**
     * 描述: 解析 URL，得到 token、AppID、时间戳等信息
     *
     * @param url
     * @return {@link ApiRequest}
     * @author: HuangChao
     * @since: 2022/12/30 15:17
     */
    public static ApiRequest creatFromFullUrl(String url) {
        // 可以考虑用正则

        String[] split = url.split("\\?");
        // 无参数返回空
        if (split.length == 1) {
            return null;
        }

        Map<String, String> paramMap = new HashMap<>();
        String[] params = split[1].split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }

        return new ApiRequest(url, paramMap.get("token"), paramMap.get("appId"), Long.parseLong(paramMap.get("timestamp")));
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
