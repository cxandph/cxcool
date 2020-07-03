package com.tfp.studyreg;


import com.tfp.reg.studyreg.utils.FileUtils;
import com.tfp.reg.studyreg.utils.PropertiesUtil;
import org.junit.Test;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.tfp.reg.studyreg.utils.FileUtils.CAPACITY_CALLBACK;

/**
 * Author: ph
 * Date: 2020/6/20
 * Time: 2:45
 * Description:
 */
public class TestSth {

    @Test
    public  void   testPropUtils(){
        PropertiesUtil propertiesUtil  = new PropertiesUtil();
        Properties  properties =propertiesUtil.getProperties("HttpPoolConfig.properties");

        System.out.println("读取配置文件...");
        System.out.println(properties.getProperty("MAX_TOTAL"));
        System.out.println(properties.getProperty("MAX_PER_ROUTE"));
    }

    @Test
    public   void  testByteBuffer(){
        ByteBuffer  buffer  =  ByteBuffer.allocate(1024);
        System.out.println(buffer.compact());
        System.out.println(buffer.mark());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
    }

    @Test
    public   void  testMap(){
        Map<String,String> map   =  new HashMap<>();
        map.put("a","aaa");
        map.put("b","bbb");
        for (Map.Entry<String,String> e :
                map.entrySet()) {
            System.out.println(e.getKey()+":"+e.getValue());
        }
        map.put("b","ccc");
        System.out.println("======修改map中的一个值以后=========");
        for (Map.Entry<String,String> e :
                map.entrySet()) {
            System.out.println(e.getKey()+":"+e.getValue());
        }
    }

    @Test
    public   void  testNF(){
        NumberFormat nf2 =  NumberFormat.getInstance();
        nf2.setMaximumFractionDigits(1);
        nf2.setRoundingMode(RoundingMode.DOWN);
        long  byteReadCur   =    0L;
        double  byteRead  =3.99;
        System.out.println(nf2.format(byteRead));
        System.out.println(byteReadCur=Long.parseLong(nf2.format(byteRead/ FileUtils.CAPACITY_CALLBACK)));
    }


}
