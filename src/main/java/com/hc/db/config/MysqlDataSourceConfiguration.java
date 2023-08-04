//package com.hc.db.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * 类描述: mysql 数据源配置
// *
// * @author: HuangChao
// * @since: 2023/08/04 17:20
// */
//@Configuration
//public class MysqlDataSourceConfiguration {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.local.study")
//    public DataSourceProperties localStudyDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("localStudy")
//    @Primary
//    @Qualifier("localStudy")
//    public HikariDataSource localStudyDataSource() {
//        return localStudyDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.local.work")
//    public DataSourceProperties localWorkDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("localWork")
//    @Primary
//    @Qualifier("localWork")
//    public HikariDataSource localWorkDataSource() {
//        return localWorkDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.zmn.test3")
//    public DataSourceProperties zmnTest3DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("zmnTest3")
//    @Primary
//    @Qualifier("zmnTest3")
//    public HikariDataSource zmnTest3DataSource() {
//        return zmnTest3DataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.zmn.prod")
//    public DataSourceProperties zmnProdDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("zmnProd")
//    @Primary
//    @Qualifier("zmnProd")
//    public HikariDataSource zmnProdDataSource() {
//        return zmnProdDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//
//}
