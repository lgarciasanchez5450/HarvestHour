package GameEngine.Entities;

import static GameEngine.GameConstants.BLOCK_SIZE;
import static GameEngine.GameConstants.harvestTag;
import static GameEngine.GameConstants.weetTag;

import java.awt.Image;

import Applications.GameApp;
import Assets.images.ImageLoader;
import GameEngine.Animation;
import GameEngine.Physics.Physical;
import lib.Quadtree.Rect;

public class Harvester extends Entity {
    private static Image[] frames;
    private final float radius;
    protected Harvester(float x, float y, float radius) {

        super(harvestTag,x, y, 0, 0);
        this.radius = radius;
        if (frames == null)
            frames = ImageLoader.loadFolder("src/Assets/images/player/harvesting",BLOCK_SIZE,BLOCK_SIZE);
        currentAnimation = new Animation(frames,25);
        setAnchorY(0.1f);
        setAnchorX(0.1f);
    }
    public void update() {
        shouldDespawn = currentAnimation.isFinishedCycle();
    }
    @Override
    public void onDespawn(GameApp game) {
        Rect collider = new Rect(getWorldX()-radius-1,getWorldY()-radius-1,2*radius+2,2*radius+2);
        for(Physical obj : game.getPhysics().getEntitiesCollided(collider)) {
            if (obj.getTag().equals(weetTag)) {
                if (Math.hypot(obj.getWorldX() - getWorldX(), obj.getWorldY() - getWorldY()) <= radius)
                    game.killEntity((Entity)obj);
            }
        }

    }

    @Override
    public float getVelX() {return 0;}
    @Override
    public float getVelY() {return 0;}
    @Override
    public void setVelX(float x) {}
    @Override
    public void setVelY(float y) {}
}
