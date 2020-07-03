package com.tfp.juc;

import java.util.concurrent.*;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 22:36
 * Description:
 * 分支、合并框架展示
 * Fork：把一个复杂任务进行分拆，大事化小
 * Join：把分拆任务的结果进行合并.
 * 波那契数列（Fibonacci sequence），
 * 又称黄金分割数列:jdk中给的示例
 * 斐波那契数列指的是这样一个数列：1,1,2,3,8,11
 * 这个数列从第3项开始，每一项都等于前两项之和
 */

class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n)
    { this.n = n; }
    protected Integer compute() {
      if (n <= 1)
        return n;
      Fibonacci f1 = new Fibonacci(n - 1);
      f1.fork();
      Fibonacci f2 = new Fibonacci(n - 2);
      return f2.compute() + f1.join();
    }
  }
public class ForkJoinDemo {


    public static void main(String[] args) {
        ForkJoinPool   forkJoinPool = new  ForkJoinPool();
        try {
            Fibonacci  myTask  = new Fibonacci(6);
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }


    }
}
