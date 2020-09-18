package com.bobandata.iot.transport.connector.handler;

import com.bobandata.iot.transport.frame.SingleByteFrame;
import com.bobandata.iot.transport.util.FinalConst;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description:心跳消息处理类
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:55 2018/12/3.
 */
public class HeartBeatReqHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatReqHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.debug("READER IDLE ....");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.debug("WRITER IDLE ....");
                SingleByteFrame status = new SingleByteFrame(FinalConst.HEART_BEAT);
                ctx.writeAndFlush(status);
            }
        }
    }


}
