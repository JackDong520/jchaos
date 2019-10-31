package Server.Handler;


import Server.NettyChannelMap;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.io.PrintStream;

/**
 * Created by yaozb on 15-4-11.
 */
@ChannelHandler.Sharable
public class MesgHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        super.channelRead(ctx, msg);
    }
}
