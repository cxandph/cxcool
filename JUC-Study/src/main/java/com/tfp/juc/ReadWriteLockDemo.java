package com.tfp.juc;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 15:56
 * Description:读写锁
 */

class  DefineCookie{

    private  volatile Map<String ,Object > cookies =  new ConcurrentHashMap<>();
    private  ReadWriteLock readWriteLock  =  new ReentrantReadWriteLock();
    public  Object get(String  str   ) {
        readWriteLock.readLock().lock();
        Object  res =null;
        try {
            System.out.println(Thread.currentThread().getName()+"正在读...");
            res  = cookies.get(str) ;
            System.out.println(Thread.currentThread().getName()+"读出结果:"+res);
            System.out.println(Thread.currentThread().getName()+"读完了...");
        } finally {
            readWriteLock.readLock().unlock();
        }

        return res;
    }

    public void set(String  str ,Object  o) {

        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"正在写...");
            TimeUnit.SECONDS.sleep(3);
            cookies.put(str,o);
            System.out.println(Thread.currentThread().getName()+"写完了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }
}
public class ReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        DefineCookie  defineCookie   = new DefineCookie();
        CountDownLatch  countDownLatch   = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            final  int   n  = i  ;
                    new Thread(
                            () -> {
                                defineCookie.set(String.valueOf(n), UUID.randomUUID().toString().substring(0,8));
                                countDownLatch.countDown();
                            },
                            "线程"+String.valueOf(i))
                            .start();
                }

        for (int i = 0; i < 6; i++) {
            final  int   n  = i  ;
            countDownLatch.await();
                    new Thread(
                            () -> {
                                defineCookie.get(String.valueOf(n));
                            },
                            "线程"+String.valueOf(i+6))
                            .start();
                }
    }

}
