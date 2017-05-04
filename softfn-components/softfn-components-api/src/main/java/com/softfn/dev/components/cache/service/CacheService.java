package com.softfn.dev.components.cache.service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * CacheService 缓存服务接口
 * <p/>
 *
 * @author softfn
 */
public interface CacheService<K extends Serializable, V extends Serializable> {
    Boolean hasKey(K key);

    void delete(K key);

    void delete(Collection<K> keys);

//    DataType type(K key);

    Set<K> keys(K pattern);

//    String randomKey();

    void rename(K oldKey, K newKey);

    Boolean renameIfAbsent(K oldKey, K newKey);

    Boolean expire(K key, long timeout, TimeUnit unit);

    Boolean expireAt(K key, Date date);

    Boolean move(K key, int dbIndex);

    byte[] dump(K key);

    void restore(K key, byte[] value, long timeToLive, TimeUnit unit);

    Long getExpire(K key);

    Long getExpire(K key, TimeUnit timeUnit);

//    void watch(K key);
//
//    void watch(Collection<K> keys);
//
//    void unwatch();
//
//    void multi();
//
//    void discard();

//    List<Object> exec();

    void set(K key, V value);

    void set(K key, V value, long timeout, TimeUnit unit);

    Boolean setIfAbsent(K key, V value);

    void multiSet(Map<? extends K, ? extends V> m);

    Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m);

    V get(K key);

    V getAndSet(K key, V value);

    List<V> multiGet(Collection<K> keys);

    Long increment(K key, long delta);

    Double increment(K key, double delta);

    Integer append(K key, String value);

    String get(K key, long start, long end);

    void set(K key, V value, long offset);

    Long size(K key);

}
