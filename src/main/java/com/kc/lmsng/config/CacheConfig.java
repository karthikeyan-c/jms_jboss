package com.kc.lmsng.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CacheConfig {
        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            Cache acctCache = new ConcurrentMapCache("acctmap");
            Cache fxCache = new ConcurrentMapCache("fxmap");
            cacheManager.setCaches(Arrays.asList(acctCache,fxCache));
            return cacheManager;
        }
}
