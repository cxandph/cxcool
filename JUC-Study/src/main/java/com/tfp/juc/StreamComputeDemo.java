package com.tfp.juc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: ph
 * Date: 2020/6/29
 * Time: 20:21
 * Description:
 * 流式计算，链式编程
 * 	step1.创建一个Stream：一个数据源（数组、集合）
 * 	step2.中间操作：一个中间操作，处理数据源数据
 * 	step3.终止操作：一个终止操作，执行中间操作链，产生结果
 * 总而言之就是 : 源头=>中间流水线=>结果
 *  题目：请按照给出数据，找出同时满足
 *  偶数ID
 *  且年龄大于24
 *  且用户名转为大写
 *  且用户名字母倒排序
 *  最后只输出一个用户名
 */

class  User {
    private  Integer  id  ;
    private  String  name  ;
    private  Integer age;

    public  User(){

    }
    public  User(Integer id ,String  name  ,Integer age){
        this.id= id ;
        this.name = name  ;
        this.age= age;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
public class StreamComputeDemo    {

    public static void main(String[] args) {
        User u1 =  new User(1,"a",25);
        User u2 =  new User(2,"b",23);
        User u3 =  new User(3,"c",21);
        User u4 =  new User(4,"d",27);
        User u5 =  new User(5,"e",30);
        User u6 =  new User(6,"f",28);
        List<User> list = Arrays.asList(u1,u2,u3,u4,u5,u6);

        /*  题目：请按照给出数据，找出同时满足
         *  偶数ID
         *  且年龄大于24
         *  且用户名转为大写
         *  且用户名字母倒排序
         *  最后只输出一个用户名
         */
//             list.stream().filter( (t) -> t.getId()%2==0)
//                .filter((t) -> t.getAge()>24)
//                .map((t) -> t.getName().toUpperCase())
//                .sorted( (o1,o2) -> o2.compareTo(o1))
//                .limit(1L)
//                     .forEach( t -> System.out.println(t));




       List list2 = list.stream().filter( (t) -> t.getId()%2==0)
                .filter((t) -> t.getAge()>24)
                .map((t) -> new User(t.getId(),t.getName().toUpperCase(),t.getAge()))
                .sorted( (o1,o2) -> o2.getName().compareTo(o1.getName()))
                .limit(1L)
               .collect(Collectors.toList());
        System.out.println(list2);
        //这里可以替代  t -> System.out.println(t)是一种语法规则
        list2.forEach(System.out :: println);




    }


}
