package com.bobandata.iot.transport.coder;

import com.bobandata.iot.transport.frame.IFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageToByteEncoder extends io.netty.handler.codec.MessageToByteEncoder {

    private static final Logger logger = LoggerFactory.getLogger(MessageToByteEncoder.class);

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof IFrame) {
            IFrame iframe = (IFrame) o;
            byteBuf.writeBytes(iframe.getBuffer());
        }
    }
}
