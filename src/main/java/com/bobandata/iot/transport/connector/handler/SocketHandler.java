package com.bobandata.iot.transport.connector.handler;

import com.bobandata.iot.transport.connector.SocketAdapter;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lizhipeng
 * @Description:TCP协议，消息处理类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:55 2018/12/3.
 */
@ChannelHandler.Sharable
public class SocketHandler extends ChannelInboundHandlerAdapter  {
    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private ChannelHandlerContext ctx;
    private LinkedBlockingQueue<Object> queue =  new LinkedBlockingQueue<>(1);
    private SocketAdapter imConnection;

    public SocketHandler(SocketAdapter imConnection){
        this.imConnection = imConnection;
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public void sendMessage(Object message){
        this.ctx.writeAndFlush(message);
    }

    public Object getData() throws InterruptedException {
        return queue.poll(15, TimeUnit.SECONDS);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        this.queue.poll();
        this.queue.add(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.imConnection.getProtocol().isClose();
        logger.info("CONNECT IS SHUTDOWN...");
        imConnection.setSuccess(false);
        //使用过程中断线重连
//        final EventLoop eventLoop = ctx.channel().eventLoop();
//        eventLoop.schedule(new Runnable() {
//            @Override
//            public void run() {
//                imConnection.connect();
//            }
//        }, 5L, TimeUnit.SECONDS);
//        super.channelInactive(ctx);
    }
}