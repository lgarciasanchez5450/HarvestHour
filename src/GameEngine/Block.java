package GameEngine;


import java.awt.Image;

import Assets.images.ImageLoader;
import GameEngine.Entities.Entity;
import GameEngine.Physics.Immovable;

public class Block extends Entity implements Immovable {


    protected Block(String tag,float x, float y, float width, float height, String path) {
        super(tag, x, y, width, height);

        if (path != null)
            currentAnimation = new Animation(new Image[] {ImageLoader.load(path)},1);
    }
    public enum Types {
        INVISIBLE(1,1,"Invisible",null),
        WOOD(1,1,"plankhehehheaw","blocks/plank/0.png");
        static {
            WOOD.setAnchors(0,1);
        }
        private final float width,height;
        private final String tag;
        private final String path;
        private float anchorX,anchorY;
        Types(float w, float h, String tag,String p) {
            width = w;
            height = h;
            path = p;
            this.tag = tag;
        }
        public Types setAnchors(float x, float y) {
            this.anchorX = x;
            this.anchorY = y;
            return this;
        }

        public Block makeBlock(float x, float y) {
            Block b =new Block(tag, x,y,width,height,path);
            b.setAnchorX(anchorX);
            b.setAnchorY(anchorY);
            return b;
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
