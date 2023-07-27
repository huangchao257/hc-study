package com.hc.auth.service;

import org.springframework.stereotype.Service;

/**
 * 类描述: 获取密钥配置（数据库/配置文件等方式均可）
 *
 * @author: HuangChao
 * @since: 2022/12/30 14:59
 */
@Service
public class CredentialStorageService {

    public String getPasswordByAppId(String appId) {
        return "hc";
    }
}
