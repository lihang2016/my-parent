package com.my.biz.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.UnknownHostException;

/**
 * z77z
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.expire}")
    private Long expire;
//    @Value("${cache.guavaCache.hotelPosition.maxSize}")
//    private long hotelPositionMaxSize;
//    @Value("${cache.guavaCache.hotelPosition.duration}")
//    private long hotelPositionDuration;

    //使用内置缓存
//    private GuavaCache buildHotelPositionCache() {
//        return new GuavaCache("hotel_position",
//                CacheBuilder.newBuilder()
//                        .recordStats()
//                        .maximumSize(11)
//                        .expireAfterWrite(100, TimeUnit.DAYS)
//                        .build());
//    }
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new DefaultKeySerializer());
        return template;
    }
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        rcm.setDefaultExpiration(expire);//秒
        return rcm;
    }
}
