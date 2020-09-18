package com.bobandata.iot.transport.acceptor.handler;

import com.bobandata.iot.transport.protocol.ISlaveProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: lizhipeng
 * @Description: 监听测消息处理器，实现动态配置规约
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:52 2018/11/3.
 */
public class RxtxHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AcceptorHandler.class);

    //规约接口，参数为监听测规约
    private ISlaveProtocol protocol;

    public RxtxHandler(){}

    public RxtxHandler(ISlaveProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        protocol.channelRegister(channel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        this.protocol.channelRead( ctx.channel(), msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("client "+ctx.channel().remoteAddress().toString()+" is exception close ...");
        cause.printStackTrace();
    }

    public ISlaveProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(ISlaveProtocol protocol) {
        this.protocol = protocol;
    }
}
