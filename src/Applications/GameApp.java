package Applications;

import static GameEngine.GameConstants.TIME_LIMIT_IN_SECONDS;
import static GameEngine.GameConstants.WINDOW_HEIGHT;
import static GameEngine.GameConstants.WINDOW_WIDTH;
import static GameEngine.GameConstants.loanSharkTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import Assets.images.ImageLoader;
import GameEngine.Animation;
import GameEngine.Block;
import GameEngine.Entities.Boat;
import GameEngine.Entities.Entity;
import GameEngine.Entities.LoanShark;
import GameEngine.Entities.Seed;
import GameEngine.Ground;
import GameEngine.MapGenerator;
import GameEngine.MusicPlayer;
import GameEngine.Physics.Physical;
import GameEngine.Physics.PhysicsEngine;
import GameEngine.Entities.Player;
import GameEngine.Rendering.Cameras.ConvergeCamera;
import GameEngine.Rendering.Renderer;
import lib.Clock;
import lib.KeyInput;
import lib.MouseInput;
import lib.Quadtree.Rect;
import lib.Time;
import lib.VisualCounter;

public class GameApp extends JPanel implements Runnable {
    protected Thread gameThread;
    protected ArrayList<Entity> entities = new ArrayList<>();
    protected ArrayList<Entity> toDespawn = new ArrayList<>();
    protected PhysicsEngine physics;
    public static float timeMultiplier = 1;
    private VisualCounter weetLabel;
    private VisualCounter seedLabel;
    private MusicPlayer musicPlayer;
    private boolean win;
    protected Player player;
    protected Renderer renderer;
    protected MouseInput mouseInput;
    private float secondsLeft = TIME_LIMIT_IN_SECONDS;
    private VisualCounter timer;

    public void setMouseInput(MouseInput mi) {
        mouseInput = mi;
    }
    public GameApp() {
        win = false;

        this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        player = new Player(14,14,this);
        renderer = new Renderer(new ConvergeCamera(player,10),3);
        MapGenerator.generate();
        physics = new PhysicsEngine(MapGenerator.getMap());
        seedLabel = new VisualCounter(
                new Animation(new Image[] {ImageLoader.load("blocks/seed/00.png").getScaledInstance(32,32,0)},1),
                0,0
        );
        add(seedLabel);
        seedLabel.setCount(10);
        weetLabel = new VisualCounter(
                new Animation(new Image[] {ImageLoader.load("blocks/wheet/0.png")},1),
                0,35
        );
        add(weetLabel);
        timer = new VisualCounter(
                new Animation(new Image[] {ImageLoader.load("gui/ded.png")},0),
                0,90);
        add(timer);


    }
    public void killEntity(Entity e) {
        if (toDespawn.contains(e) || e.dead) return;
        e.dead = true;
        toDespawn.add(e);
    }
    private void manageEntities(ArrayList<Entity> entities) {
        if (toDespawn.size()!=0) return;
        for (Entity entity : entities) {
            if (entity.getShouldDespawn()) {
                killEntity(entity);
            }
        }
        while (toDespawn.size() > 0) {
            Entity toKill = toDespawn.removeLast();
            entities.remove(toKill);
            renderer.removeRenderable(toKill);
            if (!physics.removeMovable(toKill)) {
                physics.removeImmovableEntity(toKill);
            }
            toKill.onDespawn(this);
        }
    }
    public boolean spawnEntity(Entity entity, int layer,Entity.Type type) {
        return spawnEntity(entity,layer,type,true);
    }

    public boolean spawnEntity(Entity entity, int layer,Entity.Type type, boolean visible) {
        if (!entity.canSpawnOn(physics)) return false;
        entity.startAnimation();
        entities.add(entity);
        if (visible)
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
        clock.tick(1000);
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
        seedLabel.paint((Graphics2D) g);
        weetLabel.paint((Graphics2D) g);
        timer.paint((Graphics2D) g);

    }
    public void makeDynamicWaterBarriers() {
        Ground water = Ground.Types.WATER.getGround();
        for (int y = 0; y < MapGenerator.getMap().length;y++) {
            for (int x = 0; x < MapGenerator.getMap()[0].length;x++) {
                Ground.Types g = MapGenerator.getGroundAt(x,y).getType();
                if (g== Ground.Types.WATER) {
                    if (MapGenerator.getGroundAt(x-1,y) != water ||
                        MapGenerator.getGroundAt(x+1,y) != water ||
                        MapGenerator.getGroundAt(x,y-1) != water ||
                        MapGenerator.getGroundAt(x,y+1) != water) {
                        spawnEntity(Block.Types.INVISIBLE.makeBlock(x,y),0, Entity.Type.IMMOVABLE_GRID_BOUND,false);

                    }


                }
            }
        }
    }
    public void init() {
        musicPlayer = new MusicPlayer("0",60*5);
        musicPlayer.setVolume(-50);
        int offsetX = 10;
        int offsetY = -3;

        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+10,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+11,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+12,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+13,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+14,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+10,offsetY+14),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+11,offsetY+14),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+13,offsetY+14),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+14,offsetY+14),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+14),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+10,offsetY+11),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+10,offsetY+12),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+10,offsetY+13),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+10),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+11),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+12),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        spawnEntity(Block.Types.WOOD.makeBlock(offsetX+15,offsetY+13),1, Entity.Type.IMMOVABLE_GRID_BOUND);
        makeDynamicWaterBarriers();
        spawnEntity(player, 1, Entity.Type.MOVABLE);
        renderer.setHalfWindowWidth(getWidth()/2);
        renderer.setHalfWindowHeight(getHeight()/2);

        // Spawn All Entities (non-player)

        // Spawn LoanShark
        LoanShark ls = new LoanShark(15,40);
        spawnEntity(new Boat(15,39.5f),1,Entity.Type.MOVABLE);

        spawnEntity(ls,1,Entity.Type.MOVABLE);
        spawnEntity(Block.Types.INVISIBLE.makeBlock(15,40),1,Entity.Type.IMMOVABLE_GRID_BOUND);
    }
    public void update() {

        if (!musicPlayer.running() && secondsLeft < 250) {

            musicPlayer.start();
        }
        secondsLeft -= Time.deltaTime * timeMultiplier;
        timer.setCount((int)secondsLeft);
        if (secondsLeft <= 0) {
            gameThread = null;
            return;
        }
        for (Entity e : entities) {
            e.update();
        }
        if (seedLabel.getCount() > 0 && mouseInput.mouseButtonsClicked[0]) {
            if (spawnEntity(new Seed((int)renderer.getWorldXFromScreenX(mouseInput.mousePosition.x),(int)renderer.getWorldYFromScreenY(mouseInput.mousePosition.y)),
            0,Entity.Type.IMMOVABLE)) {
                seedLabel.setCount(seedLabel.getCount() - 1);
            }
        }

        if (KeyInput.getKey(KeyEvent.VK_DOWN)) {
            boolean consumed = false;
            Rect r = player.getAsRect().inflate(3,3);
            for (Physical p : physics.getEntitiesCollided(r)) {
                if (p.getTag().equals(loanSharkTag)) {
                    LoanShark ls = (LoanShark)p;
                    ls.acceptWeet(weetLabel.getCount());
                    weetLabel.setCount(0);
                    consumed = true;
                    if (ls.isHappy()) {
                        gameThread = null;
                        win = true;
                    }
                    break;
                }
            }
            if (!consumed)
                player.beginHarvesting();

        }
        physics.update();
        renderer.update();
        if (KeyInput.getKey(KeyEvent.VK_U) && KeyInput.getKey(KeyEvent.VK_I) && KeyInput.getKey(KeyEvent.VK_O) && KeyInput.getKey(KeyEvent.VK_P)) {
            timeMultiplier = 5;
        }

    }
    public PhysicsEngine getPhysics() {return physics;}
    public void playerCollectWeet(int amount) {
        weetLabel.setCount(weetLabel.getCount()+amount);
    }
    public void playerCollectSeed(int amount) {
        seedLabel.setCount(seedLabel.getCount()+amount);
    }
    public boolean isWon() {
        return win;
    }
    public boolean isDone() {
        if (gameThread == null) musicPlayer.stop();
        return gameThread == null;
    }
}
