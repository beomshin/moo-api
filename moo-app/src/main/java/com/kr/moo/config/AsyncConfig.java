package com.kr.moo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    private final int corePoolSize = 5; // 기본 쓰레드 수
    private final int maxPoolSize = 30; // 최대로 만들어지는 쓰레드 수
    private final int queueCapacity = 50; // Queue 사이즈

    @Override
    public Executor getAsyncExecutor() {
        log.info("▶ [Async Executor] 설정: corePoolSize [{}], maxPoolSize [{}], queueCapacity [{}]", corePoolSize, maxPoolSize, queueCapacity);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("moo-async-");
        executor.initialize();
        return executor;
    }
}
