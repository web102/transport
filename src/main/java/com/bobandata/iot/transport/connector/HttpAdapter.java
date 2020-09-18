package com.bobandata.iot.transport.connector;

import com.bobandata.iot.transport.connector.handler.HttpHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: lizhipeng
 * @Description: http协议连接器
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 12:36 2018/12/3.
 */
public class HttpAdapter implements IChannel{

    private static final Logger logger = LoggerFactory.getLogger(SocketAdapter.class);

    private HttpHandler clientHandler = new HttpHandler();
    private String url;
    private URI uri;
    private EventLoopGroup loopGroup;

    public HttpAdapter(String url) {
        this.url = url;
    }

    @Override
    public void init(ICommChannel adapter) {

    }

    /**
     *建立连接
     */
    public void connect(){
        try {
            this.uri = new URI(this.url);
        } catch (URISyntaxException e) {
            logger.error("HTTP URI SYNTAX EXCEPTION ..."+e.getMessage());
        }
        loopGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        ((Bootstrap) ((Bootstrap) b.group(loopGroup)).channel(NioSocketChannel.class)).handler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new ChannelHandler[]{new HttpRequestEncoder()})
                        .addLast(new ChannelHandler[]{new HttpResponseDecoder()})
                        .addLast(new ChannelHandler[]{HttpAdapter.this.clientHandler});
            }

        });
        Channel channel = null;
        try {
            channel = b.connect(this.uri.getHost(), this.uri.getPort() < 0 ? 80 : this.uri.getPort()).sync().channel();
            while (!channel.isActive())
                Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.error("HTTP CONNECT EXCEPTION ..."+e.getMessage());
        }

    }

    /**
     * 请求数据
     * @param message
     * @return
     */
    public Object getBody(Object message){
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, this.uri.toASCIIString());
        request.headers().set("Host", this.uri.getHost());
        request.headers().set("Connection", "keep-alive");
        request.headers().set("Content-Length", Integer.valueOf(request.content().readableBytes()));
        ChannelPromise promise = this.clientHandler.sendMessage(request);
        try {
            promise.await();
        } catch (InterruptedException e) {
            logger.error("HTTP CONNECT EXCEPTION ..."+e.getMessage());
        }
        return this.clientHandler.getData();
    }

    /**
     * 关闭HTTP连接
     */
    public void disconnect()
    {
        this.loopGroup.shutdownGracefully();
    }
}
