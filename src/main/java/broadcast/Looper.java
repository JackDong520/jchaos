package broadcast;

import java.util.Queue;

public abstract class Looper {
    private Queue<Msg> msgQueue;

    public void writeMsg(Msg msg) {
        msgQueue.add(msg);
    }


    public void runLooper() {
        while (true) {
            Msg msg = msgQueue.poll();
            if (msg != null) {
                return;
            }
        }
    }
    public abstract void handleMessage(Msg msg);
}
