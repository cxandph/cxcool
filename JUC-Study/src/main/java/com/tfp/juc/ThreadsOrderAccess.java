package com.tfp.juc;

/** Author: ph Date: 2020/6/18 Time: 14:47 Description:多线程-确定通知顺序 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class PrintInfo {
  private int num = 1;
  private Lock lock = new ReentrantLock();
  private Condition  condition1 = lock.newCondition();
  private Condition  condition2 = lock.newCondition();
  private Condition  condition3 = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            // 1、判断
            while (num != 1) {
                System.out.println(Thread.currentThread().getName()+"waiting....");
                condition1.await();
            }
      // 2、干活
      System.out.println(Thread.currentThread().getName()+"runing...");
            // 3、通知
            // 3.1 变更标志位
            num = 2;
            // 3.2顺序通知
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB() {
      lock.lock();
      try {
        // 1、判断
        while (num != 2) {
        System.out.println(Thread.currentThread().getName()+"waiting...");
          condition2.await();
        }
        // 2、干活
          System.out.println(Thread.currentThread().getName()+"runing...");
        // 3、通知
        // 3.1 变更标志位
        num = 3;
        // 3.2顺序通知
        condition3.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  //
    public void printC() {
      lock.lock();
      try {
        // 1、判断
        while (num != 3) {
        System.out.println(Thread.currentThread().getName()+"waiting...");
          condition3.await();
        }
        // 2、干活
          System.out.println(Thread.currentThread().getName()+"runing...");
        // 3、通知
        // 3.1 变更标志位
        num = 1;
        // 3.2顺序通知
        condition1.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
}

/**
 * @Author pan.he
 * @Date 2020/6/28 10:28
 * @Description
 * 控制线程按照一定顺序进行调度：
 * 可以解决问题：AAA先打印5次，BBB再打印6次，CCC最后打印7次 如此按顺序进行循环10轮，请用多线程的方式解决此类问题。
 * @Param
 * @Return
 * @Since version-1.0
 */
public class ThreadsOrderAccess {

  public static void main(String[] args) {
    PrintInfo printInfo = new PrintInfo();
    new Thread(
            () -> {
              for (int i = 0; i < 10; i++) {
                printInfo.printA();
              }
            },
            "A线程")
        .start();
    new Thread(
            () -> {
              for (int i = 0; i < 10; i++) {
                printInfo.printB();
              }
            },
            "B线程")
        .start();
    new Thread(
            () -> {
              for (int i = 0; i < 10; i++) {
                printInfo.printC();
              }
            },
            "C线程")
        .start();
  }
}
