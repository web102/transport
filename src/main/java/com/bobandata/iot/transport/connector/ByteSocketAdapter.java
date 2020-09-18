package com.bobandata.iot.transport.connector;

import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.frame.SingleByteFrame;
import com.bobandata.iot.transport.protocol.ClientByteProtocol;
import com.bobandata.iot.transport.util.ByteArrayUtils;
import com.bobandata.iot.transport.util.FinalConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lizhipeng
 * @Description: 关于客户端的一个简单实现，用于收发数据
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:52 2018/11/3.
 */
public class ByteSocketAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ByteSocketAdapter.class);
    private String hostname;
    private int port;
    public SocketAdapter client;
    public AtomicInteger sendNum = new AtomicInteger(0);

    public ByteSocketAdapter(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
        init(hostname, port);
    }

    //初始化连接
    public void init(String hostname, int port){
        client = new SocketAdapter(hostname, port, new ClientByteProtocol(), FinalConst.LONG_CONNECT);
        client.connect();
        if(client != null){
            client.setSuccess(true);
            this.client = client;
        }
    }

    //发送消息无应答
    public byte sendMessage(byte[] content){
        if(!client.isSuccess()){
            return FinalConst.CONNECT_FAIL;
        }
        EasyByteFrame easyByteFrame = new EasyByteFrame(content);
        try {
            SingleByteFrame singleByteFrame = (SingleByteFrame)client.getBody(easyByteFrame);
            if(singleByteFrame != null){
                return (byte)singleByteFrame.getSingleByte();
            }else{
                return FinalConst.STATUS_BYTE_FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sendNum.incrementAndGet();
        }
        return FinalConst.STATUS_BYTE_FAIL;
    }

    public byte sendMessage(Object obj){
        byte[] content = ByteArrayUtils.objectToBytes(obj);
        return this.sendMessage(content);
    }

    //关闭连接
    public void disconnect(){
        client.disconnect();
    }

    //获取连接状态
    public boolean isSuccess(){
        return client.isSuccess();
    }

    public int getSendNum() {
        return sendNum.get();
    }

}
