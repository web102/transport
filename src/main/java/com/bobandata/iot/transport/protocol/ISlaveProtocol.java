package com.bobandata.iot.transport.protocol;

import io.netty.channel.Channel;

/**
 * @Author: lizhipeng
 * @Description: 监听侧规约接口
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:29 2018/11/15.
 */
public interface ISlaveProtocol extends IProtocol {

  public void channelRegister(Channel channel);

  public void channelRead(Channel paramChannel, Object paramObject);

}
