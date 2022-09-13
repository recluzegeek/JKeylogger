import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matthias Hinz && Muhammad Salman
 */
class ClipBoardMonitor implements Runnable {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    public void run() {
        System.out.println("Listening to clipboard...");
        // the first output will be when a non-empty text is detected
        String recentContent = "";
        // continuously perform read from clipboard
        while (running) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // request what kind of data-flavor is supported
                List<DataFlavor> flavors = Arrays.asList(sysClip.getAvailableDataFlavors());
                // this implementation only supports string-flavor
                if (flavors.contains(DataFlavor.stringFlavor)) {
                    String data = (String) sysClip.getData(DataFlavor.stringFlavor);
                    if (!data.equals(recentContent)) {
                        recentContent = data;
                        System.out.println("New clipboard text detected: " + data);
                    }
                }

            } catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ClipBoardMonitor b = new ClipBoardMonitor();
        Thread thread = new Thread(b);
        thread.start();
    }
}
