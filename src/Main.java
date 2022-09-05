import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
/*
 * VC_A = means virtual code for A in the library
 * raw-code = asci of the chars
 * */
import java.util.LinkedList;
import java.util.Queue;

public class Main implements NativeKeyListener {
    static Queue<Character> queue;
    static LinkedList<String> linkedList;
    static String word;

    Main() {
        queue = new LinkedList<>();
        linkedList = new LinkedList<>();
        word = "";
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
     *   keyReleased - when the key comes up
     *   keyTyped - when the unicode character represented by this key is sent by the keyboard to system input.
     *
     * */

    // key code is the virtual code assigned to each special key to generate their words counterpart

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        int keyCode = nativeKeyEvent.getKeyCode();
//        System.out.println("Keycode is : " + keyCode);
        if (!(keyCode >= 2 && keyCode <= 11 || keyCode >= 16 && keyCode <= 25
                || keyCode >= 25 && keyCode <= 38 || keyCode >= 44 && keyCode <= 50))
            System.out.println("Key Pressed : " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));

        // getKeyText is static member of class NativeKeyEvent, so should be called via class name, not via its object
        // if the pressed key is the space key or enter key, then empty the queue and join the removed elements to word
        // add that word to our linked list and make word empty
        if (keyCode == NativeKeyEvent.VC_ENTER || keyCode == NativeKeyEvent.VC_SPACE) {
            while (!queue.isEmpty()) {
                word += queue.remove();
            }
            linkedList.add(word);
            System.out.println("Resultant Linked List : " + linkedList);
            word = "";
        }

    }


    // raw code is the asci of the character being typed
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        if (e.getRawCode() >= 32 && e.getRawCode() <= 127) {
            // add all typed characters to the queue
            queue.add(e.getKeyChar());
            System.out.println("KeyTyped : \"" + e.getKeyChar() + "\" ");

        }
    }


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
}