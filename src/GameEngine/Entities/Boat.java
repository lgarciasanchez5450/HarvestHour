package GameEngine.Entities;

import static GameEngine.GameConstants.boatTag;

import java.awt.Image;

import Assets.images.ImageLoader;
import GameEngine.Animation;

public class Boat extends Entity{

    public Boat(float x, float y) {
        super(boatTag, x, y, 0, 0);
        currentAnimation = new Animation(new Image[] {ImageLoader.load("blocks/boat/0.png").getScaledInstance(67*10,47*10,0)},0);
        setAnchorY(1);
        setAnchorX(0.5f);

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
