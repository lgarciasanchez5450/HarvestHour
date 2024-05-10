package lib;

public class Time {
    public static double deltaTime;
    public static double time;
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

}
