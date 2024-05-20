package lib.Quadtree;

public class Rect {
    private final float x,y,width,height;

    public Rect(float x,float y,float w,float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public float getX(){return x;}
    public float getY(){return y;}
    public float getWidth(){return width;}
    public float getHeight(){return height;}
    public boolean containsPoint(float x, float y) {
        return x >=this.x && y >= this.y && x < this.x+width && y < this.y+height;
    }
    public boolean intersectRect(Rect r) {
        boolean widthIsPositive  = Math.min(x+width, r.x+r.width) > Math.max(x, r.x);
        boolean heightIsPositive = Math.min(y+height, r.y+r.height) > Math.max(y, r.y);
        return (widthIsPositive && heightIsPositive);

    }

    public Rect inflate(float dx, float dy) {
        return new Rect(x-dx/2,y-dy/2,width+dx,height+dy);
    }


}
