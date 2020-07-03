package com.tfp.reg.studyreg.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.tfp.reg.exceptions.DefineException;
import com.tfp.reg.studyreg.entities.DownLoadFileEntity;

import java.text.NumberFormat;
import java.util.List;

/**
 * Author: ph
 * Date: 2020/6/18
 * Time: 22:08
 * Description:
 */
public class Test2 {

    public static void main(String[] args) throws DefineException {

        testNumberFormat();

//        System.out.println("系统最大使用空间：Xmx=" + Runtime.getRuntime().maxMemory()/1024.0/1024 + "M");
//        System.out.println("系统可用空间：free mem=" + Runtime.getRuntime().freeMemory()/1024.0/1024 + "M");
//        System.out.println("系统中分配到的空间：total mem=" + Runtime.getRuntime().totalMemory()/1024.0/1024 + "M");
////        String url  ="https://bde4.com/god/E9642D9C247435A61F151654B50841647A8218D3EBCC6B2E2B26E2A6F2DD77AC37ADC882203B60643E7FFC3EE9C2ED94?sg=";
//        Map<String,Object> header  =  new HashMap<>();
//        header.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//        header.put("cookie","__cfduid=d7b9ec79e919e5474c26f2ebae5c30f9b1592230365; _ga=GA1.2.1246213429.1592230373; Hm_lvt_70b86b24f83343ed8a495ada6dbc4357=1592230373,1592312123,1592325622,1592481013; _gid=GA1.2.1424725454.1592481015; JSESSIONID=phWJem_MRcKFQ8Oa1hN0bvDr6c1h7q_X5DWW_GEZ; Hm_lpvt_70b86b24f83343ed8a495ada6dbc4357=1592490186; recente=%5B%7B%22vod_name%22%3A%22%E5%A5%A5%E7%BB%B4%E5%B0%94%E5%8F%B7%E7%AC%AC%E4%BA%8C%E5%AD%A3%22%2C%22vod_url%22%3A%22%2Fxiju%2F14008.htm%22%2C%22vod_part%22%3A%22%E7%AC%AC1%E9%9B%86%EF%BC%88%E8%8B%B1%E8%AF%AD%EF%BC%89%22%7D%2C%7B%22vod_name%22%3A%22%E6%96%97%E7%BD%97%E5%A4%A7%E9%99%86%22%2C%22vod_url%22%3A%22%2Fjuqing%2F12024.htm%22%2C%22vod_part%22%3A%22%E7%AC%AC7%E9%9B%86%EF%BC%88%E5%9B%BD%E8%AF%AD%EF%BC%89%22%7D%2C%7B%22vod_name%22%3A%22%E9%98%BF%E7%89%B9%E7%B1%B3%E6%96%AF%E7%9A%84%E5%A5%87%E5%B9%BB%E5%8E%86%E9%99%A9%22%2C%22vod_url%22%3A%22%2Fqihuan%2F17949.htm%22%2C%22vod_part%22%3A%22HD1080P%EF%BC%88%E8%8B%B1%E8%AF%AD%EF%BC%89%22%7D%5D; _gat_gtag_UA_165046881_1=1; FTN5K=ded970e3");
//        String res  =HttpUtil.get(url, header);
//        System.out.println("返回值======");
//        System.out.println(res);

//        String   url  ="http://sz-download.bde4.com/ftn_handler/2c26c300A2e630f9837E81487d624A76594C70e1b5cDbcebC70334170D22fC4cbA19b6a448D6B02C78F181b166AA2d85a9Db09558A44de88c6754A4B27A235e0";
//        String fileName  ="F:\\视频\\斗罗大陆\\第10集.mp4";
//        long size = HttpUtil.downloadFile(url, FileUtil.file(fileName));
//        HttpUtil.downloadBytes(url);
//        System.out.println("Download size: " + size);size


        //httpclient 的方式尝试:
//        HttpService  httpService  = new HttpServiceImpl();
//        String  url ="http://sz-download.bde4.com/ftn_handler/190b47f3a703fF157b8A6017c3fF3c73028f0c560074A61eb5eA581f565078791c7e6Ed60E52C9Dc16A3B98b41fC0b9570ABb4F82eE9B219F8cb6299Ae4F5717";
//        Map<String,String> headers = new HashMap<>();
//        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//        headers.put("Cookie","_ga=GA1.2.1246213429.1592230373; _gid=GA1.2.1424725454.1592481015; Hm_lvt_70b86b24f83343ed8a495ada6dbc4357=1592545510,1592567740,1592573584,1592595281; Hm_lpvt_70b86b24f83343ed8a495ada6dbc4357=1592595295; FTN5K=42eb955d");
//        String res  = httpService.sendHttp(RequestType.GET,
//                url,headers,null);
//        System.out.println("httpclient返回值...");
//        System.out.println(res);

    }



    private static void doDownload(List<DownLoadFileEntity> list) {
        if (ObjectUtil.isEmpty(list)) {
            return;
        }
        for (DownLoadFileEntity d :
                list) {
            long size = HttpUtil.downloadFile(d.getUrl(), FileUtil.file(d.getFileName()));
            System.out.println("Download size: " + size);
        }
    }

    private  static   void testNumberFormat(){
        Double  b  =  3.1415926521;
        NumberFormat nf  =  NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        System.out.println("初始值:"+b);
        System.out.println(nf.format(b));
//        NumberFormat nf2  =  NumberFormat.getInstance();
//        nf2.setRoundingMode(RoundingMode.DOWN);
//        System.out.println(nf2.format(b));
        System.out.println("开始测试double的除法:===");
        System.out.println(b/2);
    }
}
