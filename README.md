# JKeylogger
This Java-based keylogger utilizes `jnative hook` to capture keystrokes, and applies concepts learned in a Data Structures course to process the data. As Java files themselves runs inside the JVM they're unable to log keystrokes, `jnative hook` library provides a system-wide hook for capturing keystrokes. I formatted the captured keystrokes using Data Structures, such as combining keystrokes into words or phrases. For example, when the spacebar or special key is pressed, dequeue all the keystrokes to form a complete word. This ensures that the output is more readable and user-friendly.

![Demo](https://github.com/Salman1057/JKeylogger/assets/72850566/6cc99690-1e6e-4074-b1f7-e7daec946c1a)


## Implementation

The `MainDriver` class contains the `main` method which starts the program by initializing an instance of the `KeyLogger` class, starting a `Timer` to periodically write the logged data to a file, and starting a new thread to monitor the clipboard. 

The `while` loop inside the `main` method keeps running until the program is terminated, constantly checking if the focus of the current window has changed. If the focus has changed, the `BalancedBST` instance logs the key sequence pressed by the user since the previous focus change.

The `KeyLogger` class implements the `NativeKeyListener` interface and contains methods to listen for key events. The `nativeKeyPressed` method is called whenever a key is pressed. It checks if the key is a special key, and if not, logs the pressed key and enqueues it to a `Queue`. If a special key is pressed (i.e., enter, space, period, or backspace), the contents of the `Queue` are dequeued and appended to a `StringBuilder`, which is then added to a `LinkedList` containing all the words typed since the previous special key press. The `nativeKeyTyped` method is called whenever a key is typed and simply enqueues the typed character to the `Queue`.

## Known Bugs
- Text / Keystrokes that are logged in a program are shown onto the next program record.
  - If we wrote 'Hello from Notepad' in Notepad, and we change the focus to Chrome the text we wrote will be shown under Chrome record
- Clipboard Monitoring doesn't work for Images.
  - If an image is on the Clipboard before the execution of the program, it will crash.
## Setup
- Create new Project in Intellij and clone this repository.
- Import `jnativehook.jar` as library by going into the project structure.
- Run and test the program.

### For Testing Purposes, you can use the `.jar` included in the repository.
  - `.jar` path from Repository Root
    - out/artifacts/JKeyLogger_jar/KeyLogger.jar
