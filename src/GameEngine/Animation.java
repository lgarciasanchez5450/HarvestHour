package GameEngine;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

import lib.Time;

/**
 * Holds Information about one cycle of animation
 */
public class Animation {
    private static final ArrayList<Animation> animationsOn = new ArrayList<>();
    private final Image[] frames;
    private int frame;
    private float fps;
    private float timePassed;
    private boolean active;
    private boolean finishedCycle;

    public Animation(Image[] frames, float fps) {
        this.frames = Arrays.copyOf(frames,frames.length); // Get a copy of the frames
        this.fps = fps;
    }
    public float getFps() { return fps; }
    public void setFps(float fps) { this.fps = fps; }

    public static void updateAll() {
        for (Animation anim : animationsOn) {
            if (anim.fps != 0 && anim.frames.length != 0) {
                anim.timePassed += Time.deltaTime * anim.fps;
                int newFrame = (int) anim.timePassed % anim.frames.length;
                anim.finishedCycle = (newFrame != anim.frame && newFrame == 0);
                anim.frame = newFrame;
            }
        }

    }
    public void nextFrame() {
        frame= frames.length==0 ? -1 : (frame+1) %frames.length;
        if (frame == 0) {
            finishedCycle = true;
        }
    }
    public boolean isFinishedCycle() {
        if (finishedCycle){
            finishedCycle = false;
            return true;
        }
        return false;}

    public void start() {
        if (active) return;
        animationsOn.add(this);
        active = true;
    }


    public Image getCurrentFrame() {
        return frames[frame];
    }
}
