import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;


public class EnumerateWindows {
    public static void main(String[] args) throws Exception {
        HWND prevFg = null;
        HWND fg;

        while (true) {
            Thread.sleep(200);

            fg = User32.INSTANCE.GetForegroundWindow();
            if (fg == null) {
                System.out.println("fg is null");
                continue;
            }
            // don't print the name if it's still the same window as previously
            if (fg.equals(prevFg)) {
                continue;
            }

            String fgImageName = getImageName(fg);
            if (fgImageName == null) {
                System.out.println("Failed to get the image name!");
            } else {
                System.out.println("\t*********************************************************************************************************");
                char[] buffer = new char[1024 * 2];
                User32.INSTANCE.GetWindowText(fg, buffer, 1024);
                System.out.println("Foreground Windows Title: " + Native.toString(buffer));
                System.out.println("Focused Application Path: " + fgImageName);
            }

            prevFg = fg;
        }
    }

    private static String getImageName(HWND window) {
        // Get the process ID of the window
        IntByReference procId = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(window, procId);

        // Open the process to get permissions to the image name
        HANDLE procHandle = Kernel32.INSTANCE.OpenProcess(
                Kernel32.PROCESS_QUERY_LIMITED_INFORMATION,
                false,
                procId.getValue()
        );

        // Get the image name
        char[] buffer = new char[4096];
        IntByReference bufferSize = new IntByReference(buffer.length);
        boolean success = Kernel32.INSTANCE.QueryFullProcessImageName(procHandle, 0, buffer, bufferSize);

        // Clean up: close the opened process
        Kernel32.INSTANCE.CloseHandle(procHandle);

        return success ? new String(buffer, 0, bufferSize.getValue()) : null;
    }
}