package com.tfp.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 23:56
 * Description:
 *  分支、合并框架展示
 *  Fork：把一个复杂任务进行分拆，大事化小
 *  Join：把分拆任务的结果进行合并.
 *  从1+2+3+4...+100,分组加和计算
 *
 */


class  MyTask extends RecursiveTask<Integer>{
    public static final int  SPILI_NUM  =10;
    private int  begin   ;
    private int  end ;
    private int  result ;

    public MyTask(int  begin , int  end){
        this.begin =begin;
        this.end=end;
    }
    @Override
    protected Integer compute() {
        if(end - begin<=SPILI_NUM){
            for (int i = begin; i <= end; i++) {
                result=result+i;
            }

        }else{
            int  midNum =  (end+begin)/2;
            MyTask  task1  = new MyTask(begin,midNum);
            MyTask  task2  = new MyTask(midNum+1,end);
            task1.fork();
            task2.fork();
            result = task1.join()+task2.join();
        }

        return  result;

    }
}
public class ForkJoinDemo2 {

    public static void main(String[] args) {
        MyTask myTask =  new MyTask(0,10000);
        ForkJoinPool forkJoinPool =  new ForkJoinPool();
        ForkJoinTask forkJoinTask =forkJoinPool.submit(myTask);
        try {
            System.out.println(forkJoinTask.get());
            System.out.println(forkJoinPool.getPoolSize());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }
    }
}
