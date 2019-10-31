package broadcast;

import Config.Config;
import org.junit.Test;

import java.io.IOException;
import java.net.*;

public class MyHandler {

    public MyHandler() {
    }


    public String getBroadcast() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(Config.BroadcastPort);
        byte[] arr = new byte[1024];
        DatagramPacket packet = new DatagramPacket(arr, arr.length);
        //3 当程序运行起来之后,receive方法会一直处于监听状态
        serverSocket.receive(packet);
        //从包中将数据取出
        byte[] arr1 = packet.getData();
        System.out.println(new String(arr1));
        //4
        serverSocket.close();
        return new String(arr1);
    }

    public void send_broadcast(String msg) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] arr = msg.getBytes();
        DatagramPacket packet = new DatagramPacket
                (arr, arr.length, InetAddress.getByName(Config.BroadcastAddress), Config.BroadcastPort);
        socket.send(packet);
        socket.close();
    }
    @Test
    public void test() throws IOException, InterruptedException {



        Thread.sleep(2*1000);
        new MyHandler().send_broadcast("wdnmd");
        Thread.sleep(2*1000);
        new MyHandler().send_broadcast("wdnmd");
        Thread.sleep(2*1000);
        new MyHandler().send_broadcast("wdnmd");
        Thread.sleep(2*1000);
        new MyHandler().send_broadcast("wdnmd");
        Thread.sleep(2*1000);
    }


}
