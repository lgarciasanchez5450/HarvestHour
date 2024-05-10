package GameEngine;

import java.awt.Image;

import GameEngine.Physics.Immovable;
import GameEngine.Physics.Movable;
import GameEngine.Rendering.Renderable;

public abstract class Entity implements Renderable, Movable {
    // Basic State
    private float worldX, worldY;
    private final float sizeX,sizeY;
    private float anchorX, anchorY;

    private float mass;

    protected Animation currentAnimation;

    protected Entity(float x, float y, float width, float height) {
        worldX = x;
        worldY = y;
        sizeX = width;
        sizeY = height;
    }
    public float getMass() {return mass;}

    public final float getWorldX() { return worldX; }
    public final float getWorldY() {
        return worldY;
    }
    public final void setWorldX(float x) { worldX = x; }
    public final void setWorldY(float y) { worldY = y; }

    public final float getSizeX() {
        return sizeX;
    }
    public final float getSizeY() {
        return sizeY;
    }

    public final float getAnchorX() { return anchorX; }
    public final float getAnchorY() {return anchorY; }

    public final float setAnchorX(float anchorX) { return this.anchorX = anchorX;}
    public final float setAnchorY(float anchorY) {return this.anchorY = anchorY; }

    public Image getCurrentFrame() {
        return currentAnimation.getCurrentFrame();
    }

    public void update() {}


}
