package com.tfp.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author: ph
 * Date: 2020/6/30
 * Time: 0:17
 * Description:
 * 异步回调：有/没有返回参数
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //没有返回值...
//        CompletableFuture  completableFuture  =CompletableFuture.runAsync(
//                () -> System.out.println(Thread.currentThread().getName()+"没有返回值."));
//        completableFuture.get();

        //有返回值...
        CompletableFuture<Integer>  completableFuture2  =CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName()+"有返回值.");
//                    return 111;
                    return 111/0;
                }
        ).whenComplete(
                (c1,c2) -> {
                    System.out.println(c1);
                    System.out.println(c2);
                }
        ).exceptionally(
                t -> {
                    System.out.println(t.getMessage());
                    return 222;
                }
        );
        System.out.println("=====================");
        System.out.println(completableFuture2.get());
    }
}
