package tfp.studyjuc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author: ph
 * Date: 2020/6/25
 * Time: 18:44
 * Description:
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask   = new FutureTask( () -> {
            System.out.println(Thread.currentThread().getName()+"通过Callable接口创建线程");
            return 888;
        });
        FutureTask futureTask2   = new FutureTask( () -> {
            System.out.println(Thread.currentThread().getName()+"通过Callable接口创建线程");
            return 777;
        });
        new  Thread(futureTask,"A").start();
        new  Thread(futureTask2,"B").start();
        System.out.println( futureTask.get());
        System.out.println( futureTask2.get());
    }
}
