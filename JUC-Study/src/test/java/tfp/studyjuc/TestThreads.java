package tfp.studyjuc;

import tfp.studyjuc.newthreads.Foo1;
import tfp.studyjuc.newthreads.Foo2;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author: ph
 * Date: 2020/6/25
 * Time: 18:04
 * Description:
 */
public class TestThreads {

    @Test
    //1、继承Thread类
    public void createThread1() {
        new Foo1().start();
        new Foo1().start();

    }
    @Test
    //2.实现Runnable接口
    public  void createThread2(){
//        new Thread(
//                () -> {
//                    System.out.println(Thread.currentThread().getName()+"实现Runnable接口创建线程");
//                },
//                "A")
//                .start();
//        new Thread(
//                () -> {
//                    System.out.println(Thread.currentThread().getName()+"实现Runnable接口创建线程");
//                },
//                "B")
//                .start();

        Foo2  foo2   = new  Foo2();
        new Thread(foo2,"A").start();
        new Thread(foo2,"B").start();
    }
    //3、通过Callable接口创建线程
    //注意:1、futureTask.get()要放在程序的最后进行调用
    //2、同一个FutureTask中实现Callable接口的call方法的不会被调用两次
    @Test
    public  void createThread3() throws ExecutionException, InterruptedException {
        FutureTask  futureTask   = new FutureTask( () -> {
            System.out.println(Thread.currentThread().getName()+"通过Callable接口创建线程");
            return 888;
        });

        Thread  t1  =   new  Thread(futureTask,"A");
        Thread  t2 = new  Thread(futureTask,"B");
        t1.start();
        t2.start();
        System.out.println(t1.isAlive());
        System.out.println(t2.isAlive());
        System.out.println(t1.getName());
        System.out.println(t2.getName());
        System.out.println( futureTask.get());
    }
}
