package lib;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;

import GameEngine.Animation;
public class VisualCounter extends JLabel {
    private final Animation anim;
    private final int x;
    private final int y;
    private int count;
    public VisualCounter(Animation anim,int x, int y) {
        this.anim = anim;
        this.x = x;
        this.y = y;
        setBounds(x+anim.getCurrentFrame().getWidth(null),0,200,20);
    }
    public void setCount(int c) {
        this.count = c;
    }
    public int getCount() {
        return count;
    }
    public void paint(Graphics2D g) {
        Image frame = anim.getCurrentFrame();
        g.drawImage(frame,x,y,null);
        setBounds(x+frame.getWidth(null)+5,y+frame.getHeight(null)/4,200,20);
        setText(String.valueOf(count));
    }
}
