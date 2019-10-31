package Server;


import Server.Handler.MesgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;


public final class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;

    //创建 DefaultChannelGroup 用来 保存所有连接的的 WebSocket channel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final ChannelGroup channelPictureGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    public static ChannelPipeline p;

    private void bind() throws InterruptedException {


        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);

        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                p = socketChannel.pipeline();
                p.addLast("decoder", new StringDecoder());
                p.addLast("encoder", new StringEncoder());
                p.addLast(new MesgHandler());


            }
        });


        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            System.out.println("server start---------------");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        NettyServerBootstrap bootstrap = new NettyServerBootstrap(9999);

    }
}
