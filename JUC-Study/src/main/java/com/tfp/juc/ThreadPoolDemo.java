package com.tfp.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 12:41
 * Description:
 * 线程池 --- 3种大类型(但是一般都不使用，原因是：大量创建线程，可能会造成oom)
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {

        //一池5个工作线程，类似一个银行5个受理窗口
//        ExecutorService  executorPool=  Executors.newFixedThreadPool(5);
        //一池1个工作线程，类似一个银行1个受理窗口
//        ExecutorService  executorPool=  Executors.newSingleThreadExecutor();
        //一池N个工作线程，类似一个银行N个受理窗口
        ExecutorService  executorPool=  Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"受理...");
                });
//                  try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorPool.shutdown();
        }





    }
}
