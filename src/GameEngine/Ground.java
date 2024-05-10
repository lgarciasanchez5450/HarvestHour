package GameEngine;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Assets.images.ImageLoader;
import GameEngine.Physics.PhysicalGround;
import GameEngine.Rendering.Renderable;

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

    public enum Types {
        WATER(new Ground(1,"src/Assets/images/ground/water")),
        GRASS(new Ground(5,"src/Assets/images/ground/grass")),
        SAND(new Ground(8,"src/Assets/images/ground/sand"));
        private final Ground g;
        public Ground getGround() {return g;}
        Types(Ground g) {this.g = g;}

    }
}
