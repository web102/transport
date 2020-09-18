package com.bobandata.iot.transport.acceptor.handler;

import com.bobandata.iot.transport.protocol.ISlaveProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description: 监听测消息处理器，实现动态配置规约
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:52 2018/11/3.
 */
public class AcceptorHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AcceptorHandler.class);

    //规约接口，参数为监听测规约
    private ISlaveProtocol protocol;

    public AcceptorHandler(ISlaveProtocol protocol) {
        this.protocol = protocol;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();
        protocol.channelRegister(channel);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        this.protocol.channelRead(channel, msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("client "+ctx.channel().remoteAddress().toString()+" exception close ...");
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress()+" shutdown");
    }
}
