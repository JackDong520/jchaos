package broadcast;

import org.junit.Test;

import java.io.IOException;
import java.net.*;

public class Broadcast {

    public void run_send() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4000);
        byte[] arr = new byte[1024];
        DatagramPacket packet = new DatagramPacket(arr, arr.length);
        //3 当程序运行起来之后,receive方法会一直处于监听状态
        serverSocket.receive(packet);
        //从包中将数据取出
        byte[] arr1 = packet.getData();
        System.out.println(new String(arr1));
        //4
        serverSocket.close();
    }

    public void run_udp() throws IOException {
        //1.创建对象
        //构造数据报套接字并将其绑定到本地主机上任何可用的端口。
        DatagramSocket socket = new DatagramSocket();
        //2.打包
        byte[] arr = "wdnmd".getBytes();
        //四个参数: 包的数据  包的长度  主机对象  端口号
        DatagramPacket packet = new DatagramPacket
                (arr, arr.length, InetAddress.getByName("127.0.0.1"), 4000);

        //3.发送
        socket.send(packet);

        //4.关闭资源
        socket.close();
    }

    @Test
    public void test() throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    run_send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Thread.sleep(2 * 1000);
        run_udp();
        Thread.sleep(2 * 1000);
    }
}
