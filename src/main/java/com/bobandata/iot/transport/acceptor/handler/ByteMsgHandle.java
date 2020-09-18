package com.bobandata.iot.transport.acceptor.handler;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:43 2018/11/3.
 */
public interface ByteMsgHandle {

    public boolean HandleMsg(byte[] content);

}
