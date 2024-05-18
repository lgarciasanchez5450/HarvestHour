package GameEngine.Rendering;

import static GameEngine.GameConstants.BLOCK_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashSet;

import Assets.images.ImageLoader;
import GameEngine.Block;
import GameEngine.Ground;
import GameEngine.MapGenerator;
import lib.KeyInput;
import lib.MouseInput;

public class MapMakerGroundHUD {
    private static final int MAX_PER_ROW = 5;
    private static final int SPACING = 5;
    private int halfWindowHeight,halfWindowWidth;
    private boolean showing;
    private int activeIndex;
    private final Ground.Types[] grounds;
    MouseInput mouseInput;
    Point size;
    int left;
    int top;
    Image saveButton;
    HashSet<Integer> saveIndexes = new HashSet<>();
    public MapMakerGroundHUD() {
        grounds = Ground.Types.values();
        size = getSize();
        left= halfWindowWidth - size.x/2;
        top =halfWindowHeight - size.y/2;
        saveButton = ImageLoader.load("gui/saveButton.png");
        int[] a = {-1, -2, -3, -6, -7, -8, -11, -12, -13};
        for (int x : a) {
            saveIndexes.add(x);
        }
    }
    public void setMouseInput(MouseInput mi) {
        mouseInput = mi;
    }
    private Point getSize() {
        int sizeY = grounds.length/MAX_PER_ROW + 1;
        int sizeX = sizeY==1 ? grounds.length : MAX_PER_ROW;
        sizeY *= BLOCK_SIZE + SPACING;
        sizeX *= BLOCK_SIZE + SPACING;
        sizeX -= SPACING;
        sizeY -= SPACING;
        return new Point(sizeX,sizeY);
    }
    public void update() {
        if (!showing) return;
        if (mouseInput != null && mouseInput.mouseButtonsClicked[0]) {
            int x = (mouseInput.mousePosition.x - left) / (BLOCK_SIZE + SPACING);
            int y = (mouseInput.mousePosition.y - top) / (BLOCK_SIZE + SPACING);
            int index = y * MAX_PER_ROW + x;
            if (x < MAX_PER_ROW && (index >= 0 && index < grounds.length)) {
                activeIndex = index;
            } else {
                if (saveIndexes.contains(index)) {
                    MapGenerator.saveMap("main");
                }
            }
        }

    }
    public void paint(Graphics g) {
        if (!showing) return;
        int left = halfWindowWidth - size.x/2;
        int top = halfWindowHeight - size.y/2;
        for (int i = 0; i < grounds.length;i++) {
            Ground ground = grounds[i].getGround();
            int offsetX = i%MAX_PER_ROW * (BLOCK_SIZE + SPACING);
            int offsetY = i/MAX_PER_ROW * (BLOCK_SIZE + SPACING);

            if (i==activeIndex) {
                Color p = g.getColor();
                g.setColor(Color.GREEN);
                g.fillRect(left+offsetX-SPACING,top+offsetY-SPACING,BLOCK_SIZE+2*SPACING,BLOCK_SIZE+2*SPACING);
                g.setColor(p);
            }
            g.drawImage(ground.getCurrentFrame(),left+offsetX,top+offsetY,BLOCK_SIZE,BLOCK_SIZE,null);

        }

        g.drawImage(saveButton,2*(halfWindowWidth-BLOCK_SIZE),0,2* BLOCK_SIZE,2*BLOCK_SIZE,null);


    }
    public void setShowing(boolean s) { showing = s; }
    public boolean getShowing() { return showing; }
    public void setHalfWindowWidth(int halfWidth) {
        halfWindowWidth = halfWidth;
        left= halfWindowWidth - size.x/2;
        top =halfWindowHeight - size.y/2;
    }
    public void setHalfWindowHeight(int halfHeight) {
        halfWindowHeight = halfHeight;
        left= halfWindowWidth - size.x/2;
        top =halfWindowHeight - size.y/2;
    }
    public Ground getGroundSelected() {
        return grounds[activeIndex].getGround();
    }
}
