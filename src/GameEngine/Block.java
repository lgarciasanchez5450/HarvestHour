package GameEngine;

import static GameEngine.GameConstants.BLOCK_SIZE;

import java.awt.Image;

import Assets.images.ImageLoader;
import GameEngine.Entities.Entity;
import GameEngine.Physics.Immovable;

public class Block extends Entity implements Immovable {
    private final float offsetX;
    private final float offsetY;

    protected Block(String tag,float x, float y, float width, float height, String path) {
        super(tag, x, y, width, height);
        offsetX = x%1.0f;
        offsetY = y%1.0f;
        if (path != null)
            currentAnimation = new Animation(new Image[] {ImageLoader.load(path).getScaledInstance(BLOCK_SIZE,BLOCK_SIZE,0)},1);
    }
    public enum Types {
        TEST(1,1,"","blocks/test.png"),
        INVISIBLE(1,1,"Invisible",null);

        private final float width,height;
        private final String tag;
        private final String path;
        Types(float w, float h, String tag,String p) {
            width = w;
            height = h;
            path = p;
            this.tag = tag;
        }
        public Block makeBlock(float x, float y) {
            return new Block(tag, x,y,width,height,path);
        }
    }

    @Override
    public final float getVelX() {return 0;}
    @Override
    public final float getVelY() {return 0;}
    @Override
    public final void setVelX(float x) {}
    @Override
    public final void setVelY(float y) {}
    @Override
    public final float getPosOffsetX() { return 0; }
    @Override
    public final float getPosOffsetY() { return 0; }
    public String toString() {
        return "Block(<"+getWorldX()+","+getWorldY()+">, <"+getSizeX()+", " + getSizeY()+">)";
    }
}
