package com.bobandata.iot.transport.acceptor.handler;

import com.bobandata.iot.transport.util.ByteArrayUtils;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 14:33 2018/11/20.
 */
public abstract class ObjectMsgHandle<T> implements ByteMsgHandle {

    @Override
    public boolean HandleMsg(byte[] content) {
        T t = ByteArrayUtils.bytesToObject(content);
        return HandleObjMsg(t);
    }

    public abstract boolean HandleObjMsg(T t);

}
