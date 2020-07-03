package com.tfp.reg.studyreg.test;

/**
 * Author: ph
 * Date: 2020/6/22
 * Time: 2:03
 * Description:感知下载进度的接口
 */
public interface Progress {

    String  onProgress(Double d );

    String onFinish();

    String  onErr();

}
