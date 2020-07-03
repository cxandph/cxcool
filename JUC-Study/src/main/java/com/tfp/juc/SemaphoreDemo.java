package com.tfp.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 15:37
 * Description:
 *  在信号量上我们定义两种操作：
 *  acquire（获取） 当一个线程调用acquire操作时，
 *  它要么通过成功获取信号量（信号量减1），
 *  要么一直等下去，直到有线程释放信号量，或超时。
 *  release（释放）
 *  实际上会将信号量的值加1，然后唤醒等待的线程。
 *  信号量主要用于两个目的:
 *  一个是用于多个共享资源的互斥使用，
 *  另一个用于并发线程数的控制。
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore  semaphore  =  new Semaphore(3);
        for (int i = 0; i < 8; i++) {
                    new Thread(
                            () -> {
                                try {
                                    semaphore.acquire();
                                    System.out.println(Thread.currentThread().getName() + "占了一个车位...");
                                    TimeUnit.SECONDS.sleep(3);
                                    System.out.println(Thread.currentThread().getName()+"开走了...");
                                    semaphore.release();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            },
                            "线程"+String.valueOf(i))
                            .start();
                }
    }
}
