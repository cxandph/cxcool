package com.tfp.reg.studyreg.entities;

/**
 * Author: ph
 * Date: 2020/6/17
 * Time: 3:44
 * Description:爬取哔嘀影视的视频文件
 */
public class BiDiEntity {


    public static  final  String  HTML_HOST="https://bde4.com";
    public static  final  String  DOWNLOAD_HOST="https://bde4.com/god";
    public static  final  String  DOWNLOAD_SUFFIX="?sg=";

    private String  htmlUrl;
    private String  downloadUrl;

    public static  String getHtmlUrl(String tagVideo) {
        return HTML_HOST+tagVideo;
    }

    public static String getDownloadUrl(String ptoken ) {
        return DOWNLOAD_HOST+"/"+ptoken+DOWNLOAD_SUFFIX;
    }


}
