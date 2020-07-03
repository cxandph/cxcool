package com.tfp.reg.studyreg.utils;



import com.alibaba.fastjson.JSON;

import com.tfp.reg.enums.RequestType;
import com.tfp.reg.exceptions.DefineException;
import com.tfp.reg.studyreg.test.Progress;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class HttpServiceImpl implements HttpService {


    private static  Logger log  = LoggerFactory.getLogger(HttpServiceImpl.class);
    //http连接池的配置文件名,注：需要放在resource文件夹下
    public static final String  CONFIG_NAME="HttpPoolConfig.properties";
    // 池化管理
    private static PoolingHttpClientConnectionManager poolConnManager = null;
    // 它是线程安全的，所有的线程都可以使用它一起发送http请求
    private static CloseableHttpClient httpClient;
    private static   RequestConfig config ;

    static {
        try {
            log.info("初始化HttpClient..,");
            log.info("获取配置文件中相关参数...");
            PropertiesUtil propertiesUtil   =new PropertiesUtil();
            Properties properties  = propertiesUtil.getProperties(CONFIG_NAME);
            // 此处解释下MAX_TOTAL和MAX_PER_ROUTE的区别：
            // 1、MAX_TOTAL是整个池子的大小；
            // 2、MAX_PER_ROUTE是根据连接到的主机对MAX_TOTAL的一个细分；比如：
            // MaxtTotal=400 DefaultMaxPerRoute=200
            // 而我只连接到http://www.abc.com时，到这个主机的并发最多只有200；而不是400；
            // 而我连接到http://www.bac.com 和
            // http://www.ccd.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是DefaultMaxPerRoute
            int MAX_TOTAL = Integer.parseInt(properties.getProperty("MAX_TOTAL"));
            int MAX_PER_ROUTE = Integer.parseInt(properties.getProperty("MAX_PER_ROUTE"));
            int CONN_TIMEOUT = Integer.parseInt(properties.getProperty("CONN_TIMEOUT"));
            int CONN_REQ_TIMEOUT = Integer.parseInt(properties.getProperty("CONN_REQ_TIMEOUT"));
            int SOCKET_TIMEOUT = Integer.parseInt(properties.getProperty("SOCKET_TIMEOUT"));

            log.info("最大连接数 ==》"+MAX_TOTAL);
            log.info("每个路由最大连接数 ==》"+MAX_PER_ROUTE);
            log.info("连接超时时间 ==》"+CONN_TIMEOUT);
            log.info("请求建立超时时间 ==》"+CONN_REQ_TIMEOUT);
            log.info("socket超时时间 ==》"+SOCKET_TIMEOUT);
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 同时最多连接数
            poolConnManager.setMaxTotal(MAX_TOTAL);
            // 设置最大路由
            poolConnManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

            //请求配置
            config = RequestConfig.custom()
                    .setConnectTimeout(CONN_TIMEOUT)
                    .setConnectionRequestTimeout(CONN_REQ_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT).build();
            httpClient = getHttpClient();
            System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    //------------------------------公有方法start--------------------------------------------

    @Override
    public String sendHttp(RequestType reqType, String url, Map<String, String> headers, Object param) throws DefineException {
        return sendHttpBase(reqType,url,headers,param,null,null);
    }

    /**
     * 发送请求
     *
     * @param reqType 请求类型
     * @param url     请求url
     * @param headers 请求头
     * @param param   请求体
     * @return
     */
    public String sendHttpBase(RequestType reqType, String url, Map<String, String> headers,
                               Object param, String saveDir, Progress progress )
            throws DefineException {
        HttpRequestBase reqBase = reqType.getHttpType(url);

        log.info("\n--->>开始向地址[{}]发起 [{}] 请求", url, reqBase.getMethod());
        log.info("--->>请求头为{}", JSON.toJSONString(headers));
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = getHttpClient();


        //设置请求头
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> reqBase.setHeader(k, v));
        }

        //添加参数 参数是json字符串
        if (param instanceof String) {
            String paramStr = String.valueOf(param);
            log.info("--->>请求参数为：{}", paramStr);
            ((HttpEntityEnclosingRequest) reqBase).setEntity(
                    new StringEntity(String.valueOf(paramStr), ContentType.create("application/json", "UTF-8")));
        }
        //参数时字节流数组
        else if (param instanceof byte[]) {
            log.info("--->>请求参数为文件流");
            byte[] paramBytes = (byte[]) param;
            ((HttpEntityEnclosingRequest) reqBase).setEntity(new ByteArrayEntity(paramBytes));
        }
        //HttpEntity类型
        else if (param instanceof HttpEntity) {
            System.out.println(param);
            ((HttpEntityEnclosingRequest) reqBase).setEntity((HttpEntity) param);
        }


        //响应对象
        CloseableHttpResponse res = null;
        //响应内容
        String resCtx = null;
        try {
            //执行请求
            res = httpClient.execute(reqBase);
            log.info("--->>执行请求完毕，响应状态：{}", res.getStatusLine());
            if (res.getStatusLine().getStatusCode() >= 400) {
                throw new DefineException("--->>HTTP访问异常:" + res.getStatusLine());
            }

            //获取请求响应对象和响应entity
            HttpEntity httpEntity = res.getEntity();

            if (httpEntity != null) {
                //判断一下返回文件的类型
                String  rContent_type = httpEntity.getContentType().getValue();
                System.out.println("Http请求返回类型是:"+rContent_type);
                if(rContent_type ==null){
                    return  null;
                }
                //如果返回文件是文件流：转到保存文件的方法
                if(rContent_type.equals("application/octet-stream")||
                rContent_type.equals("video/mp4")){
                    return FileUtils.saveFileByHttpEntity(httpEntity,saveDir,progress);
                }

                resCtx = EntityUtils.toString(httpEntity, "utf-8");
                log.info("--->>获取响应内容：{}", resCtx);
            }

        } catch (Exception e) {
            throw new DefineException(e.getMessage());
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    throw new DefineException("--->>关闭请求响应失败");
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("--->>请求执行完毕，耗费时长：{} 秒", (endTime - startTime) / 1000);
        return resCtx;
    }


    /**
     * 上传文件 构建流方式
     *
     * @param files
     * @return
     */
    public HttpEntity uploadMultiFiles(List<File> files) throws DefineException {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        for (File f : files) {
            try {
                log.info("上传附件路径为:"+f.getPath());
                log.info("上传附件绝对路径为:"+f.getAbsolutePath());
                builder.addBinaryBody("file", new FileInputStream(f), ContentType.MULTIPART_FORM_DATA, f.getName());
            } catch (IOException e) {
                throw new DefineException(e.getMessage());
            }
        }

        return builder.build();
    }

    public  String  httpPostForm(String url,Map<String,String> params, Map<String,String> headers,String encode){
        HttpRequestBase reqBase = RequestType.POST.getHttpType(url);
        log.info("\n--->>开始向地址[{}]发起 [{}] 请求", url, reqBase.getMethod());
        log.info("--->>请求头为{}", JSON.toJSONString(headers));
        long startTime = System.currentTimeMillis();
        CloseableHttpClient closeableHttpClient = getHttpClient();
        if(encode == null){
            encode = "utf-8";
        }
        HttpPost httpost = new HttpPost(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("响应返回值是------>>>>>");
        log.info(content);
        long endTime = System.currentTimeMillis();
        log.info("--->>请求执行完毕，耗费时长：{} 秒", (endTime - startTime) / 1000);
        return content;
    }


    //获取客户HttpClient
    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(config)
                // 设置重试次数
                .setRetryHandler(
                        new DefaultHttpRequestRetryHandler(2,
                                false))
                .setConnectionManagerShared(true).build();
        return httpClient;

    }

}
