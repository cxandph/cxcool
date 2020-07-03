package com.tfp.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: ph
 * Date: 2020/6/18
 * Time: 0:44
 * Description: 多线程编程-卖票
 */

//定义资源类
class Tikets {
    private int num = 100;
    private Lock lock = new ReentrantLock();

    //第一种方式：synchronized关键字
//    public synchronized void sale(){
//        if(num >0){
//
//            System.out.println(Thread.currentThread().getName()+" 卖票第"+ num--+"张\t 剩余"+num+"张票");
//        }
//    }
    //第二种方式: lock
    public void sale() {
        lock.lock();
    try {
        if (num > 0) {
            System.out.println(Thread.currentThread().getName() + " 卖票第" + num-- + "张\t 剩余" + num + "张票");
        }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
        lock.unlock();
    }
    }
}

/**
 * @Author pan.he
 * @Date 2020/6/18 2:06
 * @Description 重要的一般方法/规律
 * 1.在"高内聚，低耦合"的前提下,线程操作资源类；
 * 2.拷贝小括号,写死右箭头，落地大括号（Lamda表达式）
 * @Param
 * @Return
 * @Since version-1.0
 */
public class SaleTikets {
    public static void main(String[] args) {
        Tikets tikets = new Tikets();
//        firSale(tikets);
        secSale(tikets);
    }

    //第一个版本:匿名内部类
    public static void firSale(Tikets tikets) {
    System.out.println("原生...");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 110; i++) {
                            tikets.sale();
                        }
                    }
                }
                , "A").start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 110; i++) {
                            tikets.sale();
                        }
                    }
                }
                , "B").start();
    }

    //第二个版本:lamda表达式
    public static void secSale(Tikets tikets) {
        System.out.println("程序改写:lamda");
        new Thread(() -> { for (int i = 0; i < 110; i++) { tikets.sale(); } }, "A").start();
        new Thread(() -> { for (int i = 0; i < 110; i++) { tikets.sale(); } }, "B").start();
    }

}





