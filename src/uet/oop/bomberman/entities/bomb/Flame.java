package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;

public class Flame extends Entity {

    private LocalDateTime timeStartFlame;
    public Flame(int xUnit, int yUnit, Image img, LocalDateTime timeStartFlame) {
        super(xUnit, yUnit, img);
        this.timeStartFlame = timeStartFlame;
    }

    public void update() {

    }

    public boolean checkEndFlame() {
        LocalDateTime timeEndFlame = LocalDateTime.now();
        if (Duration.between(timeStartFlame, timeEndFlame).toMillis() >= 250) {
            return true;
        }
        return false;
    }
}
