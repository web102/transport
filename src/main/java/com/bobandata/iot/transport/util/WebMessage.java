package com.bobandata.iot.transport.util;

import com.bobandata.iot.transport.frame.IFrame;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebMessage {
    private int code;                   //状态码

    private String message;             //状态信息
    private String infoHex;          //请求报文 16进制字符串
    private String infoExplain;      //请求报文 解析

    public WebMessage(int code, String message){
        this.code=code;
        this.message=message;
    }

    public WebMessage(WebMessage.Code code,IFrame info){
        this.code= code.code;
        this.message = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
        this.infoHex =info.toHexString();
        this.infoExplain=info.toExplain();
    }

    public enum Code{
        ERROR(-1),      //异常
        NORMAL(0),      //普通可忽略消息
        REQUEST_INFO(1),       //报文消息
        RESPONSE_INFO(2),      //通知消息
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
                ",\"infoHex\":\"" + infoHex + '\"' +
                ",\"infoExplain\":\"" + infoExplain + '\"' +
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

    public String getInfoHex() {
        return infoHex;
    }

    public void setInfoHex(String infoHex) {
        this.infoHex = infoHex;
    }

    public String getRequestExplain() {
        return infoExplain;
    }

    public void setRequestExplain(String requestExplain) {
        this.infoExplain = requestExplain;
    }

}
