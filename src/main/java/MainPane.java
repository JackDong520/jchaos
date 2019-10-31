import Config.Config;
import broadcast.MyHandler;
import com.sun.webkit.network.data.Handler;
import javabean.Terminal;
import javafx.scene.control.ScrollBar;
import service.ControlService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MainPane {

    private static ControlService controlService;
    public JPanel panel1;
    public JButton sendDataButton;
    public JTextField textField1;
    private JScrollPane TerminalScroll;
    private JPanel terminalPanel;


    public MainPane() throws InterruptedException, InvocationTargetException {
        init();
        sendDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("wdnmd");
                controlService.sendMesg("123", "wdnmd");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket serverSocket = new DatagramSocket(Config.BroadcastPort);
                    byte[] arr = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(arr, arr.length);
                    //3 当程序运行起来之后,receive方法会一直处于监听状态
                    //4
                    while (true) {
                        serverSocket.receive(packet);
                        //从包中将数据取出
                        byte[] arr1 = packet.getData();
                        System.out.println(new String(arr1));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init() throws InterruptedException {
        controlService = new ControlService();
        addTerminalToscroll();
    }

    private void addTerminalToscroll() {
//        TerminalScroll.createVerticalScrollBar();
//        TerminalScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            @Override
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//
//            }
//        });

        String[] items = new String[]{"身份证", "驾驶证", "军官证"};
        JList list = new JList(items);
        terminalPanel.add(list);


    }

    private void setLisenter() {

    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setData(Terminal data) {
        textField1.setText(data.getWdnmd());
    }

    public void getData(Terminal data) {
        data.setWdnmd(textField1.getText());
    }

    public boolean isModified(Terminal data) {
        if (textField1.getText() != null ? !textField1.getText().equals(data.getWdnmd()) : data.getWdnmd() != null)
            return true;
        return false;
    }
}
