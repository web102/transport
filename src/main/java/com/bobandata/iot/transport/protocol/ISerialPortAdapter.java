package com.bobandata.iot.transport.protocol;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 14:48 2019/1/14.
 */
public interface ISerialPortAdapter extends ICommAdapter {
    Integer getPort();

    Long getCommParamId();

    Long getTsId();
}
