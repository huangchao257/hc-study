package com.hc.auth.service;

import com.hc.auth.model.ApiRequest;
import com.hc.auth.model.AuthToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 类描述: api鉴权实现
 *
 * @author: HuangChao
 * @since: 2022/12/30 16:46
 */
@Service
public class ApiAuthenticator {

//    @Resource
    private CredentialStorageService credentialStorageService = new CredentialStorageService();

    public void auth(String url) {
        this.auth(ApiRequest.creatFromFullUrl(url));
    }

    public void auth(ApiRequest apiRequest) {
        if (Objects.isNull(apiRequest)) {
            throw new RuntimeException("鉴权失败");
        }
        Map<String, String> params = new HashMap<>();
        params.put("appId", apiRequest.getAppId());
        params.put("token", apiRequest.getToken());
        params.put("timestamp", String.valueOf(apiRequest.getTimestamp()));
        String pwd = credentialStorageService.getPasswordByAppId(apiRequest.getAppId());
        params.put("password", pwd);
        // 正确令牌
        AuthToken authToken = AuthToken.create(apiRequest.getBaseUrl(), System.currentTimeMillis(), params);
        // 参数令牌
        AuthToken paramToken = new AuthToken(apiRequest.getToken(), apiRequest.getTimestamp());
        if (authToken.isExpired(apiRequest.getTimestamp()) || !authToken.matchToken(paramToken)) {
            throw new RuntimeException("鉴权失败");
        }
    }
}
