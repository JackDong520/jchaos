package RequestHandler;

import Config.Config;
import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;

import javax.sound.midi.Soundbank;

public class OsInfoHandler extends MyRequestHanlder {

    @Override
    public void handlerRequest(SocketInfo socketInfo , ChannelHandlerContext ctx) {
        if (socketInfo.getResultCode() / 1000 == 1) {
            System.out.println("OsInfoHandler handle");
            switch (socketInfo.getResultCode()) {
                case Config.Result_Code_ReturnOsInfo:
                    redisUtil.addTerminalOsinfo(ctx.channel().id().toString(), socketInfo.getResultMsg());
            }

        } else {
            if (successor == null) {
                System.out.println("no Handler handler this request");
            } else {
                successor.handlerRequest(socketInfo,ctx);
            }
        }
    }
}
