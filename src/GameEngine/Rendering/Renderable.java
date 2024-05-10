package GameEngine.Rendering;

import java.awt.Image;

public interface Renderable extends HasPosition{
    Image getCurrentFrame();
    float getSizeX();
    float getSizeY();

    float getAnchorX();
    float getAnchorY();

}
