package com.bobandata.iot.transport.connector;

import com.bobandata.iot.transport.connector.handler.RxtxSocketHandler;
import com.bobandata.iot.transport.protocol.IProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lizhipeng
 * @Description: 串口协议连接器
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 12:36 2018/12/3.
 */
public class RxtxSocketAdapter implements IChannel{

    private static final Logger logger = LoggerFactory.getLogger(SocketAdapter.class);

    private EventLoopGroup loopGroup = new OioEventLoopGroup(1, new DefaultThreadFactory("server2", true));;
    private RxtxSocketHandler socketHandler = new RxtxSocketHandler(this);
    private IProtocol protocol;
    private Channel channel;
    private int baudrate;
    private short parityBit;
    private short stopBit;
    private short dataBit;
    private String serialport;

    public RxtxSocketAdapter(){

    }

    public RxtxSocketAdapter(String serialport, int baudrate, short dataBit, short parityBit, short stopBit, IProtocol protocol) {
        this.serialport = serialport;
        this.baudrate = baudrate;
        this.dataBit = dataBit;
        this.parityBit = parityBit;
        this.stopBit = stopBit;
        this.protocol = protocol;
    }

    @Override
    public void init(ICommChannel adapter) {

    }

    /**
     * 建立连接
     */
    public void connect()
    {
        RxtxDeviceAddress address = new RxtxDeviceAddress(serialport);
        Bootstrap b = new Bootstrap();
        b.group(this.loopGroup).channelFactory(new ChannelFactory<Channel>() {
            @Override
            public Channel newChannel() {
                return channel;
            }
        }).handler(new ChannelInitializer()
        {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                RxtxSocketAdapter.this.protocol.installProtocolFilter(pipeline);
                pipeline.addLast(RxtxSocketAdapter.this.socketHandler);
            }
        });
        channel = new RxtxChannel();
        RxtxChannelConfig config = (RxtxChannelConfig)channel.config();
        //波特率
        config.setBaudrate(baudrate);
        //数据位
        config.setDatabits(RxtxChannelConfig.Databits.valueOf(dataBit));
        //校验位
        config.setParitybit(RxtxChannelConfig.Paritybit.valueOf(parityBit));
        //停止位
        config.setStopbits(RxtxChannelConfig.Stopbits.valueOf(stopBit));
        try {
            //串口的端口
            b.connect(address).sync();
        } catch (InterruptedException e) {
            logger.error("connect error");
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        this.loopGroup.shutdownGracefully();
    }

    /**
     * 请求数据
     * @param message
     * @return
     */
    public Object getBody(Object message) {
        Object data = null;
        ChannelPromise promise = this.socketHandler.sendMessage(message);
        if(promise != null){
            try {
                promise.await(30000L);
            } catch (InterruptedException e) {
                logger.error("RXTX REQUEST EXCEPTION ..."+e.getMessage());
            }
            if(promise.isSuccess()) {
                data = this.socketHandler.getData();
                return data;
            }else{
                data = this.socketHandler.getData();
                if(data != null){
                    return data;
                }
            }
        }
        return data;
    }

}