package com.hc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 类描述: 项目启动类
 *
 * @author: HuangChao
 * @since: 2023/07/28 10:22
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.hc.db.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}