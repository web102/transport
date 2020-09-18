package com.bobandata.iot.transport.util;

/**
 * @Author: lizhipeng
 * @Description: 常量
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 19:13 2018/9/1.
 */
public class FinalConst {

    /**
     * 消息开始字节
     */
    public final static byte START_BYTE = 0x68;

    /**
     * 消息结束字节
     */
    public final static byte END_BYTE = 0x16;

    /**
     * 状态字节：成功
     */
    public final static byte STATUS_BYTE_SUCCESS = 0x00;

    /**
     * 状态字节：失败
     */
    public final static byte STATUS_BYTE_FAIL = 0x01;

    /**
     * 状态字节：连接异常
     */
    public final static byte CONNECT_FAIL = 0x02;

    /**
     * 心跳字节
     */
    public final static byte HEART_BEAT = 0x66;

    /**
     * 连接类型:长连接
     */
    public final static int LONG_CONNECT = 0;

    /**
     * 连接类型:短连接
     */
    public final static int SHORT_CONNECT = 1;
}
