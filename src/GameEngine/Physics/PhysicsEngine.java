package GameEngine.Physics;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import GameEngine.Ground;
import lib.Time;


public class PhysicsEngine {

    private static final PhysicalGround defaultGround = Ground.Types.WATER.getGround();
    private final PhysicalGround[][] map;
    private final Immovable[][] blocks;
    private final ArrayList<Movable> entities;
    public PhysicsEngine(PhysicalGround[][] map, Immovable[][] blocks) {
        this.map = map;
        // The map is so big that we don't want to create a copy of it even though it is theoretically the
        // Best OOP practice  to decouple the client from this class, plus because of the way that JVM works
        // We have no guarantee that it will even be garbage collected until the end of the program's life
        // Because Java sucks and when Sun Microsystems made it, that was possibly the worst decision made by a
        // Company. EVER!
        this.entities = new ArrayList<>();
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
        final float dt = (float) Time.deltaTime;
        for (Movable m : entities) {
            //System.out.println(m.getWorldX() + ";"+m.getWorldY());
            m.setWorldX(m.getWorldX() + m.getVelX() * dt);
            collideHorizontalBlocks(m);
            m.setWorldY(m.getWorldY() + m.getVelY() * dt);
            collideVerticalBlocks(m);
        }
    }
    public void addMovable(Movable m) {entities.add(m);}
    public void removeMovable(Movable m) {entities.remove(m);}

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
            if (obj == null) {continue;}
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
            if (obj == null) {
                continue;
            }
            if (touchingBlock(m, obj, coordinate[0], coordinate[1])) {
                touched = true;
                if (vel > 0) { // moving down
                    m.setWorldY(coordinate[1] + obj.getPosOffsetY() - m.getSizeY());
                } else { // moving up
                    m.setWorldY(coordinate[1] + obj.getPosOffsetY() + obj.getSizeY());
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

}
