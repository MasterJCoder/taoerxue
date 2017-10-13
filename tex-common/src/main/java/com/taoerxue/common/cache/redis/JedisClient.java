package com.taoerxue.common.cache.redis;

import java.util.concurrent.TimeUnit;

/**
 * Created by lizhihui on 2017-02-19 12:16.
 */
public interface JedisClient {


    void hset(String key, String field, Object object);

    String hget(String key, String field);

    void hdel(String key, String field);

    void set(String key, String value);

    String get(String key);

    void del(String s);

    void setAndExpire(String key, String value, long time, TimeUnit timeUnit);

    long increment(String key,long count);

    void expire(String key,long time,TimeUnit timeUnit);
}
