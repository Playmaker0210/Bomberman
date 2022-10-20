package uet.oop.bomberman.menu;

import uet.oop.bomberman.BombermanGame;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    public static void playSound(String sound) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            BombermanGame.class.getResourceAsStream("/Sound/" + sound + ".wav")
                    );
                    clip.open(inputStream);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-20.0f);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("Loi " + e.getMessage());
                }
            }
        }
        ).start();
    }
}
