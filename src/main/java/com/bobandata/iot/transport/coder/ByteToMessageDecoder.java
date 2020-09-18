package com.bobandata.iot.transport.coder;


import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.frame.SingleByteFrame;
import com.bobandata.iot.transport.util.ConvertUtil;
import com.bobandata.iot.transport.util.FinalConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteToMessageDecoder extends io.netty.handler.codec.ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(ByteToMessageDecoder.class);

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decoded = decode(channelHandlerContext, byteBuf);
        if (decoded != null) {
            list.add(decoded);
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        int count = 0 ;
        byte start=in.getByte(0);
        int length = in.readableBytes();
        byte last=in.getByte(length-1);
        //此处是为了处理接收到的数据帧断掉的情况，判断接受到的数据帧是否完整
        if(start== FinalConst.START_BYTE && last==FinalConst.END_BYTE){
            byte[] lengthBytes = new byte[2];
            //获取长度字节
            in.getBytes(1,lengthBytes);
            int lengthLow = ConvertUtil.bytes2int(lengthBytes);
            if(lengthLow == length){
                EasyByteFrame easyByteFrame = new EasyByteFrame(in);
                easyByteFrame.decode();
                return easyByteFrame;
            }else{
                return null;
            }
        }else if(start== FinalConst.STATUS_BYTE_SUCCESS || start==FinalConst.STATUS_BYTE_FAIL){
            SingleByteFrame singleByteFrame = new SingleByteFrame(in);
            singleByteFrame.decode();
            return singleByteFrame;
        }else if(start== FinalConst.HEART_BEAT){
            SingleByteFrame singleByteFrame = new SingleByteFrame(in);
            singleByteFrame.decode();
            return null;
        }else{
            return null;
        }
    }
}
