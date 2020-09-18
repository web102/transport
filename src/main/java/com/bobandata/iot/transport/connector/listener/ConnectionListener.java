package com.bobandata.iot.transport.connector.listener;

import com.bobandata.iot.transport.connector.SocketAdapter;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lizhipeng
 * @Description: 连接状态监听类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 12:36 2018/12/3.
 */
public class ConnectionListener implements ChannelFutureListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionListener.class);

    private SocketAdapter imConnection;

    public ConnectionListener(SocketAdapter imConnection){
        this.imConnection = imConnection;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    logger.info("[TCP SERVER IP]" + imConnection.getHostname() + "[TCP SERVER PORT]" + imConnection.getPort() + " CONNECT FAIL! TRY AGAIN ...");
                    imConnection.connect();
                }
            }, 5L, TimeUnit.SECONDS);
        } else {
            imConnection.setSuccess(true);
            logger.info("[TCP SERVER IP]" + imConnection.getHostname() + "[TCP SERVER PORT]" + imConnection.getPort() + " CONNECT SUCCESS!");
        }
    }

}
