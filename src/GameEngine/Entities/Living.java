package GameEngine.Entities;


public abstract class Living extends Entity {
    protected int hp,totalHp;
    protected float velX,velY;

    protected Living(float x, float y,float width, float height) {
        super(x, y, width, height);
    }

    public boolean isAlive() {return hp > 0; }
    public boolean atFullHealth() {return hp >= totalHp; } // It may be possible that Living can be above totalHp and we still count that as Full health
    public float getVelX() {
        return velX;
    }


    public float getVelY() {
        return velY;
    }
    @Override
    public void setVelX(float x) {
        velX = x;
    }

    @Override
    public void setVelY(float y) {
        velY = y;
    }
}
