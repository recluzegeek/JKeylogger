import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {
    void writeToFile() {
        try (FileWriter fw = new FileWriter(new File(".\\src\\win.txt"), false)) {
            fw.write(linkedList.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
