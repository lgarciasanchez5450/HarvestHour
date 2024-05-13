package GameEngine.Entities;

import static GameEngine.GameConstants.BLOCK_SIZE;

import java.util.ArrayList;

import Assets.images.ImageLoader;
import GameEngine.Animation;
import GameEngine.Ground;
import GameEngine.Physics.Physical;
import GameEngine.Physics.PhysicsEngine;

public class Weet extends Entity{
    public Weet(int x, int y) {
        super(x, y, 1, 1);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/blocks/wheet",BLOCK_SIZE,BLOCK_SIZE+BLOCK_SIZE/2),1);
        setAnchorX(0.5f);
        setAnchorY(1);
    }
    public boolean canSpawnOn(PhysicsEngine physicsEngine) {
        Ground ground = (Ground) physicsEngine.getGroundAt((int)getWorldX(),(int)getWorldY());
        ArrayList<Physical> collides = physicsEngine.getEntitiesCollided(getWorldX()-.05f,getWorldY()-.05f,.1f,.1f);
        for (Physical p : collides) {
            if (p.getWorldX() == getWorldX() && p.getWorldY() == getWorldY()){
                return false;
            }
        }

        return ground == Ground.Types.TILLED_DIRT.getGround() ;
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
