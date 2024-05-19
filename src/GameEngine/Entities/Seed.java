package GameEngine.Entities;

import static GameEngine.GameConstants.BLOCK_SIZE;
import static GameEngine.GameConstants.seedTag;

import java.util.ArrayList;

import Applications.GameApp;
import Assets.images.ImageLoader;
import GameEngine.Animation;
import GameEngine.Ground;
import GameEngine.Physics.Physical;
import GameEngine.Physics.PhysicsEngine;

public class Seed extends Entity{
    public static final float CHANCE_TO_GROW_PER_FRAME = 0.005f;
    public Seed(int x, int y) {
        super(seedTag,x, y, 1,1,0);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/blocks/seed",BLOCK_SIZE,BLOCK_SIZE),0);
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
    public void update() {
        if (currentAnimation.isFinishedCycle()) {
            shouldDespawn = true;
        }
        if (Math.random()<CHANCE_TO_GROW_PER_FRAME) {
            currentAnimation.nextFrame();
        }
    }
    @Override
    public void onDespawn(GameApp game) {
        game.spawnEntity(new Weet((int)getWorldX(),(int)getWorldY()),1,Type.IMMOVABLE);
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
