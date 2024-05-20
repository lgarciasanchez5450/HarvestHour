package GameEngine.Physics;

public interface Movable extends Physical { // Things that can be put into the Physics engine for it to control

    void setWorldX(float x);
    void setWorldY(float y);

    float getVelX();
    float getVelY();

    void setVelX(float x);
    void setVelY(float y);

}
