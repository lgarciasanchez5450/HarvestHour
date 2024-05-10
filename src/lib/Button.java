package lib;

import java.awt.Dimension;


public class Button extends java.awt.Button {

    public float xAlignment = CENTER_ALIGNMENT;
    public float yAlignment = CENTER_ALIGNMENT;
    public float parentXAnchor = CENTER_ALIGNMENT;
    public float parentYAnchor = CENTER_ALIGNMENT;

    private float posX = 0, posY = 0;

    public Button() {this(() -> {});}
    public Button(Runnable func) {this("",50,50,func);}
    public Button(String text, int width, int height, Runnable func) {
        super(text);
        setSize(width,height);
        addActionListener(e -> func.run());
    }


    public void recalculatePosition() {
        if (getParent() == null) return;
        Dimension parentSize = getParent().getSize();
        int trueX = (int) (parentSize.width * parentXAnchor - getWidth() * xAlignment + posX);
        int trueY = (int) (parentSize.height * parentYAnchor - getHeight() * yAlignment + posY);
        setBounds(trueX,trueY,getWidth(),getHeight());
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

}
