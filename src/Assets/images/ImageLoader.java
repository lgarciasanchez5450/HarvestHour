package Assets.images;

import java.awt.Image;

import javax.swing.ImageIcon;

import java.io.File;

import java.util.ArrayList;
import java.util.Objects;


public class ImageLoader {

    public static Image load(String path) {
        return new ImageIcon(Objects.requireNonNull(ImageLoader.class.getResource(path))).getImage();
    }
    public static Image[] loadFolder(String path) {
        return loadFolder(path,-1,-1);
    }
    public static Image[] loadFolder(String path,int width,int height) {
        ArrayList<String> pngPaths = new ArrayList<>();
        File folder = new File("./"+path);
        if (!folder.isDirectory())  throw new RuntimeException("Path provided is not a directory! Info:"+ folder);
        for (File file : folder.listFiles()) {
            if (file.isFile() && (file.getName().endsWith(".png")||file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg"))) {
                pngPaths.add(file.getPath().substring(".\\src\\Assets\\images\\".length()));
            }
        }
        Image[] images = new Image[pngPaths.size()];
        for (int i = 0; i < images.length;i++) {

            images[i] = load(pngPaths.get(i));
            if (width == -1 && height == -1) continue;
            int w = width == -1 ? images[i].getWidth(null) : width;
            int h = height == -1 ? images[i].getHeight(null) : height;
            images[i] = images[i].getScaledInstance(w,h,0);
        }
        return images;
    }
}