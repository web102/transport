package com.bobandata.iot.transport.util;

import com.bobandata.iot.transport.connector.IChannel;
import com.bobandata.iot.transport.protocol.ITaskParam;
import java.util.Date;
import java.util.List;

public class TaskParam implements ITaskParam{
    private int startMark;
    private int endMark;
    private Date startDate;
    private Date endDate;
    private String ipAddress;
    private int ipPort;
    private String restPath;    //规约MasterProtocol启动类路径
    private int taskType;       //规约下的指令
    private IChannel channel;
    private int rad;

    public int getStartMark() {
        return startMark;
    }

    public void setStartMark(int startMark) {
        this.startMark = startMark;
    }

    public int getEndMark() {
        return endMark;
    }

    public void setEndMark(int endMark) {
        this.endMark = endMark;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIpPort() {
        return ipPort;
    }

    public void setIpPort(int ipPort) {
        this.ipPort = ipPort;
    }

    public String getRestPath() {
        return restPath;
    }

    public void setRestPath(String restPath) {
        this.restPath = restPath;
    }

    public IChannel getChannel() {
        return channel;
    }

    public void setChannel(IChannel channel) {
        this.channel = channel;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }
}