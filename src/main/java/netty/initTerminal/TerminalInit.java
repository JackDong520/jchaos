package netty.initTerminal;


import io.netty.channel.Channel;

public class TerminalInit {
    /**
     * 完成客户端的初始化
     *
     * @param channel
     */
    public void initTerminals(Channel channel) {
        channel.writeAndFlush("getos");
        channel.writeAndFlush("nmap");
    }
}
