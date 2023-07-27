package com.hc.auth.model;

import java.util.Map;

/**
 * 类描述: token令牌验证类
 *
 * @author: HuangChao
 * @since: 2022/12/30 14:58
 */
public class AuthToken {

    // 默认过期时长
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 60 * 1000;

    // token令牌
    private String token;

    // 创建时间
    private Long createTime;

    // 过期时间
    private Long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;

    public AuthToken(String token, Long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, Long createTime, Long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    /**
     * 描述: 把 URL、AppID、密码、时间戳拼接为一个字符串，对字符串通过加密算法加密生成 token
     *
     * @param baseUrl
     * @param createTime
     * @param param
     * @return {@link AuthToken}
     * @author: HuangChao
     * @since: 2022/12/30 15:10
     */
    public static AuthToken create(String baseUrl, long createTime, Map<String, String> param) {
        // 加密获取token
        return new AuthToken("hc-" + param.get("password"), createTime);
    }

    /**
     * 描述: 根据时间戳判断 token 是否过期失效；
     *
     * @param timestamp
     * @return {@link boolean}
     * @author: HuangChao
     * @since: 2022/12/30 15:10
     */
    public boolean isExpired(long timestamp) {
        return Math.abs(Math.subtractExact(timestamp, createTime)) > this.expiredTimeInterval;
    }

    /**
     * 描述: 验证两个 token 是否匹配
     *
     * @param authToken
     * @return {@link boolean}
     * @author: HuangChao
     * @since: 2022/12/30 15:09
     */
    public boolean matchToken(AuthToken authToken) {
        return this.token.equals(authToken.token);
    }

    public String getToken() {
        return token;
    }
}
