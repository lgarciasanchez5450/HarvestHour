package lib;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class MouseInput implements MouseListener {

    public Point mousePosition;
    public boolean[] mouseButtons = new boolean[3];


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()-1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()-1] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
