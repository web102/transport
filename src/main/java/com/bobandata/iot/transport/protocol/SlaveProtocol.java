package com.bobandata.iot.transport.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 10:58 2019/1/31.
 */
public abstract class SlaveProtocol implements ISlaveProtocol{

    @Override
    public void channelRegister(Channel channel){ }

    @Override
    public abstract void channelRead(Channel paramChannel, Object paramObject);

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public abstract void installProtocolFilter(ChannelPipeline paramChannelPipeline);
}
