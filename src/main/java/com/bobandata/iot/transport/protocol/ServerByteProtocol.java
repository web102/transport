package com.bobandata.iot.transport.protocol;

import com.bobandata.iot.transport.acceptor.handler.ByteMsgHandle;
import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.frame.SingleByteFrame;
import com.bobandata.iot.transport.coder.ByteToMessageDecoder;
import com.bobandata.iot.transport.coder.MessageToByteEncoder;
import com.bobandata.iot.transport.util.FinalConst;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 16:07 2018/11/3.
 */
public class ServerByteProtocol extends SlaveProtocol{

    private ByteMsgHandle serverByteMsgHandle;

    public ServerByteProtocol(ByteMsgHandle serverByteMsgHandle){
        this.serverByteMsgHandle = serverByteMsgHandle;
    }

    @Override
    public String getName() {
        return "测试";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public void installProtocolFilter(ChannelPipeline pipeline) {
        pipeline.addLast("decoder", new ByteToMessageDecoder());
        pipeline.addLast("encoder", new MessageToByteEncoder());
    }

    @Override
    public void channelRead(Channel paramChannel, Object msgObj) {
        //判断接收的消息类型是否为普通结构
        if(msgObj instanceof EasyByteFrame) {
            EasyByteFrame msg = (EasyByteFrame) msgObj;
            boolean handleMsgStatus = serverByteMsgHandle.HandleMsg(msg.getContent());
            //如果消息处理成功，返回客户端状态true
            if (handleMsgStatus) {
                SingleByteFrame status = new SingleByteFrame(FinalConst.STATUS_BYTE_SUCCESS);
                paramChannel.write(status);
            } else {
                SingleByteFrame status = new SingleByteFrame(FinalConst.STATUS_BYTE_FAIL);
                paramChannel.write(status);
            }
        }
    }
}
