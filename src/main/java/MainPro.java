import Server.NettyServerBootstrap;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class MainPro {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

//        JFrame frame = new JFrame("我叼你妈的");
//        MainPane mainPane = new MainPane();
//        frame.setContentPane(mainPane.panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
        NettyServerBootstrap bootstrap = new NettyServerBootstrap(9999);
    }
}
