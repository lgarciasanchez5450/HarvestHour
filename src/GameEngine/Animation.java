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
    private float fps;
    private float timePassed;
    private boolean active;

    public Animation(Image[] frames, float fps) {
        this.frames = Arrays.copyOf(frames,frames.length); // Get a copy of the frames
        this.fps = fps;
    }
    public float getFps() { return fps; }
    public void setFps(float fps) { this.fps = fps; }

    public static void updateAll() {
        for (Animation anim : animationsOn)
            anim.timePassed += Time.deltaTime * anim.fps;
    }

    public void start() {
        if (active) return;
        animationsOn.add(this);
        active = true;
    }
    public void stop() {
        if (!active) return;
        animationsOn.remove(this);
        active = false;
    }


    public Image getCurrentFrame() {
        return frames[(int)timePassed % frames.length];
    }
}
