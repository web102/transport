package com.bobandata.iot.transport.protocol;

import com.bobandata.iot.transport.coder.ByteToMessageDecoder;
import com.bobandata.iot.transport.coder.MessageToByteEncoder;
import com.bobandata.iot.transport.connector.handler.HeartBeatReqHandler;
import com.bobandata.iot.transport.frame.IFrame;
import com.bobandata.iot.transport.util.TaskParam;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import javax.websocket.Session;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 16:07 2018/11/3.
 */
public class ClientByteProtocol extends IMasterProtocol {
    @Override
    public String getName() {
        return "测试";
    }

    @Override
    public String getVersion() {
        return "v1.0";
    }

    @Override
    public void installProtocolFilter(ChannelPipeline pipeline) {
        pipeline.addLast("decoder", new ByteToMessageDecoder());
        pipeline.addLast("encoder", new MessageToByteEncoder());
        /**
         * 第一个参数 60 表示读操作空闲时间
         * 第二个参数 20 表示写操作空闲时间
         * 第三个参数 60*10 表示读写操作空闲时间
         * 第四个参数 单位/秒
         */
        pipeline.addLast("ping", new IdleStateHandler(30, 10, 60, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatReqHandler());
    }


    @Override
    public void init(TaskParam taskParam, Session session) throws Exception {

    }

    @Override
    public void executeTask(TaskParam taskParam) throws Exception {

    }

    @Override
    public IFrame sendMsg(IFrame request) {
        return null;
    }
}
