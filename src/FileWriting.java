import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {
    void writeToFile() {
        try (FileWriter fw = new FileWriter(new File(".\\src\\win.txt"), false)) {
//            fw.write(KeyLogger.linkedList.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void formatting(String str) {
        System.out.println("--------+-----------------+--------------+--------------------------------+----------------------------------------------------+----------------------+");
        System.out.printf("%7s |    %-10s   | %-12s | %-30s | %-50s | %-20s |\n", "Index", "Date/Time", "Source", "Title/Description", "Values", "Clipboard Items");
        System.out.println("--------+-----------------+--------------+--------------------------------+----------------------------------------------------+----------------------+");


    }

    public static void main(String[] args) {
        formatting("Index");
    }
}
