package RequestHandler;

import Config.Config;
import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;

public class NmapHandler extends MyRequestHanlder {

    @Override
    public void handlerRequest(SocketInfo socketInfo, ChannelHandlerContext ctx) {
        if (socketInfo.getResultCode() / 1000 == 3) {
            System.out.println("NmapHandler handle");
            switch (socketInfo.getResultCode()) {
                case Config.Result_Code_NmapInfoList:
                    System.out.println(socketInfo.getResultMsg());

            }

        } else {
            successor.handlerRequest(socketInfo, ctx);
        }
    }
}
