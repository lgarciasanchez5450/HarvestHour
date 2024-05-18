package GameEngine.Rendering.Cameras;
import GameEngine.Rendering.HasPosition;

public class Camera {
    protected HasPosition target;
    protected float camX,camY;
    public Camera(HasPosition target) {
        this.target = target;
        camX = target.getWorldX();
        camY = target.getWorldY();
    }
    public void setTarget(HasPosition t) {
        target = t;
    }
    public void update() {
        camX = target.getWorldX();
        camY = target.getWorldY();
    }
    public float getCameraX() { return camX; }
    public float getCameraY() { return camY; }
}
