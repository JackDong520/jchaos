package RequestHandler;

import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;

public class NmapHandler extends MyRequestHanlder {

    @Override
    public void handlerRequest(SocketInfo socketInfo , ChannelHandlerContext ctx) {
        if (socketInfo.getResultCode() / 1000 == 3){
            System.out.println("NmapHandler handle");

        }else {
            successor.handlerRequest(socketInfo ,ctx);
        }
    }
}
