package lib;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Label extends java.awt.Label {

    public float xAlignment = CENTER_ALIGNMENT;
    public float yAlignment = CENTER_ALIGNMENT;
    public float parentXAnchor = CENTER_ALIGNMENT;
    public float parentYAnchor = CENTER_ALIGNMENT;

    private float posX = 0, posY = 0;
    private Font font;
    public Label(String text, Font font) {
        this(text,0,0);
        setFont(font);
        this.font = font;
        FontMetrics metrics = getFontMetrics(font);
        setSize(metrics.stringWidth(text)+4,metrics.getHeight());

    }
    public Label(String text,int width, int height) {
        super(text);
        setSize(width,height);
    }


    public void recalculatePosition() {
        if (getParent() == null) return;
        Dimension parentSize = getParent().getSize();
        int trueX = (int) (parentSize.width * parentXAnchor - getWidth() * xAlignment + posX);
        int trueY = (int) (parentSize.height * parentYAnchor - getHeight() * yAlignment + posY);
        setBounds(trueX,trueY,getWidth(),getHeight());
        if (font != null) {
            setFont(font);
        }
    }
    public void setSize(int width, int height) {
        super.setSize(width,height);
        recalculatePosition();
    }
    public void setOffset(int x, int y) {
        posX = x;
        posY = y;
        recalculatePosition();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

}
