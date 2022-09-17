import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author Matthias Hinz && Muhammad Salman
 */
class ClipBoardMonitor implements Runnable {
    private static String recentContent = "";

    public static String getRecentContent() {
        return recentContent;
    }

    private static Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void run() {
        try {
            if (sysClip.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                try {
                    String content = (String) sysClip.getData(DataFlavor.stringFlavor);
                    if (!content.equals(recentContent)) {
                        recentContent = content;
//                        System.out.println("New Clipboard text detected : " + content);
                    }
                } catch (HeadlessException | UnsupportedFlavorException | IOException e) {
                    return;
                }
            }
        } catch (IllegalStateException e) {
            return;
        }
    }
}
