package com.tfp.juc;


/** Author: ph Date: 2020/6/18 Time: 11:08 Description:拷贝小括号,写死右箭头，落地大括号 */
public class LamdaExpress {

  public static void main(String[] args) {
    Foo foo =
        (str) -> {
          return "我是" + str;
        };
    System.out.println(foo.printInfo1("方法1,这种类型的方法只能有一个，在lamda表达式中才可以被找到才能被找到"));
//    System.out.println(foo.printInfo2("方法2"));
    System.out.println("default方法可以有多个...");
    System.out.println(foo.plus(1, 1));
    System.out.println(foo.minus(11, 1));
    System.out.println("static 方法也可以有多个");
    System.out.println(Foo.div(8,2));
    System.out.println(Foo.minus2(9,2));
  }
}
