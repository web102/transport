package com.bobandata.iot.transport.protocol;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 18:56 2019/1/15.
 */
public interface ITaskExecutor {

    public void execute(ITaskParam taskItem) throws Exception;

    public void save();

}
