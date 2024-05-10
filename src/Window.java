import javax.swing.JFrame;

import lib.KeyInput;
import lib.MouseInput;

public class Window extends JFrame {
    public Window(String title) {
        super(title);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyInput());
        addMouseListener(new MouseInput());
    }
}
