package GameEngine.Entities;

import static GameEngine.GameConstants.playerTag;

import java.awt.event.KeyEvent;

import Applications.GameApp;
import Assets.images.ImageLoader;
import GameEngine.Animation;
import lib.KeyInput;
import lib.Time;

public class Player extends Living {
    private final static float ONE_OVER_SQRT_TWO = 0.707106781188f;
    public float harvestingRadius;
    public float speed;
    private boolean isHarvesting;
    private float harvestingTimeLeft;
    private final GameApp game;
    public Player(float x, float y, GameApp gameApp) {
        super(playerTag, x, y,.6f,.6f);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/player/idle"),1);
        setAnchorX(.5f);
        setAnchorY(.5f);
        game = gameApp;
        harvestingRadius = 1;
        speed = 2;
    }
    public void beginHarvesting() {
        if (isHarvesting) return;
        isHarvesting = true;
        harvestingTimeLeft = .36f;
        game.spawnEntity(new Harvester(getWorldX(),getWorldY(),harvestingRadius
        ),2,Type.IMMOVABLE);
        System.out.println("Player:"+getWorldX()+";"+getWorldY());
    }
    public void update() {
        if (isHarvesting) {
            harvestingTimeLeft -= Time.deltaTime;
            isHarvesting = harvestingTimeLeft > 0;
            velX = 0;
            velY = 0;
        } else {
            velY = KeyInput.getKeyAsInt(KeyEvent.VK_S) - KeyInput.getKeyAsInt(KeyEvent.VK_W);
            velX = KeyInput.getKeyAsInt(KeyEvent.VK_D) - KeyInput.getKeyAsInt(KeyEvent.VK_A);
            if (velX != 0 && velY != 0) { // This normalizes the velocity
                velX *= ONE_OVER_SQRT_TWO;
                velY *= ONE_OVER_SQRT_TWO;
            }
            velX *= speed;
            velY *= speed;
        }
    }

    public String toString() {
        return "Player{pos("+getWorldX() + ", " + getWorldY()+")}";
    }
}
