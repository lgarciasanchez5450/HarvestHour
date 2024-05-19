package GameEngine.Rendering;

import static GameEngine.GameConstants.BLOCK_SIZE;

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
    private final ArrayList<ArrayList<Renderable>> layers = new ArrayList<>(4);
    private int halfWindowWidth,halfWindowHeight;
    private final Camera camera;
    private final int defaultLayer;
    private final EntitySorter entitySorter = new EntitySorter();

    public Renderer(Camera cam, int numberOfLayers) {
        defaultLayer = numberOfLayers/2;
        camera = cam;
        for (int i = 0; i < numberOfLayers; i++)
            layers.add(new ArrayList<>());
    }
    public void setHalfWindowWidth(int halfWidth) {
        halfWindowWidth = halfWidth;
    }
    public void setHalfWindowHeight(int halfHeight) {
        halfWindowHeight = halfHeight;
    }
    public void addRenderable(Renderable r) {
        addRenderable(r,defaultLayer);
    }
    public boolean removeRenderable(Renderable r) {
        if (!removeRenderable(r,defaultLayer)) {
            for (int i = 0; i < layers.size();i++) {
                if (i==defaultLayer) continue;
                if (removeRenderable(r,i)) return true;
            }
        } else {
            return true;
        }
        return false;
    }
    public void addRenderable(Renderable r,int layer) {
        layers.get(layer).add(r);
    }
    public boolean removeRenderable(Renderable r, int layer) {
        return layers.get(layer).remove(r);
    }
    public Camera getCamera() { return camera; }
    public void update() {
        camera.update();
    }
    public int getNumRenderables() {
        int i = 0;
        for(ArrayList<Renderable> layer :layers)
            i += layer.size();
        return i;
    }

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
        final float WINDOW_HEIGHT_GAME_COORDS = (float) halfWindowHeight*2 / BLOCK_SIZE;
        final float WINDOW_WIDTH_GAME_COORDS = (float) halfWindowWidth*2 / BLOCK_SIZE;
        // Protocol
        // 1) Draw Ground
        // 2) Draw Sprites

        float top = camera.getCameraY() - WINDOW_HEIGHT_GAME_COORDS/2;
        float left = camera.getCameraX() - WINDOW_WIDTH_GAME_COORDS/2;
        for (int[] coord : PhysicsEngine.getCoordinatesCovered(left,top,left+WINDOW_WIDTH_GAME_COORDS,top + WINDOW_HEIGHT_GAME_COORDS)) {
            drawGround(g2, MapGenerator.getGroundAt(coord[0],coord[1]),coord[0],coord[1]);
        }
        for (ArrayList<Renderable> layer : layers ) {
            layer.sort(entitySorter);
            try {
                for (Renderable obj : layer) {
                    drawRenderable(g2,obj);
                }
            }
            catch (Exception e) {
                System.out.println(e);

            }
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
            return (int) Math.copySign(1,(r1.getWorldY() - r2.getWorldY()));
        }
    }

}
