package org.ssp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class SspConfiguration {

    @Value("${ssp.timer.maximumPoolSize:100}")
    private Integer maxPoolSize;

    @Bean
    public ExecutorService cachedExecutor() {
        return new
                ThreadPoolExecutor(0, maxPoolSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }
}
