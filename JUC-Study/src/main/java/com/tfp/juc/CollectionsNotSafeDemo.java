package com.tfp.juc;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 10:38
 * Description:集合类线程不安全举例:出现ConcurrentModificationException
 */
public class CollectionsNotSafeDemo {

    public static void main(String[] args) {
        //list  线程不安全...
//        notSafeList();
//        safeListDemo1();
//        safeListDemo2();
//        safeListDemo3();
        //set  线程不安全...
//        notSafeSetDemo();
//        safeSetDemo1();
//          safeSetDemo2();

//        Map 线程不安全...
//        notSafeMapDemo();
//        saveMapDemo1();
//        saveMapDemo2();
    }

    //List 线程不安全
    public static void notSafeList() {
        List<String> list = new ArrayList<>();
        //多个线程去写add，print(读)
        for (int i = 0; i < 30; i++) {
            new Thread(
                    () -> {

                        list.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(list);

                    },
                    "线程" + String.valueOf(i))
                    .start();
        }

    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:27
     * @Description 第一种方法：使用vector替代list
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void safeListDemo1() {
        List list = new Vector();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        list.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(list);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:28
     * @Description 第二种方法：使用工具类collections加锁处理
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void safeListDemo2() {
        List list = Collections.synchronizedList(new ArrayList<>());
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        list.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(list);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:29
     * @Description 第三种方法（最优解）:使用 java.util.concurrent中的
     * CopyOnWriteArrayList（读写分离思想）来代替list
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void safeListDemo3() {

        List list = new CopyOnWriteArrayList();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        list.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(list);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }


    //-----------set线程不安全

    public static  void notSafeSetDemo() {
        Set<String> set = new HashSet<>();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        set.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(set);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:34
     * @Description 第一种方法：使用工具类collections加锁处理
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void safeSetDemo1() {
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        set.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(set);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:34
     * @Description 第二种方法（最优解）:使用 java.util.concurrent中的
     * * CopyOnWriteArraySet（读写分离思想）来代替set
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void safeSetDemo2() {
        Set<String> set = new CopyOnWriteArraySet();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        set.add(UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(set);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }


    //---map线程不安全

    public  static void notSafeMapDemo() {
        Map<String, String> map = new HashMap<>();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(map);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:44
     * @Description 第一种方法：使用工具类collections加锁处理
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public static  void saveMapDemo1() {
        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(map);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }
    }

    /**
     * @Author pan.he
     * @Date 2020/6/28 11:44
     * @Description 第二种方法（最优解）:使用 java.util.concurrent中的
     * *      * ConcurrentHashMap来代替set
     * @Param []
     * @Return void
     * @Since version-1.0
     */

    public  static void saveMapDemo2() {
        Map<String, String> map = new ConcurrentHashMap<>();
        //多个线程去写add，print(读)
        for (int i = 0; i < 40; i++) {
            new Thread(
                    () -> {
                        map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                        System.out.println(map);
                    },
                    "线程" + String.valueOf(i))
                    .start();
        }

    }
}
