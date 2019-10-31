package RequestHandler;

import Config.Config;
import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;

public class CmdHandler extends MyRequestHanlder {
    @Override
    public void handlerRequest(SocketInfo socketInfo, ChannelHandlerContext ctx) {
        if (socketInfo.getResultCode() / 1000 == 6) {
            System.out.println("CmdHandler handle");
            switch (socketInfo.getResultCode()) {
                case Config.Request_Code_Result_Code_Cmd:
                    redisUtil.setTerminalCmdInfo(ctx.channel().id().toString(),socketInfo.getResultMsg());
            }
        } else {
            successor.handlerRequest(socketInfo, ctx);
        }
    }
}
