package GameEngine.Rendering.Cameras;

import java.util.HashMap;

import GameEngine.Rendering.HasPosition;

public class FixedCamera extends Camera { // Technically Camera is also a FixedCamera but this one does less work therefore is faster
    public FixedCamera(HasPosition target) {
        super(target);
    }
    public void update() {}
    public float getCameraX() { return target.getWorldX(); }
    public float getCameraY() { return target.getWorldY(); }
}
