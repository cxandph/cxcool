package com.tfp.reg.studyreg.test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Author: ph
 * Date: 2020/6/18
 * Time: 22:59
 * Description:
 */
public class NativeDownload {

    public final static boolean DEBUG = true; //调试用
    private static int BUFFER_SIZE = 8096; //缓冲区大小
    private Vector vDownLoad = new Vector(); //URL列表
    private Vector vFileList = new Vector(); //下载后的保存文件名列表
    /**
     　* 构造方法
     　*/
    public NativeDownload() {}
    /**
     　* 清除下载列表
     　*/
    public void resetList() {
        vDownLoad.clear();
        vFileList.clear();
    }
    /**
     　* 增加下载列表项
     　*
     　* @param url String
     　* @param filename String
     　*/
    public void addItem(String url, String filename) {
        vDownLoad.add(url);
        vFileList.add(filename);
    }
    /**
     　* 根据列表下载资源
     　*/
    public void downLoadByList() {
        String url = null;
        String filename = null;
//按列表顺序保存资源
        for (int i = 0; i < vDownLoad.size(); i++) {
            url = (String) vDownLoad.get(i);
            filename = (String) vFileList.get(i);
            try {
                saveToFile(url, filename);
            } catch (IOException err) {
                if (DEBUG) {
                    System.out.println("资源[" + url + "]下载失败!!!");
                }
            }
        }
        if (DEBUG) {
            System.out.println("下载完成!!!");
        }
    }
    /**
     * 将HTTP资源另存为文件
     *
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    public void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;

//建立链接
        url = new URL(destUrl);

        httpUrl = (HttpURLConnection) url.openConnection();

        httpUrl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36)");
//连接指定的资源
        httpUrl.connect();
//获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());
//建立文件
        fos = new FileOutputStream(fileName);
        if (this.DEBUG)
            System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
                    fileName + "]");
//保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);
        fos.close();
        bis.close();
        httpUrl.disconnect();
    }

    /**
     * 将HTTP资源另存为文件
     *
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    public void saveToFile2(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;

//建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();

//           String authString = "username" + ":" + "password";
        String authString = "50301" + ":" + "88888888";
        String auth = "Basic " +
                new sun.misc.BASE64Encoder().encode(authString.getBytes());
        httpUrl.setRequestProperty("Proxy-Authorization", auth);

//连接指定的资源
        httpUrl.connect();
//获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());
//建立文件
        fos = new FileOutputStream(fileName);

        if (this.DEBUG)
            System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
                    fileName + "]");
//保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);

        fos.close();
        bis.close();
        httpUrl.disconnect();
    }
    /**
     * 设置代理服务器
     *
     * @param proxy String
     * @param proxyPort String
     */

    /**
     * 主方法(用于测试)
     *
     * @param argv String[]
     */
    public static void main(String argv[]) {
        try {
//    //增加下载列表（此处用户可以写入自己代码来增加下载列表）
//   oInstance.addItem("http://www.~~~.com/java/网络编程001.zip","./网络编程1.zip");//
//     oInstance.addItem("http://www.~~~.com/java/网络编程002.zip","./网络编程2.zip");
//     oInstance.addItem("http://www~~~.com/java/网络编程003.zip","./网络编程3.zip");
//     //开始下载
//     oInstance.downLoadByList();
            String   url  ="http://sz-download.bde4.com/ftn_handler/2C26C300A2E630F9837E81487d624A76594C70e1B5CdBCebC70334170d22Fc4Cba19b6a448D6b02C78f181b166aA2d85A9dB09558A44DE88C6754a4B27a235E0";
            String fileName  ="F:\\视频\\斗罗大陆\\第10集.mp4";
            new NativeDownload().saveToFile(url,fileName);
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
