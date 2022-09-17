import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class EnumerateWindows {
    private static HashMap<Integer, String> hashMap = new HashMap<>();
    private static int numOfApplications = 0;

    public static HashMap<Integer, String> getHashMap() {
        return hashMap;
    }


    public void run() throws Exception {
        String appDetail;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a z");

        HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
        if (foregroundWindow == null) {
            return;
        }
        // don't print the name if it's still the same window as previously
        if (foregroundWindow.equals(MainDriver.prevFg)) {
//            MainDriver.focusedLoosed = false;
            return;
        }

        String fgImageName = getImageName(foregroundWindow);
        if (fgImageName == null) {
            System.out.println("Failed to get the image name!");
        } else {
//            System.out.println("\t*********************************************************************************************************");
            char[] buffer = new char[1024 * 2];
            User32.INSTANCE.GetWindowText(foregroundWindow, buffer, 1024);
            /*
            * Date/Time : 15/09/2022 07:57:09 AM CEST
              Foreground Windows Title : Untitled - Notepad
              Application Path C:\Windows\System32\notepad.exe
              * "\nDate/Time : " + ZonedDateTime.now().format(dtf) + "\nForeground Windows Title : " + Native.toString(buffer) + "\nApplication Path " + fgImageName + "\n";
            * */
            appDetail = ZonedDateTime.now().format(dtf) + ", " + Native.toString(buffer) + ", " + fgImageName+", "+ClipBoardMonitor.getRecentContent();
            hashMap.put(++numOfApplications, appDetail);
        }

        MainDriver.prevFg = foregroundWindow;
//        System.out.println(getHashMap());
    }

    private String getImageName(HWND window) {
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