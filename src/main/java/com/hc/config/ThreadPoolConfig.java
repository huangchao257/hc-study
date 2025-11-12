package com.hc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    /**
     * 核心线程数
     */
    private final static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程数
     */
    private final static int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 最大等待队列
     */
    private final static int MAX_WORK_QUEUE_SIZE = 1024;
    /**
     * 线程名称
     */
    public final static String THREAD_POOL_NAME = "HC-POOL";


    @Bean(THREAD_POOL_NAME)
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(MAX_WORK_QUEUE_SIZE);
        executor.setThreadNamePrefix(THREAD_POOL_NAME + "-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}