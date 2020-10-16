package com.bobandata.iot.transport.protocol;

import com.bobandata.iot.transport.frame.IFrame;
import com.bobandata.iot.transport.util.TaskParam;

import javax.websocket.Session;

/**
 * @Author: lizhipeng
 * @Description: 主站规约接口
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:29 2018/11/15.
 */
public abstract class IMasterProtocol implements IProtocol{
    private TaskParam taskParam = new TaskParam();

    public abstract void init(TaskParam taskParam , Session session) throws Exception;
    public abstract void executeTask(TaskParam taskParam ) throws Exception;

    public abstract IFrame sendMsg(IFrame request) throws Exception;

    public TaskParam getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(TaskParam taskParam) {
        this.taskParam = taskParam;
    }

    public void isClose(){}
}
