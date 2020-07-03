package com.tfp.juc;

/** Author: ph Date: 2020/6/18 Time: 11:09 Description: */

public interface Foo {


    //这种类型的方法只能有一个
    String  printInfo1(String str);

    //default 方法可以有多个,默认的实现
    default int  plus(int x,int  y){
        return  x+y;
    }
    default int  minus(int x,int  y){
        return  x-y;
    }


    public  static int  div(int x,int  y){
        return  x/y;
    }
    public  static int  minus2(int x,int  y){
        return  x-y-y;
    }
}
