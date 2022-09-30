package uet.oop.bomberman.entities.bomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;

public class Bomb extends Entity {
    private boolean isVisible = true;
    public boolean activate = false;
    public final int startTime = 2;
    private int seconds = 2;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void update() {

    }

    public void explo(int index) {
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if(time!=null) {
            time.stop();
        }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                seconds--;
                if(seconds<=0) {
                    NttGroup.removeBomb();
                    NttGroup.bombers.get(0).bombs.remove(index);
                    time.stop();
                }
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
