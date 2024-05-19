package lib;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class MouseInput implements MouseListener, MouseWheelListener {

    public Point mousePosition =  new Point();
    public Point mousePositionDelta = new Point();
    public int wheel;
    public final boolean[] mouseButtonsDown = new boolean[3];
    public final boolean[] mouseButtonsClicked = new boolean[3];
    private final boolean[] mb = new boolean[3];

    public void update() {
        wheel = 0;
        for (int i = 0; i < 3;i++) {
            mouseButtonsClicked[i] = mb[i] && !mouseButtonsDown[i];
            mouseButtonsDown[i] = mb[i];
        }
    }
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        mb[e.getButton()-1] = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        mb[e.getButton()-1] = false;
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        wheel = e.getScrollAmount();
    }
    public void reset() {
        mb[0] = false;
        mb[1] = false;
        mb[2] = false;

    }
}
