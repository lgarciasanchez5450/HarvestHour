import java.awt.Component;

import javax.swing.JFrame;

import lib.KeyInput;
import lib.MouseInput;

public class Window extends JFrame {
    private final KeyInput keyInput;
    private final MouseInput mouseInput;
    public Window(String title) {
        super(title);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        addKeyListener(keyInput);
        addMouseListener(mouseInput);
    }

    public Component add(Component comp) {
        if (comp instanceof GameApp) {
            ((GameApp)comp).setMouseInput(mouseInput);
        }
        return super.add(comp);
    }
}