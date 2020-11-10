package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.product.service.TestServcie;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TestServiceImpl implements TestServcie {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    @Override
    public void testLock() {
        // 创建锁：
        String skuId = "25";
        String locKey = "lock:" + skuId;
        // 锁的是每个商品
        RLock lock = redissonClient.getLock(locKey);
        // 开始加锁
        lock.lock();
        // 业务逻辑代码 // 获取数据
        String value = redisTemplate.opsForValue().get("num");
        if (StringUtils.isEmpty(value)){
            return;
        }
        int num = Integer.parseInt(value);
        redisTemplate.opsForValue().set("num",String.valueOf(++num));
        // 解锁：
        lock.unlock();
    }

    @Override
    public String readLock() {
        // 初始化读写锁
        RReadWriteLock readwriteLock = redissonClient.getReadWriteLock("readwriteLock");
        //获取读锁
        RLock rLock = readwriteLock.readLock();
        rLock.lock(10,TimeUnit.SECONDS);  //加10s锁
        String msg = this.redisTemplate.opsForValue().get("msg");
        //rLock.unlock(); // 解锁
        return msg;
    }

    @Override
    public String writeLock() {
        RReadWriteLock readwriteLock = redissonClient.getReadWriteLock("readwriteLock");
        RLock rLock = readwriteLock.writeLock();
        rLock.lock(10,TimeUnit.SECONDS);
        this.redisTemplate.opsForValue().set("msg",UUID.randomUUID().toString());
        //rLock.unlock(); // 解锁
        return "成功写入了内容。。。。。。";
    }

//    @Override
//    public void testLock() {
//        // 声明一个uuid ,将做为一个value 放入我们的key所对应的值中
//        String uuid = UUID.randomUUID().toString();
//        // 定义一个锁：lua 脚本可以使用同一把锁，来实现删除！
//        String skuId = "25";   // 访问skuId 为25号的商品 100008348542
//        String locKey = "lock:" + skuId;   // 锁住的是每个商品的数据
//
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent(locKey, uuid, 3, TimeUnit.SECONDS);
//        if (lock){
//            // 执行的业务逻辑开始
//            // 获取缓存中的num 数据
//            String value = redisTemplate.opsForValue().get("num");
//            // 如果是空直接返回
//            if (StringUtils.isEmpty(value)){
//                return;
//            }
//            // 不是空 如果说在这出现了异常！ 那么delete 就删除失败！ 也就是说锁永远存在！
//            int num = Integer.parseInt(value);
//            // 使num 每次+1 放入缓存
//            redisTemplate.opsForValue().set("num",String.valueOf(++num));
//            /*使用lua脚本来锁*/
//            // 定义lua 脚本
//            String script="if redis.call('get', KEYS[1]) == ARGV[1] " +
//                    "then return redis.call('del', KEYS[1]) else return 0 end";
//            // 使用redis执行lua执行
//            // 第一种传值
//            // DefaultRedisScript<Object> redisScript = new DefaultRedisScript<>(script);
//            //第二种传值
//            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
//            redisScript.setScriptText(script);
//            // 设置一下返回值类型 为Long
//            // 因为删除判断的时候，返回的0,给其封装为数据类型。
//            // 如果不封装那么默认返回String 类型，那么返回字符串与0 会有发生错误。
//            redisScript.setResultType(Long.class);
//            // 第一个要是script 脚本 ，第二个需要判断的key，第三个就是key所对应的值。
//            redisTemplate.execute(redisScript, Arrays.asList(locKey),uuid);
//        }else {
//            // 其他线程等待
//            try {
//                Thread.sleep(1000);
//                // 睡醒了之后，调用方法
//                testLock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }


//    @Override
//    public void testLock() {
//        // 1. 从redis中获取锁,setnx
//        Boolean lock = this.redisTemplate.opsForValue().setIfAbsent("lock", "111");
//        if (lock){
//            // 查询redis中的num值
//            String value = (String)this.redisTemplate.opsForValue().get("num");
//            // 没有该值return
//            if (StringUtils.isEmpty(value)){
//                return;
//            }
//            // 有值就转成成int
//            int num = Integer.parseInt(value);
//            // 把redis中的num值+1
//            this.redisTemplate.opsForValue().set("num",String.valueOf(++num));
//            // 2. 释放锁 del
//            this.redisTemplate.delete("lock");
//        }else {
//            // 3. 每隔1秒钟回调一次，再次尝试获取锁
//            try {
//                Thread.sleep(100);
//                testLock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    @Override
//    public synchronized void testLock() {
//        // 查询redis中的num值
//        String value = (String) this.redisTemplate.opsForValue().get("num");
//        // 没有该值return
//        if (StringUtils.isEmpty(value)){
//            return;
//        }
//        // 有值就转成成int
//        int num = Integer.parseInt(value);
//        // 把redis中的num值+1
//        this.redisTemplate.opsForValue().set("num",String.valueOf(++num));
//    }
}
