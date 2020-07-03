package com.tfp.reg.enums;


import org.apache.http.client.methods.*;

/**
 * description 请求类型枚举
 * author chenxi
 * date 2019/10/28
 */
public enum RequestType {

    POST {
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpPost(url);
        }
    },
    GET {
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpGet(url);
        }
    },
    DELETE {
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpDelete(url);
        }
    },
    PUT {
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpPut(url);
        }
    };

    public abstract HttpRequestBase getHttpType(String url);

}
