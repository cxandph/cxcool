package com.tfp.reg.studyreg.utils;

import com.tfp.reg.studyreg.test.Progress;
import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.NumberFormat;

/**
 * Author: ph
 * Date: 2020/6/20
 * Time: 4:13
 * Description:
 */
public class FileUtils {

    //缓冲buffer为5m
    public static final int  CAPACITY  =  1024*1024*5;
    //设定回调周期
    public static final int  CAPACITY_CALLBACK=1024*1024*10;
    public static NumberFormat nf  =  NumberFormat.getInstance();
    public static  NumberFormat  nf2 =  NumberFormat.getInstance();

    static {
        nf.setMaximumFractionDigits(2);
        nf2.setMaximumFractionDigits(0);
        nf2.setRoundingMode(RoundingMode.DOWN);
    }
    //改造:感知下载进度
    public  static String  saveFileByHttpEntity(HttpEntity httpEntity, String saveDir, Progress progress)  {
        if(httpEntity ==null){
            return  null;
        }
        //文件的大小
        long  fileSize =httpEntity.getContentLength();
        System.out.println("下载文件大小为:"+fileSize);
        //已完成下载的字节
        double  byteRead =0.00 ;
        //当前总共已读取整(/mb)
        long  byteReadCur = 0l;
        //上次总共已读已读取取整(/mb)
        long  byteReadLast = 0l;
        //默认生成的文件
        if(saveDir==null || saveDir.equals("")){
            PropertiesUtil propertiesUtil  =  new  PropertiesUtil();
            saveDir = propertiesUtil.getProperties(null).getProperty("SAVE_DIR")+System.currentTimeMillis();
        }
        FileOutputStream  fos  = null;
        ReadableByteChannel readChannerl =null;
        ByteBuffer  bbf =null;
        FileChannel  writeChannel =null;
        try {
            fos = new FileOutputStream(new File(saveDir));
            writeChannel  =  fos.getChannel();
            readChannerl =Channels.newChannel(httpEntity.getContent());
            bbf  = ByteBuffer.allocate(CAPACITY);
            printByteBufferInfo(bbf);
            int length = -1 ;
            while((length =readChannerl.read(bbf))!=-1) {
                bbf.flip();
                byteRead +=length;
                //每隔CAPACITY_CALLBACK进行回调通知
                byteReadCur=Long.parseLong(nf2.format(byteRead/CAPACITY_CALLBACK));
                try {

                } catch (Exception e) {
                    System.out.println(byteRead);
                    System.out.println(CAPACITY_CALLBACK);
                    System.out.println(byteReadCur);
                    e.printStackTrace();
                }

                if(byteReadCur>byteReadLast){
                    byteReadLast = byteReadCur;
                    progress.onProgress( Double.parseDouble(nf.format(byteRead*100/fileSize)));
                }
                writeChannel.write(bbf);
                bbf.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            progress.onErr();
        } catch (IOException e) {
            e.printStackTrace();
            progress.onErr();
        }finally {
            //为什么会先执行
            progress.onFinish();
            try {
                if(fos !=null){
                    fos.close();
                }
                if(bbf!=null){
                    bbf = null;
                }
                if(readChannerl!=null){
                    readChannerl.close();
                }
               if(writeChannel!=null){
                   writeChannel.close();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        System.out.println( "文件保存完毕...");
        return "文件保存完毕...";
    }

    private static void printByteBufferInfo(ByteBuffer bbf) {
        System.out.println("限制是：" + bbf.limit() + ",容量是：" + bbf.capacity() + " ,位置是：" + bbf.position());
    }
}
