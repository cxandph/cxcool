package com.tfp.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Author: ph Date: 2020/6/18 Time: 11:56 Description: 线程间通信 ： 判断/wait/干活/notify */
class Cake {
  public static final int INIT_NUM = 0;
  private int num = INIT_NUM;
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();

  public int increase() throws InterruptedException {
    lock.lock();
    try {
      // 判断
      while (num > INIT_NUM) {
        condition.await();
      }
      //      干活
      num++;
      System.out.println(Thread.currentThread().getName() + "\t当前蛋糕数:" + num);
      // 通知
      condition.signalAll();

    } finally {
      lock.unlock();
    }

    return num;
  }

  public int decrease() throws InterruptedException {
    lock.lock();
    try {
      // 判断
      while (num == INIT_NUM) {
        condition.await();
      }
      // 干活
      num--;
      System.out.println(Thread.currentThread().getName() + "\t当前蛋糕数:" + num);
      // 通知
      condition.signalAll();
    } finally {
      lock.unlock();
    }
    return num;
  }
}

/**
 * @Author pan.he
 * @Date 2020/6/18 14:43
 * @Description
 * 1、线程操作资源类(先创建一个资源类)
 * 2、判断/干活/通知 (判断时采用while，以防止多线程的虚假唤醒)
 * tips:  lambda表达式的写法：拷贝小括号，写死右箭头，落地大括号
 *使用lock unlock 的方式锁住一段程序块，并进行线程间通信，注意标志位的影响
 * @Since version-1.0
 */
public class ThreadsCommunication {

  public static void main(String[] args) {
    Cake cake = new Cake();
    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; i++) {
                  cake.increase();
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            },
            "PRODUCE-1")
        .start();

    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; i++) {
                  cake.increase();
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            },
            "PRODUCE-2")
        .start();

    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; i++) {
                  cake.decrease();
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            },
            "CONSUMER-1")
        .start();

    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; i++) {
                  cake.decrease();
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            },
            "CONSUMER-2")
        .start();
  }
}
