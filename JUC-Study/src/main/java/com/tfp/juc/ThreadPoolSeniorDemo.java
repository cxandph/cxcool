package com.tfp.juc;

import java.util.concurrent.*;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 14:27
 * Description:
 * 自定义线程池
 */
public class ThreadPoolSeniorDemo {

    public static void main(String[] args) {

        //获取当前机器的cpu核心数
        System.out.println("当前机器的cpu核心数==>"+Runtime.getRuntime().availableProcessors());
        //计算密集型 :  cpu核心数+1
        //io密集型：
        ExecutorService defThreadPool = new ThreadPoolExecutor(3,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                //直接异常RejectedExecutionException
//                new ThreadPoolExecutor.AbortPolicy());
                //调用者运行策略:返回给调用者 执行
//                new ThreadPoolExecutor.CallerRunsPolicy());
                //直接放弃运行策略
//                new ThreadPoolExecutor.DiscardPolicy());
                //等待最久的放弃策略
                new ThreadPoolExecutor.DiscardOldestPolicy());

        try {
            for (int i = 0; i < 10; i++) {

                defThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务...");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            defThreadPool.shutdown();
        }

    }


}
