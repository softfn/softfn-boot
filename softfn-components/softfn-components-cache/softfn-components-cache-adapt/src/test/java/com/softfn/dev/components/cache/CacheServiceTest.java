package com.softfn.dev.components.cache;

import com.softfn.dev.components.cache.service.CacheService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * <p/>
 * CacheServiceTest
 * <p/>
 *
 * @author softfn
 */
public class CacheServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/cache-adapt.xml");
        CacheService cacheService = context.getBean("cacheService", CacheService.class);

        cacheService.set("k1", "v1");
        Serializable v1 = cacheService.get("k1");
        System.out.println(v1);

        cacheService.delete("k1");

        v1 = cacheService.get("k1");
        System.out.println(v1);
    }
}
