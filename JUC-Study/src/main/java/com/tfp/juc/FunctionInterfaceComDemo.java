package com.tfp.juc;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 20:01
 * Description:
 * 展示java中常见的4大函数接口
 */
public class FunctionInterfaceComDemo {

    /**
     * @Author pan.he
     * @Date 2020/6/29 20:23
     * @Description 消费型接口，一个参数，没有返回值
     * @Param []
     * @Return void
     * @Since version-1.0
     */
    @Test
    public  void consumerDemo(){
        //消费
        Consumer<String> consumer =  new Consumer<String>(){

            @Override
            public void accept(String s) {
                System.out.println(Thread.currentThread().getName()+":consumer:==>"+s);
            }
        };
        consumer.accept("hello");
        //改写为lambda表达式(一般模式)
        System.out.println("改写为lambda表达式：=============》》》》》》》》》");
        Consumer<String> consumer2 = (String s) -> {
            System.out.println(Thread.currentThread().getName()+":consumer:==>"+s);
        };
        consumer2.accept("cxcool");
    }

    /**
     * @Author pan.he
     * @Date 2020/6/29 20:23
     * @Description 供给型接口，无参数，有返回值
     * @Param []
     * @Return void
     * @Since version-1.0
     */
    @Test
    public  void supplyDemo(){
        //生产
        Supplier<String> supplier =  new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName() + "这是supplier");
                return  "这是supplier";
            }
        };
        System.out.println(supplier.get());
        //改写为lambda表达式(部分缩减模式)
        System.out.println("改写为lambda表达式：=============》》》》》》》》》");
        Supplier<String> supplier2 = () -> {
            System.out.println(Thread.currentThread().getName() + "这是supplier");
            return  "这是supplier";
        };
        System.out.println(supplier2.get());
    }

    /**
     * @Author pan.he
     * @Date 2020/6/29 20:24
     * @Description 断定型接口，一个参数，返回boolean
     * @Param []
     * @Return void
     * @Since version-1.0
     */
    @Test
    public  void predicateDemo(){
        Predicate<String>  predicate  =  new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty();
            }
        };
        System.out.println(predicate.test("hellochexi。。。"));
        //改写为lambda表达式(最简模式)
        System.out.println("改写为lambda表达式：=============》》》》》》》》》");
        Predicate<String>  predicate2  = s -> s.isEmpty();
        System.out.println(predicate2.test("hellochexi。。。"));
    }

    /**
     * @Author pan.he
     * @Date 2020/6/29 20:24
     * @Description 函数型接口，一个参数，一个返回值
     * @Param []
     * @Return void
     * @Since version-1.0
     */
    @Test
    public  void  functionDemo(){

        Function<String ,Integer>  function =  new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        System.out.println(function.apply("helloWorld!"));

        //改写为lambda表达式(最简模式)
        System.out.println("改写为lambda表达式：=============》》》》》》》》》");
        Function<String ,Integer>  function2 =  s -> s.length();
        System.out.println(function2.apply("helloWorld!"));
    }




}
