package com.bobandata.iot.transport.protocol;

import io.netty.channel.ChannelPipeline;

/**
 * @Author: lizhipeng
 * @Description: 规约接口
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:29 2018/11/15.
 */
public interface  IProtocol {
  public  String getName();

  public  String getVersion();

  public  void installProtocolFilter(ChannelPipeline paramChannelPipeline);

}
