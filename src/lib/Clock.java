package lib;

import static lib.Time.sleep;

public class Clock {
    private long lastTime; // In nanoseconds
    public double tick(long fps) {
        long startTime = System.nanoTime();
        long nanosSinceLastCall = startTime - lastTime;
        long nanosToSleepTotalForPerfectFPS = 1_000_000_000/fps;
        if (nanosSinceLastCall < nanosToSleepTotalForPerfectFPS) {
            sleep((nanosToSleepTotalForPerfectFPS - nanosSinceLastCall)/1_000_000 );
        }
        long currentTime = System.nanoTime();
        lastTime = currentTime;
        return (double)(nanosSinceLastCall + currentTime - startTime) / 1_000_000_000;
    }}
