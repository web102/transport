package com.bobandata.iot.transport.connector;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 19:05 2018/12/13.
 */
public interface IChannel {

    public void init(ICommChannel adapter);

    public void connect();

    public Object getBody(Object message);

    public void disconnect();

}
