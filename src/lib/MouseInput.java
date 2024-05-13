package lib;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseInput implements MouseListener {

    public Point mousePosition;
    public final boolean[] mouseButtonsDown = new boolean[3];
    public final boolean[] mouseButtonsClicked = new boolean[3];
    private final boolean[] mb = new boolean[3];

    public void update() {
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
}
