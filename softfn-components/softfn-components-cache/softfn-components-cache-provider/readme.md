缓存提供者服务
=============================================================

一、生产环境
===========

1. Redis集群启动
---------------
    启动master节点  
    cd /usr/local/redis/redis-2.8.21/src
    ./redis-server  /data/redisdb/redis-master.conf
    ./redis-sentinel /data/redisdb/sentinel-master.conf
    
    启动slave节点  
    cd /usr/local/redis/redis-2.8.21/src
    ./redis-server  /data/redisdb/redis-slave.conf
    ./redis-sentinel /data/redisdb/sentinel-slave.conf


2. 部署服务器
-------------
    10.16.19.48  /apps/svr/softfn-components-cache-provider-* 3个节点
    10.16.19.49  /apps/svr/softfn-components-cache-provider-* 3个节点
     
    

二、测试环境
===========
 
    172.16.13.207 
    master-dev
    /usr/local/redis/src/redis-server /data/redis/redis_6379.conf &
    /usr/local/redis/src/redis-server /data/redis/redis_6380.conf &
    /usr/local/redis/src/redis-sentinel /data/redis/sentinel_26379.conf &
    
    master-test
    /usr/local/redis/src/redis-server /data/redis/redis_7379.conf &
    /usr/local/redis/src/redis-server /data/redis/redis_7380.conf &
    /usr/local/redis/src/redis-sentinel /data/redis/sentinel_27379.conf &
    
    