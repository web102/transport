package com.bobandata.iot.transport.connector.handler;

import com.bobandata.iot.transport.connector.RxtxSocketAdapter;
import com.bobandata.iot.transport.util.EmptyUtil;
import io.netty.channel.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description:串口协议，消息处理类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:55 2018/12/3.
 */
@ChannelHandler.Sharable
public class RxtxSocketHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RxtxSocketHandler.class);
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private Object data;
    private AtomicInteger count = new AtomicInteger(0);
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    private RxtxSocketAdapter imConnection;

    public RxtxSocketHandler(RxtxSocketAdapter imConnection){
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
                logger.error("RXTX REQUEST EXCEPTION ..."+e.getMessage());
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
                //logger.error("RXTX REQUEST EXCEPTION ..."+e.getMessage());
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("CONNECT IS DOWN...");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                imConnection.connect();
            }
        }, 5L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        if(channel.isActive())ctx.close();
    }


    public static AtomicInteger getPosition(){
        return atomicInteger;
    }

}