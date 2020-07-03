package com.tfp.juc;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 23:40
 * Description:
 * 经典的基本类型、引用类型、常量池变值问题
 */
public class TestTransferValue {


    public  void changeValue1(int num) {
        num = new Integer(12345);
    }

    public  void changeValue2(Person person) {
        System.out.println(person.hashCode());
        person.setName("xxxx");
        System.out.println(person.hashCode());
    }
    public   void changeValue3(String  name){
        System.out.println(name.hashCode());
        name ="dododod";
        System.out.println(name.hashCode());
    }

    public static void main(String[] args) {
        TestTransferValue  testTransferValue  =  new  TestTransferValue();
        //传入的是基本数据类型的拷贝，跳出方法区无效
        int i =111;
        testTransferValue.changeValue1(i);
        System.out.println("============");
        System.out.println(i);
        System.out.println("============");
        //传入的是对象的引用：跟着一起变
        Person person =new Person();
        person.setName("chenxi");
        testTransferValue.changeValue2(person);
        System.out.println("============");
        System.out.println(person);
        System.out.println("============");
        //String是常量池，传入方法区，name ="dododod" 操作，会先从常量池中找，找不到新建一个并插入常量池.
        String  name ="chenxi";
        testTransferValue.changeValue3(name);
        System.out.println("============");
        System.out.println(name);

    }
}
