package com.bobandata.iot.transport.acceptor;

import com.bobandata.iot.transport.acceptor.handler.ByteMsgHandle;
import com.bobandata.iot.transport.protocol.ServerByteProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description: 关于服务端的一个简单实现，用于收发数据
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:30 2018/11/3.
 */
public class ByteAcceptor {

    private static final Logger logger = LoggerFactory.getLogger(Acceptor.class);

    private Acceptor acceptor;

    public ByteAcceptor(int inetPort, ByteMsgHandle serverByteMsgHandle){
        this(null, inetPort, serverByteMsgHandle);
    }

    public ByteAcceptor(String inetHost, int inetPort, ByteMsgHandle serverByteMsgHandle){
        ServerByteProtocol protocol = new ServerByteProtocol(serverByteMsgHandle);
        acceptor = new Acceptor(inetHost,inetPort,protocol);
    }

    public void shunDown(){
        acceptor.shutdown();
    }

}
