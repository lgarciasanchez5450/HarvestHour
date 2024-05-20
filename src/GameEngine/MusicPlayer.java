package GameEngine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable {
    private Thread thread;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private float timer;
    private  String name;
    public MusicPlayer(String name, float everyHowManySeconds) {
        timer = everyHowManySeconds;
        this.name = name;
    }
    public void run() {
        while (thread != null) {
            try {

                audioInputStream = AudioSystem.getAudioInputStream(new File("src/Assets/sounds/" + name + ".wav"));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

            }catch (Exception ignored) {}
            try {
                Thread.sleep((long)(timer*1000));
            }catch (Exception ignored) {}
        }
    }
    public void start() {

        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread = null;
        clip.stop();
    }
    public void setVolume(float vol) {
        try {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(vol); // Reduce volume by 10 decibels.
        } catch (Exception ignored) {}
    }
    public boolean running() {
        return thread != null;
    }

}
