package com.softfn.dev.components.cache.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * RedisCacheServiceImpl 基于{@link RedisTemplate}缓存服务实现
 * <p/>
 *
 * @author softfn
 */
public class RedisCacheServiceImpl<K extends Serializable, V extends Serializable> implements CacheService<K, V> {

    private RedisTemplate redisTemplate;

    public Boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(K key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<K> keys) {
        redisTemplate.delete(keys);
    }

    public DataType type(K key) {
        return redisTemplate.type(key);
    }

    public Set<K> keys(K pattern) {
        return redisTemplate.keys(pattern);
    }

    public String randomKey() {
        // redisTemplate.randomKey();
        throw new UnsupportedOperationException("unsupported randomKey method");
    }

    public void rename(K oldKey, K newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    public Boolean renameIfAbsent(K oldKey, K newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expireAt(K key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    public Boolean move(K key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    public byte[] dump(K key) {
        return redisTemplate.dump(key);
    }

    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        redisTemplate.restore(key, value, timeToLive, unit);
    }

    public Long getExpire(K key) {
        return redisTemplate.getExpire(key);
    }

    public Long getExpire(K key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(K key, V value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Boolean setIfAbsent(K key, V value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public void multiSet(Map<? extends K, ? extends V> m) {
        redisTemplate.opsForValue().multiSet(m);
    }

    public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m) {
        return redisTemplate.opsForValue().multiSetIfAbsent(m);
    }

    public V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    public V getAndSet(K key, V value) {
        return (V) redisTemplate.opsForValue().getAndSet(key, value);
    }

    public List<V> multiGet(Collection<K> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public Long increment(K key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Double increment(K key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Integer append(K key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    public String get(K key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    public void set(K key, V value, long offset) {
        redisTemplate.opsForValue().set(key, offset);
    }

    public Long size(K key) {
        return redisTemplate.opsForValue().size(key);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
