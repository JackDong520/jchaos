package RequestHandler;

import io.netty.channel.ChannelHandlerContext;
import javabean.SocketInfo;
import redis.RedisUtil;

public abstract class MyRequestHanlder {
    public MyRequestHanlder successor;
    protected RedisUtil redisUtil;

    public MyRequestHanlder() {
        redisUtil= new RedisUtil();
    }

    public abstract void handlerRequest(SocketInfo socketInfo , ChannelHandlerContext ctx);
}
