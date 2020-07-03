package com.tfp.juc;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 23:42
 * Description:
 * 配合演示实体类
 */
public class Person {

    private int id  ;
    private String  name  ;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
