import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import GameEngine.Animation;
import GameEngine.Block;
import GameEngine.Entities.Entity;
import GameEngine.Entities.Weet;
import GameEngine.MapGenerator;
import GameEngine.Physics.PhysicsEngine;
import GameEngine.Player;
import GameEngine.Rendering.Cameras.ConvergeCamera;
import GameEngine.Rendering.Renderer;
import lib.Clock;
import lib.MouseInput;
import lib.Time;

public class GameApp extends JPanel implements Runnable {

    private Thread gameThread;
    ArrayList<Entity> entities = new ArrayList<>();
    PhysicsEngine physics;
    Player player;
    Renderer renderer;
    private MouseInput mouseInput;

    public void setMouseInput(MouseInput mi) {
        mouseInput = mi;
    }
    GameApp() {
        this.setPreferredSize(new Dimension(900,600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        // Setup Game
        MapGenerator.generate();
        physics = new PhysicsEngine(MapGenerator.getMap());
        player = new Player(14,14);
        player.speed = 5;

        //Setup Render Stuffs
        renderer = new Renderer(new ConvergeCamera(player,10));
    }


    private boolean spawnEntity(Entity entity, Entity.Type type) {
        if (!entity.canSpawnOn(physics)) return false;
        entities.add(entity);
        renderer.addRenderable(entity);
        switch (type) {
            case MOVABLE -> physics.addMovable(entity);
            case IMMOVABLE -> physics.addImmovableEntity(entity);
            case IMMOVABLE_GRID_BOUND -> {
                Block block = (Block) entity;
                physics.addBlock(block, (int) block.getWorldX(), (int) block.getWorldY());
            }
        }
        return true;
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        Clock clock = new Clock();
        Time.time = 0;
        Time.deltaTime = 0;
        final long startTime = System.nanoTime();

        init();
        while (gameThread != null) {
            mouseInput.update();
            mouseInput.mousePosition = getMousePosition();
            update();
            Animation.updateAll();
            repaint();
            Time.deltaTime = clock.tick(Application.FPS);
            Time.time = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.paint(g);
    }

    public void init() {
        spawnEntity(player, Entity.Type.MOVABLE);
        spawnEntity(Block.Types.TEST.makeBlock(10,10), Entity.Type.IMMOVABLE_GRID_BOUND);
        renderer.setHalfWindowWidth(getWidth()/2);
        renderer.setHalfWindowHeight(getHeight()/2);
    }
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
        if (mouseInput.mouseButtonsClicked[0]) {
            System.out.println(spawnEntity(new Weet((int)renderer.getWorldXFromScreenX(mouseInput.mousePosition.x),(int)renderer.getWorldYFromScreenY(mouseInput.mousePosition.y))
            ,Entity.Type.IMMOVABLE));
        }
        if (mouseInput.mouseButtonsClicked[2]) {
            System.out.println(renderer.getNumRenderables());
        }
        physics.update();
        renderer.update();

    }

}
