package Applications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GameEngine.Animation;
import GameEngine.Block;
import GameEngine.Entities.Entity;
import GameEngine.Entities.LoanShark;
import GameEngine.Entities.Seed;
import GameEngine.MapGenerator;
import GameEngine.Physics.PhysicsEngine;
import GameEngine.Entities.Player;
import GameEngine.Rendering.Cameras.ConvergeCamera;
import GameEngine.Rendering.Renderer;
import lib.Clock;
import lib.KeyInput;
import lib.Label;
import lib.MouseInput;
import lib.Time;

public class GameApp extends JPanel implements Runnable {
    protected Thread gameThread;
    protected ArrayList<Entity> entities = new ArrayList<>();
    protected HashSet<Entity> toDespawn = new HashSet<>();

    protected PhysicsEngine physics;
    private int weetInPlayer;
    private int seedsInPlayer;
    private Label weetLabel;
    private Label seedLabel;
    protected Player player;
    protected Renderer renderer;
    protected MouseInput mouseInput;

    public void setMouseInput(MouseInput mi) {
        mouseInput = mi;
    }

    public GameApp() {
        this.setPreferredSize(new Dimension(900,600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        player = new Player(14,14,this);
        player.speed = 5;
        renderer = new Renderer(new ConvergeCamera(player,10),3);
        MapGenerator.generate();
        physics = new PhysicsEngine(MapGenerator.getMap());


        // Setup Game
    }
    public void killEntity(Entity e) {
        toDespawn.add(e);
    }
    private void manageEntities(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.getShouldDespawn()) {
                toDespawn.add(entity);
            }
        }
        for (Entity toKill: toDespawn) {
            entities.remove(toKill);
            renderer.removeRenderable(toKill);
            if (!physics.removeMovable(toKill)) {
                physics.removeImmovableEntity(toKill);
            }
            toKill.onDespawn(this);
        }
        toDespawn.clear();
    }
    public boolean spawnEntity(Entity entity, int layer,Entity.Type type) {
        if (!entity.canSpawnOn(physics)) return false;
        entity.startAnimation();
        entities.add(entity);
        renderer.addRenderable(entity,layer);
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
            Point mpos = getMousePosition();
            if (mpos != null) {
                mouseInput.mousePositionDelta.x = mpos.x - mouseInput.mousePosition.x;
                mouseInput.mousePositionDelta.y = mpos.y - mouseInput.mousePosition.y;
                mouseInput.mousePosition = mpos;
            } else {
                mouseInput.mousePositionDelta.x = 0;
                mouseInput.mousePositionDelta.y = 0;
            }
            update();
            manageEntities(entities);
            KeyInput.reset();

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
        spawnEntity(player, 1, Entity.Type.MOVABLE);
        spawnEntity(Block.Types.TEST.makeBlock(10,10), 1,Entity.Type.IMMOVABLE_GRID_BOUND);
        renderer.setHalfWindowWidth(getWidth()/2);
        renderer.setHalfWindowHeight(getHeight()/2);

        // Spawn All Entities (non-player)

        // Spawn LoanShark
        LoanShark ls = new LoanShark(15,40);

        spawnEntity(ls,1,Entity.Type.MOVABLE);
        spawnEntity(Block.Types.INVISIBLE.makeBlock(15,40),1,Entity.Type.IMMOVABLE_GRID_BOUND);
    }
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
        if (seedsInPlayer > 0 && mouseInput.mouseButtonsClicked[0]) {
            spawnEntity(new Seed((int)renderer.getWorldXFromScreenX(mouseInput.mousePosition.x),(int)renderer.getWorldYFromScreenY(mouseInput.mousePosition.y)),
            0,Entity.Type.IMMOVABLE);
            seedsInPlayer--;
        }
        if (mouseInput.mouseButtonsClicked[2]) {
            System.out.println(renderer.getNumRenderables());
        }
        if (KeyInput.getKey(KeyEvent.VK_DOWN)) {
            player.beginHarvesting();
        }
        physics.update();
        renderer.update();

    }
    public PhysicsEngine getPhysics() {return physics;}

}
