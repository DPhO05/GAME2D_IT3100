package entity;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandlers {

    public boolean leftPressed, rightPressed, jPressed, kPressed, wPressed, sPressed;
    public boolean leftPressed1, rightPressed1, Pressed1, Pressed2, Pressed3, Pressed0;


    // Phương thức để xử lý sự kiện khi một phím được nhấn
    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            wPressed = true;
        }
        if (code == KeyCode.A) {
            leftPressed = true;
        }
        if (code == KeyCode.D) {
            rightPressed = true;
        }
        if (code == KeyCode.S) {
            sPressed = true;
        }
        if (code == KeyCode.J){
            jPressed = true;
        }
        if (code == KeyCode.K) {
            kPressed = true;
        }
        if (code == KeyCode.N) {
            leftPressed1 = true;
        }
        if (code == KeyCode.M){
            rightPressed1 = true;
        }
        if (code == KeyCode.V){
            Pressed1 = true;
        }
        if (code == KeyCode.END){
            Pressed0 = true;
        }
        if (code == KeyCode.Z){
            Pressed2 = true;
        }
        if (code == KeyCode.DIGIT3){
            Pressed3 = true;
        }


    }

    // Phương thức để xử lý sự kiện khi một phím được thả ra
    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            wPressed = false;
        }
        if (code == KeyCode.A) {
            leftPressed = false;
        }
        if (code == KeyCode.D) {
            rightPressed = false;
        }
        if (code == KeyCode.S) {
            sPressed = false;
        }
        if (code == KeyCode.J){
            jPressed = false;
        }
        if (code == KeyCode.K) {
            kPressed = false;
        }
        if (code == KeyCode.N){
            leftPressed1 = false;
        }
        if (code == KeyCode.M){
            rightPressed1 = false;
        }
        if (code == KeyCode.V){
            Pressed1 = false;
        }
        if (code == KeyCode.END){
            Pressed0 = false;
        }
        if (code == KeyCode.Z){
            Pressed2 = false;
        }
        if (code == KeyCode.DIGIT3){
            Pressed3 = false;
        }

    }
}
