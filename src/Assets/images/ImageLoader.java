package Assets.images;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ImageLoader {

    public static Image load(String path) {
        Image img = new ImageIcon(Objects.requireNonNull(ImageLoader.class.getResource(path))).getImage();
        return img;
    }
    public static Image[] loadFolder(String path) {
        ArrayList<String> pngPaths = new ArrayList<>();
        File folder = new File("./"+path);
        if (!folder.isDirectory())  throw new RuntimeException("Path provided is not a directory! Info:"+folder.toString());
        for (File file : folder.listFiles()) {
            if (file.isFile() && (file.getName().endsWith(".png")||file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg"))) {
                pngPaths.add(file.getPath().substring(".\\src\\Assets\\images\\".length()));
            }
        }
        System.out.println(pngPaths);
        Image[] images = new Image[pngPaths.size()];
        for (int i = 0; i < images.length;i++) {
            images[i] = load(pngPaths.get(i));
        }
        return images;
    }
}