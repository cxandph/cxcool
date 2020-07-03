package com.tfp.juc;

import java.util.concurrent.CountDownLatch;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 15:03
 * Description:让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒。
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * main主线程必须要等前面6个线程完成全部工作后，自己才能开干
 */
public class CountDownDemo {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch  countDownLatch  =  new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            final  int  n  = i  ;
                    new Thread(
                            () -> {
                                System.out.println(Thread.currentThread().getName() + ":第" + n + "个同学离开了教室...");
                                countDownLatch.countDown();
                                },
                            "线程"+String.valueOf(i))
                            .start();
                }
        countDownLatch.await();
        System.out.println("班长离开了教室...");

    }
}
