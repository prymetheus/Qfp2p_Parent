package com.qf.service;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class JedisUtiils {
    private static String url="39.107.94.76";
    private static String auth="Lonelyreader0";
    private static Jedis jedis;
    static {
        jedis=new Jedis(url,6379);
        System.out.println(jedis.isConnected());
        jedis.auth(auth);
    }
    //设置内容
    public static boolean set(String key,String value){
        jedis.set(key,value);
        jedis.expire(key,30*60);
        return true;
    }
    //设置有效期
    public static void setTime(String key){
        jedis.expire(key,1800);
    }
    //获取内容
    public static String get(String key){
        return jedis.get(key);
    }
    //删除指定的key
    public static void remove(String key){
        jedis.del(key);
    }
    public static void main(String[] args) {
       Jedis jedis=new Jedis(url,6379);
        System.out.println(jedis.isConnected());
        jedis.auth(auth);
    }

}
