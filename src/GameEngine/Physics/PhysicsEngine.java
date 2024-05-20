package GameEngine.Physics;

import static GameEngine.GameConstants.MAP_SIZE_X;
import static GameEngine.GameConstants.MAP_SIZE_Y;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Applications.GameApp;
import GameEngine.Ground;
import lib.Quadtree.Quadtree;
import lib.Quadtree.Rect;
import lib.Time;


public class PhysicsEngine {

    private static final PhysicalGround defaultGround = Ground.Types.WATER.getGround();
    private final PhysicalGround[][] map;
    private final Immovable[][] blocks;
    private final ArrayList<Movable> entities;
    private final Quadtree q_nm_entities;
    public PhysicsEngine(PhysicalGround[][] map, Immovable[][] blocks) {
        this.map = map;
        // The map is so big that we don't want to create a copy of it even though it is theoretically the
        // Best OOP practice  to decouple the client from this class, plus because of the way that JVM works
        // We have no guarantee that it will even be garbage collected until the end of the program's life
        // Because Java sucks and when Sun Microsystems made it, that was possibly the worst decision made by a
        // Company. EVER!
        this.entities = new ArrayList<>();
        q_nm_entities = new Quadtree(0,0,MAP_SIZE_X,MAP_SIZE_Y);
        this.blocks = blocks;

    }
    public PhysicsEngine(PhysicalGround[][] map) {
        this(map,new Immovable[map.length][map[0].length]);
    }
    @NotNull
    public PhysicalGround getGroundAt(int x, int y) {
        if (x < 0 || y < 0 || x >=map.length || y >= map[0].length) return defaultGround;
        return map[y][x];
    }

    public void update() {
        final float dt = (float) Time.deltaTime * GameApp.timeMultiplier;
        for (Movable m : entities) {
            //System.out.println(m.getWorldX() + ";"+m.getWorldY());
            m.setWorldX(m.getWorldX() + m.getVelX() * dt);
            collideHorizontalBlocks(m);
            m.setWorldY(m.getWorldY() + m.getVelY() * dt);
            collideVerticalBlocks(m);
        }
    }
    public void addMovable(Movable m) {entities.add(m);}
    public void addImmovableEntity(Physical p) {q_nm_entities.insert(p);}
    public boolean removeImmovableEntity(Physical p) { return q_nm_entities.remove(p);}
    public boolean removeMovable(Movable m) {return entities.remove(m);}
    /**
     * Attempts to add an immovable block to the world
     * @param im Block Object, must implement Immovable interface
     * @param worldX World X Position
     * @param worldY World Y Position
     * @return whether the attempt was successful
     */
    public boolean addBlock(Immovable im,int worldX, int worldY) {
        if (blocks[worldY][worldX] != null) return false;
        blocks[worldY][worldX] = im;
        return true;
    }
    public Immovable removeBlock(int worldX, int worldY) {
        Immovable im = blocks[worldY][worldX];
        blocks[worldY][worldX] = null;
        return im;
    }
    public boolean hasBlockAt(int worldX, int worldY) { return blocks[worldY][worldX] !=null; }

    private int[][] getCoordinatesCovered(Movable m) {
        return getCoordinatesCovered(m.getWorldX(),m.getWorldY(),m.getWorldX() + m.getSizeX(), m.getWorldY() + m.getSizeY());
    }
    public static int[][] getCoordinatesCovered(float left, float top, float right, float bottom) {
        return getCoordinatesCovered((int) Math.floor(left), (int) Math.floor(top), (int) Math.floor(right), (int) Math.floor(bottom));
    }
    public static int[][] getCoordinatesCovered(int left, int top, int right, int bottom) {
        final int size = (right-left+1) * (bottom-top+1);
        int[][] coordinates = new int[size][2];
        int i = 0;
        for (int y=top; y <= bottom; y++) {
            for (int x=left; x <= right; x++) {
                coordinates[i][0] = x;
                coordinates[i][1] = y;
                i++;
            }
        }
        return coordinates;
    }

    /**
     * Handles Collisions in the X Axis
     * @param m Movable to handle collision on
     * @return Whether a collision occurred or not.
     */
    private boolean collideHorizontalBlocks(Movable m) {
        float vel = m.getVelX();
        boolean touched = false;
        if (vel == 0) return false;
        for (int[] coordinate : getCoordinatesCovered(m)) {
            if (coordinate[0] < 0 || coordinate[1] < 0) continue;
            Immovable obj = blocks[coordinate[1]][coordinate[0]];
            if (obj == null || !obj.hasCollision()) {continue;}
            int objX = coordinate[0];
            int objY = coordinate[1];
            if (touchingBlock(m,obj,objX,objY)) {
                touched = true;
                if (vel > 0) { // moving right
                    m.setWorldX(objX+obj.getPosOffsetX() - m.getSizeX());
                } else { // moving left
                    m.setWorldX(objX+obj.getPosOffsetX()+obj.getSizeX());
                }
            }
        }
        return touched;
    }
    /**
     * Handles Collisions in the Y Axis
     * @param m Movable to handle collision on
     * @return Whether a collision occurred or not.
     */
    private boolean collideVerticalBlocks(Movable m) {
        float vel = m.getVelY();
        boolean touched = false;
        if (vel == 0) return false;
        for (int[] coordinate : getCoordinatesCovered(m)) {
            if (coordinate[0] <0 || coordinate[1] < 0) continue;
            Immovable obj = blocks[coordinate[1]][coordinate[0]];
            if (obj == null || !obj.hasCollision()) continue;
            int objX = coordinate[0];
            int objY = coordinate[1];
            if (touchingBlock(m, obj, objX, objY)) {
                touched = true;
                if (vel > 0) { // moving down
                    m.setWorldY(objY + obj.getPosOffsetY() - m.getSizeY());
                } else { // moving up
                    m.setWorldY(objY + obj.getPosOffsetY() + obj.getSizeY());
                }
            }
        }
        return touched;
    }

    private boolean touchingBlock(@NotNull Movable m, Immovable block, float blockX,float blockY) {
        float objTop = blockY + block.getPosOffsetY();
        float objLeft = blockX + block.getPosOffsetX();

        return !((objTop + block.getSizeY() <= m.getWorldY()) || (objTop >= m.getWorldY() + m.getSizeY()) ||
                (objLeft + block.getSizeX() <= m.getWorldX() || (objLeft >= m.getWorldX() + m.getSizeX())));
    }

    private boolean entityCollidesWithRect(Rect r, Physical a) {
        Rect r_a = new Rect(a.getWorldX(),a.getWorldY(),a.getSizeX(),a.getSizeY());
        return r.intersectRect(r_a);
    }
    public ArrayList<Physical> getEntitiesCollided(Rect r) {
        ArrayList<Physical> collided = (ArrayList<Physical>) q_nm_entities.getRectIntersect(r);
        for(Movable m : entities) {
            if (entityCollidesWithRect(r,m)) {
                collided.add(m);
            }
        }
        return collided;
    }
    public ArrayList<Physical> getEntitiesCollided(float left, float top, float width, float height) {
        Rect r = new Rect(left,top,width,height);
        return getEntitiesCollided(r);
    }

}
