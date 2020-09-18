package com.bobandata.iot.transport.util;

/**
 * @Author: lizhipeng
 * @Description: 通道类型
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 19:21 2018/12/13.
 */
public enum EChannelType {

    UNKNOWN(0),

    ETHERNET(1),

    SERIAL_PORT(2),

    DIAL(3),

    LISTENER_SERVER(4),

    HTTP(5);

    private int value;

    EChannelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
