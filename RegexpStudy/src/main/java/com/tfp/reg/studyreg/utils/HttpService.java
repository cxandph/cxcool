package com.tfp.reg.studyreg.utils;



import com.tfp.reg.enums.RequestType;
import com.tfp.reg.exceptions.DefineException;
import com.tfp.reg.studyreg.test.Progress;
import org.apache.http.HttpEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * description Http请求service
 * author 宫清
 * date 2019/9/17 20:09
 */
public interface HttpService {

    /**
     * 发送请求
     *
     * @param reqType 请求类型
     * @param url     请求url
     * @param headers 请求头
     * @param param   请求体
     * @return
     */
    String sendHttp(RequestType reqType,
                    String url,
                    Map<String, String> headers,
                    Object param) throws DefineException;

    /**
     * 发送http请求基础版
     * @param reqType 请求类型
     * @param url 请求地址
     * @param headers 请求头
     * @param param 请求参数
     * @param saveDir 下载文件保存地址(当服务端返回的是application/octet-stream时有效)
     * @param progress 下载进度反馈接口
     * @return
     * @throws DefineException
     */
    String sendHttpBase(RequestType reqType,
                    String url,
                    Map<String, String> headers,
                    Object param, String  saveDir, Progress progress) throws DefineException;
    /**
     * 上传文件 构建流方式
     *
     * @param multipartFiles
     * @return
     * @throws DefineException
     */
    HttpEntity uploadMultiFiles(List<File> multipartFiles) throws DefineException;
    String  httpPostForm(String url, Map<String, String> params, Map<String, String> headers, String encode);
}
