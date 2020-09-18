package com.bobandata.iot.transport.util;

import com.bobandata.iot.transport.frame.IFrame;
import com.sun.org.apache.bcel.internal.classfile.Code;

public class WebMessage {
    private int code;                   //状态码

    private String message;             //状态信息
    private String requestHex;          //请求报文 16进制字符串
    private String responseHex;         //响应报文 16进制字符串
    private String requestExplain;      //请求报文 解析
    private String responseExplain;     //响应报文 解析

    public WebMessage(int code, String message){
        this.code=code;
        this.message=message;
    }

    public WebMessage(IFrame request, IFrame response){
        this.code= Code.INFFO.getCode();
        this.requestHex=request.toHexString();
        this.requestExplain=request.toExplain();
        if(response!=null) {
            this.responseHex = response.toHexString();
            this.responseExplain = response.toExplain();
        }else {
            this.responseHex = "解析异常！";
            this.responseExplain = "解析异常！";
        }
    }

    public enum Code{
        ERROR(-1),      //异常
        NORMAL(0),      //普通可忽略消息
        INFFO(1),       //报文消息
        NOTIFY(2),      //通知消息
        CLOSE(3)        //关闭websocket连接
        ;
        private int code;
        Code(int code) {
            this.code=code;
        }

        public int getCode() {
            return code;
        }
    }

    public String toJson() {
        return ("{" +
                "\"code\":\"" + code +'\"'+
                ",\"message\":\"" + message + '\"' +
                ",\"requestHex\":\"" + requestHex + '\"' +
                ",\"responseHex\":\"" + responseHex + '\"' +
                ",\"requestExplain\":\"" + requestExplain + '\"' +
                ",\"responseExplain\":\"" + responseExplain + '\"' +
                '}').replaceAll("\n","-n").replaceAll("\t","-t");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestHex() {
        return requestHex;
    }

    public void setRequestHex(String requestHex) {
        this.requestHex = requestHex;
    }

    public String getResponseHex() {
        return responseHex;
    }

    public void setResponseHex(String responseHex) {
        this.responseHex = responseHex;
    }

    public String getRequestExplain() {
        return requestExplain;
    }

    public void setRequestExplain(String requestExplain) {
        this.requestExplain = requestExplain;
    }

    public String getResponseExplain() {
        return responseExplain;
    }

    public void setResponseExplain(String responseExplain) {
        this.responseExplain = responseExplain;
    }
}
