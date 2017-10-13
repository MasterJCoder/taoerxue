package com.taoerxue.common.cache.redis;

import com.taoerxue.util.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by lizhihui on 2017-02-19 12:57.
 */
public class JedisSingleClient implements JedisClient {
    private RedisTemplate<String, Object> redisTemplate;

    public void hset(String key, String field, Object object) {
        redisTemplate.opsForHash().put(key, field, JsonUtils.objectToJson(object));
    }

    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setAndExpire(String key, String value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public long increment(String key, long count) {
        return redisTemplate.opsForValue().increment(key, count);
    }

    @Override
    public void expire(String key,long time,TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
