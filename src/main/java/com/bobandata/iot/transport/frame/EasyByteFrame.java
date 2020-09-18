package com.bobandata.iot.transport.frame;

import com.bobandata.iot.transport.util.ConvertUtil;
import com.bobandata.iot.transport.util.FinalConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 17:54 2018/11/3.
 */
public class EasyByteFrame implements IFrame{
    private static final Logger logger = LoggerFactory.getLogger(SingleByteFrame.class);

    private int length;
    private byte[] content;
    private ByteBuf in;

    EasyByteFrame(){

    }

    public EasyByteFrame(byte[] content){
        this.content = content;
        this.length = content.length+4;
        this.in = Unpooled.buffer(length);
        try {
            this.encode();
        } catch (Exception e) {
            logger.error("EasyByteFrame ENCODE ERROR！！");
        }
    }

    public EasyByteFrame(ByteBuf in){
        this.in = in;
    }

    @Override
    public int encode() throws Exception {
        in.writeByte(FinalConst.START_BYTE);
        //编码报文长度
        byte [] bytes = ConvertUtil.short2bytes(this.length);
        in.writeBytes(bytes);
        //编码报文内容
        in.writeBytes(content);
        in.writeByte(FinalConst.END_BYTE);
        return 0;
    }

    @Override
    public int decode() throws Exception {
        //读消息头
        in.readByte();
        //读长度
        byte[] lengthBytes = new byte[2];
        in.readBytes(lengthBytes);
        int lengthLow = ConvertUtil.bytes2int(lengthBytes);
        this.setLength(lengthLow);
        //读内容
        byte[] contentBytes = new byte[lengthLow-4];
        in.readBytes(contentBytes);
        this.setContent(contentBytes);
        //读消息尾
        in.readByte();
        return 0;
    }

    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public String toExplain() {
        return null;
    }

    @Override
    public ByteBuf getBuffer() {
        return this.in;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public ByteBuf getIn() {
        return in;
    }

    public void setIn(ByteBuf in) {
        this.in = in;
    }
}
