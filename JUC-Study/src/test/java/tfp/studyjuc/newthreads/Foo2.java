package tfp.studyjuc.newthreads;

/**
 * Author: ph
 * Date: 2020/6/25
 * Time: 19:07
 * Description:
 */
public class Foo2  implements   Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"实现Runnable创建线程...");
    }
}
