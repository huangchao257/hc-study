package com.hc.auth;

import com.hc.auth.service.ApiAuthenticator;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2022/12/30 17:39
 */
public class ApiApplication {


    public static void main(String[] args) {
        String url = "xxx?appId=1&token=hc-hc&timestamp=" + System.currentTimeMillis();
        ApiAuthenticator apiAuthenticator = new ApiAuthenticator();
        apiAuthenticator.auth(url);
    }
}
