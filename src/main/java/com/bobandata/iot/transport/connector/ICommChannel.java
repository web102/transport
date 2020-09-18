package com.bobandata.iot.transport.connector;

import com.bobandata.iot.transport.util.EChannelType;

import java.util.Date;

/**
 * @Author: lizhipeng
 * @Description: 通讯设备抽象接口
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 19:18 2018/12/13.
 */
public interface ICommChannel{

    /**
     * 通道ID。
     *
     * @return
     */
    public Long getChannelId();

    /**
     * 通道名称。
     *
     * @return
     */
    public String getChannelName();

    /**
     * 采集点ID。
     *
     * @return
     */
    public Long getAcquiredId();

    /**
     *
     * <br>
     * 创建时间:2012-4-12上午11:10:52 <br>
     * 创建的目的: 通道参数 <br>
     * 实现的功能: <br>
     * 参数描述: @return <br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
	/*public String getParam();*/

    /**
     * 失败重试次数。
     *
     * @return
     */
    public Integer getTryTimes();

    /**
     * 最后通讯时标。
     *
     * @return
     */
    public Date getCommTimeTag();

    /**
     * 设置最后通讯时间。
     *
     * @param date
     */
    public void setCommTimeTag(Date date);

    /**
     * 通讯最大等待时间。
     *
     * @return
     */
    public Integer getMaxCommDelay();

    /**
     * 使用状况
     *
     * @return
     */
    public Short getIsInUse();

    /**
     * 设备当前工作的位置。
     *
     * @return
     */
    public Long getServiceId();

    /**
     * 设置当前工作的位置。
     *
     * @param serviceId
     */
    public void setServiceId(Long serviceId);

    /**
     * 通讯状态。
     *
     * @return
     */
    public Long getStatus();

    /**
     * 设置通道状态。
     *
     * @param status
     *            当前状态。
     */
    public void setStatus(Long status);

    /**
     * <br>
     * 创建时间:2012-5-7下午06:06:29 <br>
     * 创建的目的: 获取通道的信息描述。 <br>
     * 实现的功能: <br>
     * 参数描述: @return <br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
    public String channelInfo();

    /**
     *
     * <br>
     * 创建时间:2013-1-20下午06:57:56 <br>
     * 创建的目的: 获取通道的类型 <br>
     * 实现的功能: <br>
     * 参数描述: @return <br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
    public EChannelType channelType();

    /**
     *
     * <br>
     * 创建时间:2013-1-20下午06:57:56 <br>
     * 创建的目的: 获取通道的优先级 <br>
     * 实现的功能: <br>
     * 参数描述: @return <br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
    public Long getPri();

    /**
     *
     * <br>
     * 创建时间:2016-1-19下午01:52:56 <br>
     * 创建的目的: 获取通道最后通讯成功的时间 <br>
     * 实现的功能: <br>
     * 参数描述: @return 最后通讯成功时间<br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
    public  Date getLastSuccessTimeTag();

    /**
     *
     * <br>
     * 创建时间:2016-1-19下午01:52:56 <br>
     * 创建的目的: 获取通道最后通讯成功的时间 <br>
     * 实现的功能: <br>
     * 参数描述: @param timeTag 最后通讯成功时间<br>
     * 修改日期: <br>
     * 修改内容: <br>
     * 修改人员:
     */
    public  void setLastSuccessTimeTag(Date timeTag);
}
