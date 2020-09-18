package com.bobandata.iot.transport.connector.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import java.nio.charset.Charset;

/**
 * @Author: lizhipeng
 * @Description:http协议，消息处理类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:55 2018/12/3.
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data;
    private long readByte;
    private int statusCode;

    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public ChannelPromise sendMessage(Object message) {
        if (this.ctx == null)
            throw new IllegalStateException();
        this.promise = this.ctx.writeAndFlush(message).channel().newPromise();
        return this.promise;
    }

    public String getData() {
        String data = this.data;
        this.data=null;
        return data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ((msg instanceof HttpResponse)) {
            HttpResponse response = (HttpResponse) msg;
            this.statusCode = response.getStatus().code();
        }
        if ((msg instanceof HttpContent) && this.statusCode == 200)  {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            if(buf instanceof EmptyByteBuf)
                return;
            this.readByte = buf.readableBytes();
            this.data = buf.toString(Charset.forName("gb2312"));
            this.promise.setSuccess();
            buf.release();
        }
    }
}