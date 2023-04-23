import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class MusicThread implements Runnable {
    Thread t;
    public MusicThread(){
        t=new Thread(this);
        //System.out.println("New thread: "+t);
        t.start();
    }
    @Override
    public void run() {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        AudioInputStream inputStream = null;
        try {
            inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/data/8bit-music-for-game-68698.wav")));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(inputStream);
            clip.start();
            System.out.println("Clip Started");
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (!Thread.interrupted()){
            System.out.println("Thread is running");
            }
        System.out.println("Thread is stopped");
        clip.stop();
        System.out.println("Clip stopped");

    }

    }
