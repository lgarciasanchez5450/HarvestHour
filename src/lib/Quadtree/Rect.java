package lib.Quadtree;

public class Rect {
    private float x,y,width,height;

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
    public static boolean rectangleOverlap(float x1,
                                      float x2,
                                      float x3,
                                      float x4,
                                      float y1,
                                      float y2,
                                      float y3,
                                      float y4
                                      ) {
        boolean widthIsPositive  = Math.min(x3, y3) > Math.max(x1, y1);
        boolean heightIsPositive = Math.min(x4, y4) > Math.max(x2, y2);
        return (widthIsPositive && heightIsPositive);
    }
    public boolean equals(Rect other) {
        if (other == this) return true;
        return other.x ==x && other.y == y && other.width == width && other.height == height;
    }
}
