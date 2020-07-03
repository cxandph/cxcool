package com.tfp.reg.exceptions;

/**
 * Author: ph
 * Date: 2020/6/19
 * Time: 1:36
 * Description:
 */
public class DefineException extends  Exception {

    private int  code ;
    private String errMsg;


    public DefineException(int  code ,String errMsg) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
    public DefineException(String errMsg){
        super(errMsg);
        this.errMsg=errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
