package GameEngine;

import java.awt.event.KeyEvent;

import Assets.images.ImageLoader;
import lib.KeyInput;

public class Player extends Living {
    private final static float ONE_OVER_SQRT_TWO = 0.707106781188f;
    public float speed;
    public Player(float x, float y) {
        super(x, y,.8f,.8f);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/player/idle"),1);
        setAnchorX(.5f);
        setAnchorY(.5f);
    }
    public void update() {
        velY = KeyInput.getKeyAsInt(KeyEvent.VK_S) - KeyInput.getKeyAsInt(KeyEvent.VK_W);
        velX = KeyInput.getKeyAsInt(KeyEvent.VK_D) - KeyInput.getKeyAsInt(KeyEvent.VK_A);
        if (velX != 0 && velY != 0) { // This normalizes the velocity 
            velX *= ONE_OVER_SQRT_TWO;
            velY *= ONE_OVER_SQRT_TWO;
        }
        velX *= speed;
        velY *= speed;
    }

    public String toString() {
        return "Player{pos("+getWorldX() + ", " + getWorldY()+")}";
    }
}
