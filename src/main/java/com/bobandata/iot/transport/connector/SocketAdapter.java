 package com.bobandata.iot.transport.connector;

 import com.bobandata.iot.transport.connector.handler.SocketHandler;
 import com.bobandata.iot.transport.connector.listener.ConnectionListener;
 import com.bobandata.iot.transport.protocol.IMasterProtocol;
 import com.bobandata.iot.transport.util.FinalConst;
 import io.netty.bootstrap.Bootstrap;
 import io.netty.channel.*;
 import io.netty.channel.nio.NioEventLoopGroup;
 import io.netty.channel.socket.nio.NioSocketChannel;
 import io.netty.util.concurrent.DefaultThreadFactory;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import java.net.InetSocketAddress;

 /**
  * @Author: lizhipeng
  * @Description: TCP协议连接器
  * @Company: 上海博般数据技术有限公司
  * @Date: Created in 12:36 2018/12/3.
  */
public class SocketAdapter implements IChannel{

    private static final Logger logger = LoggerFactory.getLogger(SocketAdapter.class);
    private EventLoopGroup loopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("server2", true));;
    private String hostname;
    private int port;
    private InetSocketAddress address;
    private SocketHandler socketHandler = new SocketHandler(this);
    private IMasterProtocol protocol;
    private boolean isSuccess = false;
    private int connectType;

    public void init(ICommChannel adapter) {

    }

    public SocketAdapter(){

    }

    public SocketAdapter(String hostname, int port, IMasterProtocol protocol){
        this(hostname,port,protocol, FinalConst.SHORT_CONNECT);
    }

    public SocketAdapter(String hostname, int port, IMasterProtocol protocol, int connectType)
    {
     this.hostname = hostname;
     this.port = port;
     this.protocol = protocol;
     this.connectType = connectType;
    }

     /**
      * 建立连接
      */
    public void connect()
    {
     this.address = new InetSocketAddress(this.hostname, this.port);
     Bootstrap b = new Bootstrap();
     ((b.group(this.loopGroup)).channel(NioSocketChannel.class)).handler(new ChannelInitializer()
     {
         @Override
         protected void initChannel(Channel ch) throws Exception {
             ChannelPipeline pipeline = ch.pipeline();
             SocketAdapter.this.protocol.installProtocolFilter(pipeline);
             pipeline.addLast(SocketAdapter.this.socketHandler);
         }
     });
     if(this.connectType == FinalConst.SHORT_CONNECT){
         try {
             b.connect(this.address.getHostString(), this.address.getPort()).sync();
         } catch (InterruptedException e) {
             logger.error("TCP CLIENT CONNECT ERROR ..."+e.getMessage());
         }
     }else{
         ChannelFuture f = b.connect(this.address.getHostString(), this.address.getPort());
         f.addListener(new ConnectionListener(this));
     }
    }

     /**
      * 关闭连接
      */
    public void disconnect()
    {
     this.loopGroup.shutdownGracefully();
    }

     /**
      * 请求数据
      * @param message
      * @return
      */
    public Object getBody(Object message) {
        this.socketHandler.sendMessage(message);
        try {
            return this.socketHandler.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

     public String getHostname() {
         return hostname;
     }

     public int getPort() {
         return port;
     }

     public boolean isSuccess() {
         return isSuccess;
     }

     public void setSuccess(boolean success) {
         isSuccess = success;
     }

     public IMasterProtocol getProtocol() {
         return protocol;
     }

     public void setProtocol(IMasterProtocol protocol) {
         this.protocol = protocol;
     }
 }