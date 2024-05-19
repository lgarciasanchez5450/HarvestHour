package GameEngine.Entities;

import static GameEngine.GameConstants.harvestTag;
import static GameEngine.GameConstants.weetTag;

import Applications.GameApp;
import Assets.images.ImageLoader;
import GameEngine.Animation;
import GameEngine.Physics.Physical;
import lib.Quadtree.Rect;

public class Harvester extends Entity {
    private float radius;
    protected Harvester(float x, float y, float radius) {
        super(harvestTag,x, y, 0, 0);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/player/harvesting"),25);
    }
    public void update() {
        shouldDespawn = currentAnimation.isFinishedCycle();
    }
    @Override
    public void onDespawn(GameApp game) {
        Rect collider = new Rect(getWorldX()-radius-1,getWorldY()-radius-1,2*radius+2,2*radius+2);
        for(Physical obj : game.getPhysics().getEntitiesCollided(collider)) {
            if (obj.getTag().equals(weetTag)) {
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
