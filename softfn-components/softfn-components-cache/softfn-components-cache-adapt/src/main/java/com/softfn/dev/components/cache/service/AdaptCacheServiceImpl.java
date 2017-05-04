package com.softfn.dev.components.cache.service;

import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.components.cache.service.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * AdaptCacheServiceImpl
 * <p/>
 *
 * @author softfn
 */
public class AdaptCacheServiceImpl<K extends Serializable, V extends Serializable> implements CacheService<K, V>, InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(AdaptCacheServiceImpl.class);

    private JdkSerializationRedisSerializer serializer;
    private CacheService<byte[], byte[]> remoteCacheService;

    public Boolean hasKey(K key) {
        return remoteCacheService.hasKey(serialize(key));
    }

    public void delete(K key) {
        remoteCacheService.delete(serialize(key));
    }

    public void delete(Collection<K> keys) {
        remoteCacheService.delete(serialize(keys));
    }

    public Set<K> keys(K pattern) {
        Set<byte[]> keys = remoteCacheService.keys(serialize(pattern));

        Set<K> keySet = new HashSet<K>();
        if (keys != null) {
            for (byte[] key : keys) {
                keySet.add(deserializeKey(key));
            }
        }
        return keySet;
    }

    public K randomKey() {
        return (K) UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
    }

    public void rename(K oldKey, K newKey) {
        remoteCacheService.rename(serialize(oldKey), serialize(newKey));
    }

    public Boolean renameIfAbsent(K oldKey, K newKey) {
        return remoteCacheService.renameIfAbsent(serialize(oldKey), serialize(newKey));
    }

    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return remoteCacheService.expire(serialize(key), timeout, unit);
    }

    public Boolean expireAt(K key, Date date) {
        return remoteCacheService.expireAt(serialize(key), date);
    }

    public Boolean move(K key, int dbIndex) {
        return remoteCacheService.move(serialize(key), dbIndex);
    }

    public byte[] dump(K key) {
        return remoteCacheService.dump(serialize(key));
    }

    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        remoteCacheService.restore(serialize(key), value, timeToLive, unit);
    }

    public Long getExpire(K key) {
        return remoteCacheService.getExpire(serialize(key));
    }

    public Long getExpire(K key, TimeUnit timeUnit) {
        return remoteCacheService.getExpire(serialize(key), timeUnit);
    }

    public void set(K key, V value) {
        remoteCacheService.set(serialize(key), serialize(value));
    }

    public void set(K key, V value, long timeout, TimeUnit unit) {
        remoteCacheService.set(serialize(key), serialize(value), timeout, unit);
    }

    public Boolean setIfAbsent(K key, V value) {
        return remoteCacheService.setIfAbsent(serialize(key), serialize(value));
    }

    public void multiSet(Map<? extends K, ? extends V> m) {
        remoteCacheService.multiSet(serialize(m));
    }

    public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m) {
        return remoteCacheService.multiSetIfAbsent(serialize(m));
    }

    public V get(K key) {
        return deserializeValue(remoteCacheService.get(serialize(key)));
    }

    public V getAndSet(K key, V value) {
        byte[] andSet = remoteCacheService.getAndSet(serialize(key), serialize(value));
        return deserializeValue(andSet);
    }

    public List<V> multiGet(Collection<K> keys) {
        List<V> list = new ArrayList<V>();
        if (keys != null && keys.size() > 0) {
            List<byte[]> values = remoteCacheService.multiGet(serialize(keys));
            if (values != null && values.size() > 0) {
                for (byte[] value : values) {
                    list.add(deserializeValue(value));
                }
            }
        }
        return list;
    }

    public Long increment(K key, long delta) {
        return remoteCacheService.increment(serialize(key), delta);
    }

    public Double increment(K key, double delta) {
        return remoteCacheService.increment(serialize(key), delta);
    }

    public Integer append(K key, String value) {
        return remoteCacheService.append(serialize(key), value);
    }

    public String get(K key, long start, long end) {
        return remoteCacheService.get(serialize(key), start, end);
    }

    public void set(K key, V value, long offset) {
        remoteCacheService.set(serialize(key), serialize(value), offset);
    }

    public Long size(K key) {
        return remoteCacheService.size(serialize(key));
    }

    private <T> byte[] serialize(T data) {
        long startTime = System.currentTimeMillis();
        try {
            return serializer.serialize(data);
        } catch (Exception e) {
            throw new SerializeException(ExceptionCode.EXCEPTION, "Object serialization exception", e);
        } finally {
            logger.info("Serialize cost ms：{}, Data:{}", System.currentTimeMillis() - startTime, data);
        }
    }

    private <K> List<byte[]> serialize(Collection<K> keys) {
        List<byte[]> keyList = new ArrayList<byte[]>();
        if (keys != null && keys.size() > 0) {
            for (K key : keys) {
                keyList.add(serialize(key));
            }
        }
        return keyList;
    }

    private Map<byte[], byte[]> serialize(Map<? extends K, ? extends V> m) {
        Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
        if (m != null) {
            for (K k : m.keySet()) {
                map.put(serialize(k), serialize(m.get(k)));
            }
        }
        return map;
    }

    private K deserializeKey(byte[] key) {
        return deserialize(key);
    }

    private V deserializeValue(byte[] value) {
        return deserialize(value);
    }

    private <T> T deserialize(byte[] value) {
        long startTime = System.currentTimeMillis();
        try {
            return (T) serializer.deserialize(value);
        } catch (Exception e) {
            throw new SerializeException(ExceptionCode.EXCEPTION, "Object deserialize exception", e);
        } finally {
            logger.info("Deserialize cost ms：{}", System.currentTimeMillis() - startTime);
        }
    }

    public void setRemoteCacheService(CacheService remoteCacheService) {
        this.remoteCacheService = remoteCacheService;
    }

    public void afterPropertiesSet() throws Exception {
        serializer = new JdkSerializationRedisSerializer();
    }
}
