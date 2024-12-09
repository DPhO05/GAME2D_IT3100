package entity;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandlers1 {


    public boolean leftPressed1, rightPressed1, Pressed1, Pressed2, Pressed3, Pressed0;


    // Phương thức để xử lý sự kiện khi một phím được nhấn
    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();


        if (code == KeyCode.LEFT){
            leftPressed1 = true;
        }
        if (code == KeyCode.RIGHT){
            rightPressed1 = true;
        }
        if (code == KeyCode.DIGIT1){
            Pressed1 = true;
        }
        if (code == KeyCode.DIGIT0){
            Pressed0 = true;
        }
        if (code == KeyCode.DIGIT2){
            Pressed2 = true;
        }
        if (code == KeyCode.DIGIT3){
            Pressed3 = true;
        }
    }

    // Phương thức để xử lý sự kiện khi một phím được thả ra
    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.LEFT){
            leftPressed1 = false;
        }
        if (code == KeyCode.RIGHT){
            rightPressed1 = false;
        }
        if (code == KeyCode.DIGIT1){
            Pressed1 = false;
        }
        if (code == KeyCode.DIGIT0){
            Pressed0 = false;
        }
        if (code == KeyCode.DIGIT2){
            Pressed2 = false;
        }
        if (code == KeyCode.DIGIT3){
            Pressed3 = false;
        }
    }
}
