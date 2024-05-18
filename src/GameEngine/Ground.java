package GameEngine;

import java.awt.Image;

import Assets.images.ImageLoader;
import GameEngine.Physics.PhysicalGround;

public class Ground implements PhysicalGround {

    final float frictionK;
    final Animation animation;
    public Ground(float frictionCoefficient,String pathToAnim) {
        this.frictionK = frictionCoefficient;
        animation = new Animation(ImageLoader.loadFolder(pathToAnim),1);
        animation.start();
    }
    public Image getCurrentFrame() {
        return animation.getCurrentFrame();
    }


    @Override
    public float getFrictionCoefficient() {
        return frictionK;
    }
    public Types getType(){
        if (Types.WATER.getGround() == this) { return Types.WATER; }
        else if (Types.GRASS.getGround() == this) { return Types.GRASS; }
        else if (Types.SAND.getGround() == this) { return Types.SAND; }
        else if (Types.PLANK.getGround() == this) { return Types.PLANK; }
        else if (Types.PLANK_WATER1.getGround() == this) { return Types.PLANK_WATER1; }
        else if (Types.PLANK_WATER2.getGround() == this) { return Types.PLANK_WATER2; }
        else if (Types.TILLED_DIRT.getGround() == this) { return Types.TILLED_DIRT; }
        return Types.WATER;
    }
    public enum Types {
        WATER(new Ground(1,"src/Assets/images/ground/water")),
        GRASS(new Ground(5,"src/Assets/images/ground/grass")),
        SAND(new Ground(8,"src/Assets/images/ground/sand")),
        PLANK(new Ground(8,"src/Assets/images/ground/plank")),
        PLANK_WATER1(new Ground(8,"src/Assets/images/ground/waterplankmerge1")),
        PLANK_WATER2(new Ground(8,"src/Assets/images/ground/waterplankmerge2")),
        TILLED_DIRT(new Ground(8,"src/Assets/images/ground/tilled_dirt"));
        private final Ground g;
        public Ground getGround() {return g;}
        Types(Ground g) {this.g = g;}

    }
}
