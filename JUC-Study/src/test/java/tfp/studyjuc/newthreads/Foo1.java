package tfp.studyjuc.newthreads;

/**
 * Author: ph
 * Date: 2020/6/25
 * Time: 18:19
 * Description:
 */
public class Foo1  extends   Thread{
    @Override
    public void run() {
        System.out.println("这是创建线程的第一个方法..."+ Thread.currentThread().getName());
    }
}
