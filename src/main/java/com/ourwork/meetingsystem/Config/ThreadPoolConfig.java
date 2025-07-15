package com.ourwork.meetingsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
    @Bean(name = "MyPool")
    public ThreadPoolTaskExecutor TaskExecutor1(){
        ThreadPoolTaskExecutor MyPool = new ThreadPoolTaskExecutor();
        MyPool.setCorePoolSize(10);//设置核心线程数
        MyPool.setMaxPoolSize(20);//设置最大线程数
        MyPool.setQueueCapacity(100);//设置队列容量
        MyPool.setKeepAliveSeconds(30);//设置线程空闲时间
        MyPool.setThreadNamePrefix("MyTask-");//设置线程名称前缀
        MyPool.initialize();
        return MyPool;
    }
}
