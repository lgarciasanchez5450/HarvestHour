

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;



import javax.swing.JFrame;

import lib.Clock;
import lib.KeyInput;
import lib.MouseInput;

public abstract class Application extends JPanel implements Runnable {
    public static final int FPS = 60;
    protected JFrame screen;
    private boolean running;

    protected KeyInput keyInput;
    protected MouseInput mouseInput;

    protected Font defaultFont;

    public final Dimension displayResolution;

    Application(String caption, int width, int height) {
        displayResolution = Toolkit.getDefaultToolkit().getScreenSize();
        defaultFont = new Font("Arial",Font.PLAIN,20);

        if (width == 0) width = displayResolution.width;
        if (height == 0) height = displayResolution.height;

        screen = new JFrame(caption);
        screen.setSize(width,height);
        screen.setLocation((displayResolution.width-width)/2,(displayResolution.height-height)/2); // Puts the Window in the middle of the page.
        screen.setPreferredSize(new Dimension(width,height));
        screen.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });
        screen.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                onResize();
            }
        });
        keyInput = new KeyInput();
        screen.addKeyListener(keyInput);

        mouseInput = new MouseInput();
        screen.addMouseListener(mouseInput);

    }
    public boolean isRunning() {
        return running;
    }
    public void quit() {
        running = false;
    }
    public void run() {
        running = true;
        Clock clock = new Clock();

        init();
        screen.setVisible(true);

        while (running) {
            loop();
            clock.tick(FPS);
        }
        onQuit();
        screen.dispose();

    }
    protected abstract void init();

    protected abstract void loop();

    protected abstract void onQuit();

    protected void onResize() {} // Optional to override

}