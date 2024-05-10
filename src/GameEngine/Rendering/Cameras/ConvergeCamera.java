package GameEngine.Rendering.Cameras;

import GameEngine.Rendering.HasPosition;
import lib.Time;

public class ConvergeCamera extends Camera {
    private float convergeSpeed;
    public ConvergeCamera(HasPosition target, float convergeSpeed) {
        super(target);
        this.convergeSpeed = convergeSpeed;

    }
    public void update() {
        camX += (target.getWorldX() - camX) * convergeSpeed * Time.deltaTime;
        camY += (target.getWorldY() - camY) * convergeSpeed * Time.deltaTime;
    }
}
