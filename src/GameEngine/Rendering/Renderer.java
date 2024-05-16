package GameEngine.Rendering;

import static GameEngine.GameConstants.BLOCK_SIZE;
import static GameEngine.GameConstants.WINDOW_HEIGHT_GAME_COORDS;
import static GameEngine.GameConstants.WINDOW_WIDTH_GAME_COORDS;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;

import GameEngine.Ground;
import GameEngine.MapGenerator;
import GameEngine.Physics.PhysicsEngine;
import GameEngine.Rendering.Cameras.Camera;

public class Renderer {
    private final ArrayList<Renderable> toRender = new ArrayList<>();
    private int halfWindowWidth,halfWindowHeight;
    private final Camera camera;
    private final EntitySorter entitySorter = new EntitySorter();


    public Renderer(Camera cam) {
        camera = cam;
    }
    public void setHalfWindowWidth(int halfWidth) {
        halfWindowWidth = halfWidth;
    }
    public void setHalfWindowHeight(int halfHeight) {
        halfWindowHeight = halfHeight;
    }
    public void addRenderable(Renderable r) {
        toRender.add(r);
    }
    public void removeRenderable(Renderable r) {
        toRender.add(r);
    }

    public void update() {
        camera.update();
    }
    public int getNumRenderables() {return toRender.size();}

    public final float getWorldXFromScreenX(float x) {
        return (x - halfWindowWidth) / BLOCK_SIZE + camera.getCameraX();
    }
    public final int getScreenXFromWorldX(float worldX) {
        return (int) Math.floor((worldX - camera.getCameraX()) * BLOCK_SIZE) + halfWindowWidth;
    }
    public final float getWorldYFromScreenY(float y) {
        return (y - halfWindowHeight) / BLOCK_SIZE + camera.getCameraY();
    }
    public final int getScreenYFromWorldY(float worldY) {
        return (int) Math.floor((worldY - camera.getCameraY()) * BLOCK_SIZE) + halfWindowHeight;
    }
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Protocol
        // 1) Draw Ground
        // 2) Draw Sprites

        float top = camera.getCameraY() - WINDOW_HEIGHT_GAME_COORDS/2;
        float left = camera.getCameraX() - WINDOW_WIDTH_GAME_COORDS/2;
        for (int[] coord : PhysicsEngine.getCoordinatesCovered(left,top,left+WINDOW_WIDTH_GAME_COORDS,top + WINDOW_HEIGHT_GAME_COORDS)) {
            drawGround(g2, MapGenerator.getGroundAt(coord[0],coord[1]),coord[0],coord[1]);
        }

        toRender.sort(entitySorter);
        for (Renderable obj : toRender) {
            drawRenderable(g2,obj);
        }
    }
    private void drawGround(Graphics2D g, Ground ground,int x, int y) {
        int pixelCoordX = (int) Math.floor((x - camera.getCameraX()) * BLOCK_SIZE);
        int pixelCoordY = (int) Math.floor((y - camera.getCameraY()) * BLOCK_SIZE);
        g.drawImage(ground.getCurrentFrame(),pixelCoordX+ halfWindowWidth,pixelCoordY+halfWindowHeight,BLOCK_SIZE,BLOCK_SIZE,null);
    }

    private void drawRenderable(Graphics2D g,Renderable renderable) {
        Image image = renderable.getCurrentFrame();
        if (image == null) return;
        renderImage(g,image,renderable.getWorldX(),renderable.getWorldY(),
                            renderable.getSizeX(),renderable.getSizeY(),
                            renderable.getAnchorX(), renderable.getAnchorY());
    }
    private void renderImage(Graphics2D g, Image img, float worldX, float worldY, float worldSizeX, float worldSizeY, float anchorX, float anchorY) {
        int pixelCoordX = (int) Math.floor((worldX - camera.getCameraX()) * BLOCK_SIZE);
        int pixelCoordY = (int) Math.floor((worldY - camera.getCameraY()) * BLOCK_SIZE);
        int offsetX = (int) ((worldSizeX*BLOCK_SIZE-img.getWidth(null)) * anchorX);
        int offsetY = (int) ((worldSizeY*BLOCK_SIZE-img.getHeight(null)) * anchorY);
        g.drawImage(img,pixelCoordX+offsetX + halfWindowWidth,pixelCoordY+offsetY + halfWindowHeight,img.getWidth(null), img.getHeight(null),null);
        //g.drawImage(img,pixelCoordX+offsetX + halfWindowWidth,pixelCoordY+offsetY + halfWindowHeight,(int)(worldSizeX*BLOCK_SIZE), (int)(worldSizeY*BLOCK_SIZE),null);
    }
    public static class EntitySorter implements Comparator<Renderable> {
        @Override
        public int compare(Renderable r1, Renderable r2) {
            return (int)(2*(r1.getWorldY() - r2.getWorldY()));
        }
    }

}
