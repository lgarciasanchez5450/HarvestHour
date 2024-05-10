import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import GameEngine.Animation;
import GameEngine.Block;
import GameEngine.Entity;
import GameEngine.MapGenerator;
import GameEngine.Physics.PhysicsEngine;
import GameEngine.Player;
import GameEngine.Rendering.Cameras.ConvergeCamera;
import GameEngine.Rendering.Renderer;
import lib.Clock;
import lib.Time;

public class GameApp extends JPanel implements Runnable {
    private Thread gameThread;
    ArrayList<Entity> entities = new ArrayList<>();
    PhysicsEngine physics;
    Player player;
    Renderer renderer;
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
    private void spawnEntity(Entity entity) {
        entities.add(entity);
        renderer.addRenderable(entity);
        physics.addMovable(entity);
    }
    private void spawnBlock(Block block) {
        entities.add(block);
        renderer.addRenderable(block);
        physics.addBlock(block,(int)block.getWorldX(),(int)block.getWorldY());
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
        spawnEntity(player);
        spawnBlock(Block.Types.TEST.makeBlock(10,10));
        renderer.setHalfWindowWidth(getWidth()/2);
        renderer.setHalfWindowHeight(getHeight()/2);
    }
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
        physics.update();
        renderer.update();

    }

}
