package com.tfp.reg.studyreg.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Author: ph
 * Date: 2020/6/20
 * Time: 2:25
 * Description:主要用于对properties文件的手动加载
 */
public class PropertiesUtil {
    //默认路径】
     public static final  String  DEF_PROP_URL ="application.properties";

     public Properties getProperties(String fileName){
         if(fileName==null ||fileName.equals("")){
             fileName  = DEF_PROP_URL;
         }
         Properties  properties  = new Properties();
         System.out.println("将要加载的配置文件的路径是:");
         System.out.println(this.getClass().getClassLoader().getResource(fileName).getPath());
         try {
             properties.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
         } catch (IOException e) {
             throw  new  ExceptionInInitializerError("初始化失败...");
         }

         return properties ;
         
     }
}
