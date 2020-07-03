package com.tfp.juc;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Author: ph
 * Date: 2020/6/28
 * Time: 17:35
 * Description:
 * 阻塞队列
 *
 *
 * 以下来自jdk doc  中关于BlockingQueue接口的描述
 * Summary of BlockingQueue methods
 * Throws ||exception	||Specialvalue|| Blocks ||	        Times out
 * Insert	add(e)	       offer(e)	      put(e)	        offer(e, time, unit)
 * Remove	remove()	   poll()	      take()	        poll(time, unit)
 * Examine	element()	   peek()	      notapplicable	    notapplicable
 */
public class BlockingQueueDemo {

    @Test
   public  void testAddRemoveElement(){
        BlockingQueue<String> blockingQueue   = new ArrayBlockingQueue(3) ;
        blockingQueue.add("A");
        blockingQueue.add("B");
        blockingQueue.add("C");
        System.out.println("======");
        System.out.println(blockingQueue.element());
        System.out.println("======");
//        blockingQueue.add("D");

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        //报异常
        System.out.println(blockingQueue.element());
    }
   @Test
   public  void testOfferPollPeek(){
       BlockingQueue<String> blockingQueue   = new ArrayBlockingQueue(3) ;
       System.out.println(blockingQueue.offer("A"));
       System.out.println(blockingQueue.offer("B"));
       System.out.println(blockingQueue.offer("C"));

//       System.out.println("遍历开始...");
//       Iterator  iterator   =  blockingQueue.iterator();
//       while(iterator.hasNext()){
//           System.out.println(iterator.next());
//       }
//       System.out.println("遍历结束...");
       //false
       System.out.println(blockingQueue.offer("D"));
       System.out.println("peek...");
       System.out.println(blockingQueue.peek());
       System.out.println(blockingQueue.poll());
       System.out.println(blockingQueue.poll());
       System.out.println(blockingQueue.poll());
       //null
       System.out.println(blockingQueue.poll());
       System.out.println("peek...");
       //null
       System.out.println(blockingQueue.peek());

   }
   @Test
   public  void testPutTake(){
       BlockingQueue<String> blockingQueue   = new ArrayBlockingQueue(3) ;
       try {
           blockingQueue.put("A");
           blockingQueue.put("B");
           blockingQueue.put("C");
           System.out.println("peek...");
           System.out.println(blockingQueue.peek());
           blockingQueue.take();
           blockingQueue.put("D");
           System.out.println("peek...");
           System.out.println(blockingQueue.peek());

           //========
           blockingQueue.take();
           blockingQueue.take();
           blockingQueue.take();

//           blockingQueue.put("E");
//           System.out.println("peek...");
//           System.out.println(blockingQueue.peek());
           blockingQueue.take();


       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
   @Test
   public  void testOfferPullTimeout(){
       BlockingQueue<String> blockingQueue   = new ArrayBlockingQueue(3) ;
       try {
           System.out.println(blockingQueue.offer("A", 3, TimeUnit.SECONDS));
           System.out.println(blockingQueue.offer("B", 3, TimeUnit.SECONDS));
           System.out.println(blockingQueue.offer("C", 3, TimeUnit.SECONDS));
           System.out.println("队列已满..."+System.currentTimeMillis());
           System.out.println(blockingQueue.offer("C", 3, TimeUnit.SECONDS));
           System.out.println(System.currentTimeMillis());

           System.out.println(blockingQueue.poll(3,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(3,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(3,TimeUnit.SECONDS));
           System.out.println(blockingQueue.poll(3,TimeUnit.SECONDS));

       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
}
