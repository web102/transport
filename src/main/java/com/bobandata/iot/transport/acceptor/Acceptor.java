package com.bobandata.iot.transport.acceptor;

import com.bobandata.iot.transport.acceptor.handler.AcceptorHandler;
import com.bobandata.iot.transport.protocol.ISlaveProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: lizhipeng
 * @Description: 监听测主进程，监听固定的端口接收数据
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 23:52 2018/11/3.
 */
public class Acceptor {

    private static final Logger logger = LoggerFactory.getLogger(Acceptor.class);

    private String inetHost;

    private int inetPort;

    private int bossSize;

    private int workerSize;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ISlaveProtocol protocol;

    //默认无参构造函数
    public Acceptor(){

    }

    /**
     * 无指定IP构造函数，EventLoop默认线程数
     * @param inetPort 端口
     * @param protocol 监听测规约
     */
    public Acceptor(int inetPort, ISlaveProtocol protocol){
        this(null,inetPort,protocol);
    }

    /**
     * 无指定IP构造函数，EventLoop非默认线程数
     * @param inetPort 端口
     * @param protocol 监听测规约
     * @param bossSize bossEventLoop线程个数
     * @param workerSize workerEventLoop线程个数
     */
    public Acceptor(int inetPort, ISlaveProtocol protocol, int bossSize, int workerSize){
        this(null,inetPort,protocol,bossSize,workerSize);
    }

    /**
     * 指定IP构造函数，EventLoop默认线程数
     * @param inetHost 指定IP
     * @param inetPort 端口
     * @param protocol 监听测规约
     */
    public Acceptor(String inetHost, int inetPort, ISlaveProtocol protocol){
        this(inetHost,inetPort,protocol,2,4);
    }

    /**
     *
     * @param inetHost 指定IP
     * @param inetPort 端口
     * @param protocol 监听测规约
     * @param bossSize bossEventLoop线程个数
     * @param workerSize workerEventLoop线程个数
     */
    public Acceptor(String inetHost, int inetPort, ISlaveProtocol protocol, int bossSize, int workerSize){
        this.inetHost = inetHost;
        this.inetPort = inetPort;
        this.bossSize = bossSize;
        this.workerSize = workerSize;
        this.protocol = protocol;
        bossGroup = new NioEventLoopGroup(bossSize, new DefaultThreadFactory("server1", true));
        workerGroup = new NioEventLoopGroup(workerSize, new DefaultThreadFactory("server2", true));
        if(inetHost != null){
            init(inetHost, inetPort, protocol);
        }else{
            init(inetPort, protocol);
        }
    }

    /**
     * 未指定IP初始化方法
     * @param inetPort 端口
     * @param protocol 监听测规约
     */
    public void init(int inetPort, final ISlaveProtocol protocol){
        InetAddress addr = null;
        try {
            //获取本机网络地址
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.error("TCP SERVER GET INETADDRESS ERROR ..." + e.getMessage());
        }
        this.inetHost=addr.getHostAddress().toString();
        this.init(inetHost, inetPort, protocol);
    }

    /**
     *  指定IP初始化方法
     * @param inetHost IP
     * @param inetPort 端口
     * @param protocol 监听测规约
     */
    public void init(String inetHost, int inetPort, final ISlaveProtocol protocol){
        //初始化服务端启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //指定连接处理线程池、消息处理线程池
        bootstrap.group(bossGroup, workerGroup);
        //指定通道类别
        bootstrap.channel(NioServerSocketChannel.class);
        //设置过滤器和消息处理器
        bootstrap.childHandler(new ChannelInitializer() {
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                protocol.installProtocolFilter(pipeline);
                pipeline.addLast(new AcceptorHandler(protocol));
            }
        });
        logger.info("[TCP SERVER IP]" + inetHost + "[TCP SERVER PORT]" + inetPort);
        try {
            //绑定IP、端口
            ChannelFuture f = bootstrap.bind(inetHost, inetPort).sync();
        } catch (InterruptedException e) {
            logger.error("TCP SERVER START ERROR ..." + e.getMessage());
        }
        logger.info("TCP SERVER START...");
        //f.channel().closeFuture().sync();
    }

    /**
     * 停止服务端
     */
    public void shutdown() {
        logger.info("TCP SERVER SHUTDOWN...");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    /**
     * 重启服务
     */
    public Acceptor restart() {
        shutdown();
        try {
            Thread.sleep(1000*20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Acceptor(inetHost, inetPort, protocol, bossSize, workerSize);
    }
}

