package Applications;

import static GameEngine.GameConstants.BLOCK_SIZE;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import GameEngine.Entities.Entity;
import GameEngine.Entities.Player;
import GameEngine.MapGenerator;
import GameEngine.Rendering.MapMakerGroundHUD;
import lib.KeyInput;
import lib.MouseInput;

public class MapMaker extends GameApp {
    Entity camPos;
    MapMakerGroundHUD groundHUD;

    public MapMaker() {
        this.setPreferredSize(new Dimension(1600,900));
        camPos = new Player(14,14);
        renderer.getCamera().setTarget(camPos);
        groundHUD = new MapMakerGroundHUD();
    }
    public void init() {
        renderer.setHalfWindowWidth(getWidth()/2);
        renderer.setHalfWindowHeight(getHeight()/2);
        groundHUD.setHalfWindowWidth(getWidth()/2);
        groundHUD.setHalfWindowHeight(getHeight()/2);
    }
    public void setMouseInput(MouseInput mi) {
        super.setMouseInput(mi);
        groundHUD.setMouseInput(mi);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        groundHUD.paint(g);
    }
    public void update() {
        camPos.update();
        if (mouseInput.mouseButtonsDown[1]) {
            camPos.setVelX(-mouseInput.mousePositionDelta.x / (float)BLOCK_SIZE);
            camPos.setVelY(-mouseInput.mousePositionDelta.y / (float)BLOCK_SIZE);
        } else {
            camPos.setVelX(0);
            camPos.setVelY(0);
        }
        groundHUD.setShowing(KeyInput.getKey(KeyEvent.VK_G));
        if (mouseInput.mouseButtonsDown[0] && !groundHUD.getShowing()) {
            int x = (int)renderer.getWorldXFromScreenX(mouseInput.mousePosition.x);
            int y = (int)renderer.getWorldYFromScreenY(mouseInput.mousePosition.y);
            MapGenerator.setGroundAt(x,y,groundHUD.getGroundSelected());
        }
        camPos.setWorldX(camPos.getWorldX() + camPos.getVelX());
        camPos.setWorldY(camPos.getWorldY() + camPos.getVelY());
        groundHUD.update();
        renderer.update();
    }
}