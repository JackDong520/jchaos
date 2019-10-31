package netty;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import netty.Handler.MesgHandler;
import redis.RedisUtil;


public final class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;
    private ChannelId SingleId;//保存单个ChannelID信息
    private MesgHandler mesgHandler;
    private RedisUtil redisUtil;


    //���� DefaultChannelGroup ���� �����������ӵĵ� WebSocket channel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final ChannelGroup channelPictureGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        mesgHandler = new MesgHandler(channelGroup);
        redisUtil = new RedisUtil();
        bind();
        init();
    }

    public void init() {
        redisUtil.delALLterminalChannelIDinfo();
    }

    public static ChannelPipeline p;

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

                p = socketChannel.pipeline();
                p.addLast("decoder", new StringDecoder());
                p.addLast("encoder", new StringEncoder());
                p.addLast(mesgHandler);
            }
        });
        System.out.println("----------------------:" + port);
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            System.out.println("server start---------------");
        }

    }

    public MesgHandler getMesgHandler() {
        return mesgHandler;
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException {

        NettyServerBootstrap bootstrap = new NettyServerBootstrap(9999);
        Thread.sleep(1 * 1000);
    }
}
