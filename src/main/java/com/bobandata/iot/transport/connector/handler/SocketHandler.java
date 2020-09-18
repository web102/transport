package com.bobandata.iot.transport.connector.handler;

import com.bobandata.iot.transport.connector.SocketAdapter;
import com.bobandata.iot.transport.protocol.IMasterProtocol;
import com.bobandata.iot.transport.util.EmptyUtil;
import io.netty.channel.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description:TCP协议，消息处理类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:55 2018/12/3.
 */
@ChannelHandler.Sharable
public class SocketHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private Object data;
    private AtomicInteger count = new AtomicInteger(0);
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    private SocketAdapter imConnection;

    public SocketHandler(SocketAdapter imConnection){
        this.imConnection = imConnection;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public ChannelPromise sendMessage(Object message){
        int tryCount = this.count.get();
        if (this.ctx == null) {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                logger.error("TCP REQUEST EXCEPTION ..."+e.getMessage());
            }
            this.count.incrementAndGet();
            return tryCount <= 3 ? sendMessage(message) : null;
        }
        this.count = new AtomicInteger(0);
        Channel channel = this.ctx.writeAndFlush(message).channel();
        if(channel.isActive() || channel.isOpen()){
            this.promise =  channel.newPromise();
        }
        return this.promise;
    }

    public Object getData() {
        Object data = this.data;
        this.data=null;
        return data;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (EmptyUtil.isNotEmpty(msg) && this.promise != null) {
            this.data = msg;
            atomicInteger.incrementAndGet();
            try{
                this.promise.setSuccess();
            }catch (Exception e){
//                logger.error("PROMISE SET FAIL..."+e.getMessage());
            }
        }
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


    public static AtomicInteger getPosition(){
        return atomicInteger;
    }

}