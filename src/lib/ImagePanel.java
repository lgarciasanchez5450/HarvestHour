package lib;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;

import Assets.images.ImageLoader;

public class ImagePanel extends Panel {
    Image img;
    public ImagePanel(String path) {
        img = ImageLoader.load(path);
    }
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g) {

        //super.paint(g);
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
        // g.dispose();
    }

}
