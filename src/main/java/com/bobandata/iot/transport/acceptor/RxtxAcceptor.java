package com.bobandata.iot.transport.acceptor;

import com.bobandata.iot.transport.acceptor.handler.RxtxHandler;
import com.bobandata.iot.transport.connector.SocketAdapter;
import com.bobandata.iot.transport.connector.handler.RxtxSocketHandler;
import com.bobandata.iot.transport.protocol.ISlaveProtocol;
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

public class RxtxAcceptor {

    private static final Logger logger = LoggerFactory.getLogger(SocketAdapter.class);

    private EventLoopGroup loopGroup = new OioEventLoopGroup(1, new DefaultThreadFactory("server2", true));;
    private RxtxHandler rxtxHandler = new RxtxHandler();
    private ISlaveProtocol slaveProtocol;
    private Channel channel;
    private int baudrate;
    private short parityBit;
    private short stopBit;
    private short dataBit;
    private String serialport;

    public RxtxAcceptor(){

    }

    public RxtxAcceptor(String serialport, int baudrate, short dataBit, short parityBit, short stopBit, ISlaveProtocol slaveProtocol){
        this.serialport = serialport;
        this.baudrate = baudrate;
        this.dataBit = dataBit;
        this.parityBit = parityBit;
        this.stopBit = stopBit;
        this.slaveProtocol = slaveProtocol;
    }

    /**
     * 建立连接
     */
    public void connect() {
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
                RxtxAcceptor.this.rxtxHandler.setProtocol(RxtxAcceptor.this.slaveProtocol);
                RxtxAcceptor.this.slaveProtocol.installProtocolFilter(pipeline);
                pipeline.addLast(RxtxAcceptor.this.rxtxHandler);
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
            logger.info(serialport+" startup success");
        } catch (InterruptedException e) {
            logger.error(serialport+" startup fail");
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        this.loopGroup.shutdownGracefully();
    }

}
