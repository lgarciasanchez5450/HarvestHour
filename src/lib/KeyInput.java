package lib;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class KeyInput implements KeyListener {

    public static final List<Character> keysTyped = new LinkedList<>();
    public static final List<Integer> keysPressed = new LinkedList<>();
    public static final List<Integer> keysReleased = new LinkedList<>();

    public static final HashMap<Integer,Boolean> keysDown = new HashMap<>();
    public static boolean getKey(int keyCode) {
        if (!keysDown.containsKey(keyCode)) return false;
        return keysDown.get(keyCode);
    }
    public static int getKeyAsInt(int keyCode) {
        if (!keysDown.containsKey(keyCode)) return 0;
        return keysDown.get(keyCode)? 1 : 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keysTyped.add(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        keysDown.put(e.getKeyCode(),true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysReleased.add(e.getKeyCode());
        keysDown.put(e.getKeyCode(),false);

    }
    public static void reset() {
        keysPressed.clear();
        keysReleased.clear();
    }
    public static boolean consumeKeyTyped(char key) {
        int i = 0;
        while (i < keysTyped.size() && keysTyped.get(i)!= key) {
            i++;
        }
        if (i == keysTyped.size()) return false;
        keysTyped.remove(i);
        return true;
    }
}
