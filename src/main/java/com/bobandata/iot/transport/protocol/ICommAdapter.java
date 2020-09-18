package com.bobandata.iot.transport.protocol;

import com.bobandata.iot.transport.util.EChannelType;

import java.sql.Timestamp;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 14:43 2019/1/14.
 */
public interface ICommAdapter extends IProtocolAdapter {
    Long getChannelId();

    String getChannelName();

    Long getAcquiredId();

    Integer getTryTimes();

    Timestamp getCommTimeTag();

    void setCommTimeTag(Timestamp var1);

    Integer getMaxCommDelay();

    Short getIsInUse();

    Long getServiceId();

    void setServiceId(Long var1);

    Long getStatus();

    void setStatus(Long var1);

    String channelInfo();

    EChannelType channelType();

    Long getPri();

    Timestamp getLastSuccessTimeTag();

    void setLastSuccessTimeTag(Timestamp var1);
}
