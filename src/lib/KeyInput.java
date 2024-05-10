package lib;

import com.sun.jdi.IntegerValue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KeyInput implements KeyListener {

    public static final List<KeyEvent> keysTyped = new LinkedList<>();
    public static final List<KeyEvent> keysPressed = new LinkedList<>();
    public static final List<KeyEvent> keysReleased = new LinkedList<>();

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
        keysTyped.add(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e);
        keysDown.put(e.getKeyCode(),true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysReleased.add(e);
        keysDown.put(e.getKeyCode(),false);
    }
    public void reset() {
        keysPressed.clear();
        keysReleased.clear();
        keysTyped.clear();
    }
}
