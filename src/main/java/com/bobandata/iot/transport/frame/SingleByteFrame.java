package com.bobandata.iot.transport.frame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 单字节数据帧
 * @Param:
 * @Throws:
 * @Return:
 * @Author:lizhipeng
 * @Date:15:19 2018/8/17
 */
public class SingleByteFrame implements IFrame {
    private static final Logger logger = LoggerFactory.getLogger(SingleByteFrame.class);
    private ByteBuf buffer;
    private byte singleByte;

    public SingleByteFrame(ByteBuf buffer) {
        this.buffer = Unpooled.buffer(1);
        buffer.readBytes(this.buffer, 1);
        try {
            this.decode();
        } catch (Exception e) {
            logger.error("SINGLEBYTE DECODE ERROR！");
        }
    }

    public SingleByteFrame(byte status) {
        this.buffer = Unpooled.buffer(1);
        this.singleByte = status;
        try {
            this.encode();
        } catch (Exception e) {
            logger.error("SINGLEBYTE ENCODE ERROR！！");
        }
    }

    public String toHexString() {
        return null;
    }

    public String toExplain() {
        return toHexString();
    }

    public int encode() throws Exception {
        this.buffer.writeByte(this.singleByte);
        return 0;
    }

    public int decode() throws Exception {
        this.singleByte = this.buffer.getByte(0);
        return 0;
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public byte getSingleByte() {
        return this.singleByte;
    }

    public void setSingleByte(byte singleByte) {
        this.singleByte = singleByte;
    }
}
