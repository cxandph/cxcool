package com.tfp.reg.studyreg.test;


import cn.hutool.core.util.ObjectUtil;

import com.alibaba.fastjson.JSONObject;
import com.tfp.reg.enums.RequestType;
import com.tfp.reg.exceptions.DefineException;
import com.tfp.reg.studyreg.entities.BiDiEntity;
import com.tfp.reg.studyreg.entities.DownLoadFileEntity;
import com.tfp.reg.studyreg.utils.HttpService;
import com.tfp.reg.studyreg.utils.HttpServiceImpl;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: ph Date: 2020/6/17 Time: 3:55 Description:
 */
public class Crawler {
    //过滤出html页面中关键信息ptoken的正则表达式
    public static final String REG_STR = "var\\s+ptoken\\s+=\\s+\"(?<poken>\\w+)\"";
    //下载开始页(不包含)=当前保存的最大集数
    //斗罗12024
    public static final String START_HTM = "/play/12024-109.htm";
    //407错误异常出现时，进行重试的次数
    public static final int DOWNLOAD_RETRY = 3;
    private static HttpService httpService = new HttpServiceImpl();
    private static Map<String, String> headers = new HashMap<>();

    //需要下载的集数
    public static final int pnum = 1;

    // 下载文件保存目录
    public static final String SAVE_URL = "F:\\视频\\正在追更\\斗罗大陆\\";

    //默认启动的下载线程数
    public static final int DEF_THREADS = 1;

    //初始cookie值
//    public static final String INIT_COOKIE = "_ga=GA1.2.1246213429.1592230373; _gid=GA1.2.1424725454.1592481015; Hm_lvt_70b86b24f83343ed8a495ada6dbc4357=1592545510,1592567740,1592573584,1592595281; Hm_lpvt_70b86b24f83343ed8a495ada6dbc4357=1592595295; FTN5K=";
//    public static final String INIT_COOKIE ="__cfduid=d4bb5a8a3a84b85bb5520498dcd859e9e1592595665;JSESSIONID=tmyZU52064kdWMlatKSPhSE3qcxmKBwlXzOCwmnv;FTN5K=";
    public static final String INIT_COOKIE ="_ga=GA1.2.1246213429.1592230373; _gid=GA1.2.1424725454.1592481015; Hm_lvt_70b86b24f83343ed8a495ada6dbc4357=1592658928,1592678821,1592736273,1592740110; Hm_lpvt_70b86b24f83343ed8a495ada6dbc4357=1592742681; FTN5K=";
    static {
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("Cookie", INIT_COOKIE);

    }

    //主方法
    public static void main(String[] args) throws DefineException {

        int sn1 = START_HTM.indexOf(".");
        int sn3 = START_HTM.indexOf("-");
        int sn2 = Integer.parseInt(START_HTM.substring(sn3 + 1, sn1));
        System.out.println("sn2为:" + sn2);
        int offset = sn2 > 0 ? sn2 : -sn2;
        offset = String.valueOf(offset).length();
        String str1 = START_HTM.substring(0, sn1 - offset);
        System.out.println("str1:" + str1);
        String str2 = START_HTM.substring(sn1, START_HTM.length());
        System.out.println("str2:" + str2);
        //暂时不用多线程下载
//        List<DownLoadFileEntity> list = new ArrayList<>();
//        for (int i = 0; i < pnum; i++) {
//            String videoTag = str1 + (sn2 + i + 1) + str2;
//            String fileName  =SAVE_URL + "第" + (sn2 + i+2) + "集.mp4";
//            //获取下载信息实体
//            DownLoadFileEntity downLoadFileEntity = getDownLoadFileEntity(getPoken(videoTag),fileName);
//            list.add(downLoadFileEntity);
//        }
//        System.out.println("多线程下载任务启动:=====");
//        MutiThreadDownload(list);


        //循环单线程下载
        loopDownload(sn2, str1, str2);
    }

    /**
     * @Author pan.he
     * @Date 2020/6/22 2:09
     * @Description This is description of method
     * @Param [sn2 ：目标html中定位集数的值, str1:html中sn2之前的值, str2:html中sn2之后的值]
     * @Return void
     * @Since version-1.0
     */
    private static void loopDownload(int sn2, String str1, String str2) throws DefineException {
        for (int i = 0; i < pnum; i++) {
            String videoTag = str1 + (sn2 + i ) + str2;
            String fileName = SAVE_URL + "第" + (sn2 + i +1) + "集.mp4";
            //获取下载信息实体
            DownLoadFileEntity downLoadFileEntity = getDownLoadFileEntity(getPoken(videoTag), fileName);
            //启用单线程下载...
            //因为老是报错:407,做一个3次的重复
            int retry_num = 1;
            String errMsg = singleDownload(downLoadFileEntity);
            if (errMsg != null) {
                //如果发生了407错误，再试DOWNLOAD_RETRY 次
                while (errMsg.indexOf("407") > -1 && retry_num <= DOWNLOAD_RETRY) {
                    System.out.println("出现407错误,进行第"+retry_num+"次重试...");
                    errMsg = singleDownload(downLoadFileEntity);
                    retry_num++;
                }

            }


        }
    }

    /**
     * 从html中，利用正则表达式提取poken值
     *
     * @param tagVideo
     * @return
     * @throws DefineException
     */
    private static String getPoken(String tagVideo) throws DefineException {
        String res = httpService.sendHttp(RequestType.GET,
                BiDiEntity.getHtmlUrl(tagVideo), headers, null);
        Pattern pattern = Pattern.compile(REG_STR);
        Matcher matcher = pattern.matcher(res);
        String poken = null;
        while (matcher.find()) {
            poken = matcher.group("poken");
        }
        return poken;
    }

    /**
     * 获取下载的信息实体类
     *
     * @param poken    poken值
     * @param fileName 文件名
     * @return
     * @throws DefineException
     */
    private static DownLoadFileEntity getDownLoadFileEntity(String poken, String fileName) throws DefineException {
        if (poken == null || poken.equals("")) {
            return null;
        }
        String s_url = BiDiEntity.getDownloadUrl(poken);
        //一定要模仿浏览器标识，否则返回的url无法下载
        String res = httpService.sendHttp(RequestType.GET, s_url, headers, null);
        JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(res);
        String d_url = jsonObject.getString("url");
        String self_cookie = jsonObject.getString("cookie").toLowerCase();
        headers.put("Cookie", INIT_COOKIE + self_cookie);
        //再使用正确的cookie请求一次，重新解析
        res = httpService.sendHttp(RequestType.GET, s_url, headers, null);
        JSONObject jsonObject2 = com.alibaba.fastjson.JSONObject.parseObject(res);
        d_url = jsonObject2.getString("url");
        self_cookie = jsonObject2.getString("cookie").toLowerCase();
        headers.put("Cookie", INIT_COOKIE + self_cookie);
        return new DownLoadFileEntity(d_url, fileName, self_cookie);
    }


    //多线程下载：由于服务方限制，无法启用，主要是cookie引起的407问题...
    private static void MutiThreadDownload(List<DownLoadFileEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("待下载对象为空...");
            return;
        }
        // 进行下载:
        String fileName = null;
        int size = list.size();

        for (int i = 0; i < DEF_THREADS; i++) {
            List<DownLoadFileEntity> splitList = parseList(list, i);
            new Thread(
                    () -> {
                        try {
                            groupDownload(splitList);
                        } catch (DefineException e) {
                            e.printStackTrace();
                        }
                    },
                    "dl线程" + i)
                    .start();
        }

    }

    //分组下载：用于多线程
    private static void groupDownload(List<DownLoadFileEntity> list) throws DefineException {
        if (ObjectUtil.isEmpty(list)) {
            return;
        }
        for (DownLoadFileEntity d :
                list) {
            singleDownload(d);
        }

    }

    //单个的下载
    private static String singleDownload(DownLoadFileEntity d) {
        System.out.println(Thread.currentThread().getName() + "\t:将要下载:" + d.getFileName() + "请求地址是:" + d.getUrl());
        try {
            httpService.sendHttpBase(RequestType.GET,
                    d.getUrl(),
                    headers, null, d.getFileName(), new Progress() {
                        @Override
                        public String onProgress(Double d) {
                            String res   =  d+"%";
                            System.out.println("文件下载进度======"+res);
                            return  res ;
                        }

                        @Override
                        public String onFinish() {
                            System.out.println("文件下载完毕......");
                            return null;
                        }

                        @Override
                        public String onErr() {
                            System.out.println("文件下载发生错误......");
                            return null;
                        }
                    });
        } catch (DefineException e) {
            return e.getErrMsg();
        }
        return null;
    }

    /**
     * 拆分元数据
     *
     * @param list
     * @param modNum
     * @return
     */
    private static List<DownLoadFileEntity> parseList(List<DownLoadFileEntity> list, int modNum) {
        if (ObjectUtil.isEmpty(list)) {
            return null;
        }
        List<DownLoadFileEntity> rlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % DEF_THREADS == modNum) {
                rlist.add(list.get(i));
            }
        }
        return rlist;
    }
}



