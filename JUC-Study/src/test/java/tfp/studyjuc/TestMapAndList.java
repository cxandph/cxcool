package tfp.studyjuc;

import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Author: ph
 * Date: 2020/6/25
 * Time: 16:48
 * Description:
 */
public class TestMapAndList {

    @Test
    public void   testList(){

    }

    @Test
    public void   testMap(){
        HashSet hashSet   =  new HashSet();
    }


    @Test
    public  void  testIllegalMonitorStateException() throws InterruptedException {


        synchronized(this){
//           wait(3000);
            TimeUnit.SECONDS.timedWait(this,3);
        }

    }
}
