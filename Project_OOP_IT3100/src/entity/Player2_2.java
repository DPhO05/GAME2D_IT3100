package Entity;

import java.io.DataInputStream;
import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.image.Image;

import Entity.Player1_2;
import Chapter2.Chapter2;

public class Player2_2 extends Entity{

    private Image image;
    public double getCurrentImageWidth() {
        return image != null ? image.getWidth() : 0; // Trả về chiều rộng của ảnh hiện tại
    }
    public Player1_2 player1_2;
    private Body body;
    private Body attackBody;
    public int maxHealth = 3000; // Máu tối đa, giả sử là 3.000 để phù hợp với số trên hình ảnh
    public int currentHealth = 3000; // Máu hiện tại
    private int barWidth = 400; // Chiều rộng của thanh máu
    private int barHeight = 20; // Chiều cao của thanh máu
    private int controlDamage = 0;




    private MediaPlayer runningSoundPlayer;
    private MediaPlayer attackSoundPlayer;

    private int animationFrame = 0; // Biến đếm khung hình hiện tại
    private int animationCounter = 0; // Biến đếm thời gian chuyển đổi khung hình
    private final int animationSpeed = 25; // Tốc độ chuyển đổi khung hình
    private boolean isMoving = false; // Biến để kiểm tra xem nhân vật có di chuyển hay không
    private boolean isUsingSkill = false;
    private boolean isUsingJump = false;
    private int skillCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill
    private boolean isUsingSkillK = false;
    private int skillKCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill qua phím K
    Chapter2 gp;
    public KeyHandlers keyH;




    //Interact
    public boolean isAttacking(){
        if (isUsingSkillK || isUsingSkill) return true;
        else return false;
    }

    public boolean isDenfensed(){
        return false;
    }




    public Player2_2(Chapter2 gp, KeyHandlers keyH, Player1_2 player1_2){
        this.gp = gp;
        this.keyH = keyH;
        this.player1_2 = player1_2;
        setDefaultValues();
        getPlayerImage();
        updateHealth(getHealth());
        body = new Body(x, y,200,200);
        attackBody = new Body(x,y,200,200);
        try {
            Media runningSound = new Media(new File("C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/Boss_chapter2/Sound/skill1.mp3").toURI().toString());
            runningSoundPlayer = new MediaPlayer(runningSound);
            runningSoundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            Media attackSound = new Media(new File("C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/Boss_chapter2/Sound/skill2.mp3").toURI().toString());
            attackSoundPlayer = new MediaPlayer(attackSound);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public Body getBody(){
        return body;
    }
    public Body getAttackBody(){
        return attackBody;
    }

    public void setDefaultValues(){
        x = 800;
        y = 230;
        speed = 3;
        direction = "down";

    }


    private void drawHealthBar(GraphicsContext gc) {
        int healthBarX = 1380 - barWidth - 10;
        int healthBarY = 0;

        // Vẽ viền ngoài
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(healthBarX, healthBarY, barWidth + 4, barHeight + 4, 10, 10);

        // Vẽ nền thanh máu
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(healthBarX + 2, healthBarY + 2, barWidth, barHeight, 8, 8);

        // Vẽ thanh máu
        double healthRatio = (double)  currentHealth / maxHealth;
        gc.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.YELLOW)));
        gc.fillRoundRect(healthBarX + 2, healthBarY + 2, barWidth * healthRatio, barHeight, 8, 8);
    }



    public void getPlayerImage() {
        try {
            // standing
            //right
            std1_r = new Image(new File("res/Boss_chapter2/right/std1_r.png").toURI().toString());
            std2_r = new Image(new File("res/Boss_chapter2/right/std2_r.png").toURI().toString());
            std3_r = new Image(new File("res/Boss_chapter2/right/std3_r.png").toURI().toString());
            std4_r = new Image(new File("res/Boss_chapter2/right/std4_r.png").toURI().toString());
            std5_r = new Image(new File("res/Boss_chapter2/right/std5_r.png").toURI().toString());
            std6_r = new Image(new File("res/Boss_chapter2/right/std6_r.png").toURI().toString());
            std7_r = new Image(new File("res/Boss_chapter2/right/std7_r.png").toURI().toString());
            std8_r = new Image(new File("res/Boss_chapter2/right/std8_r.png").toURI().toString());
            //left
            std1_l = new Image(new File("res/Boss_chapter2/left/std1_l.png").toURI().toString());
            std2_l = new Image(new File("res/Boss_chapter2/left/std2_l.png").toURI().toString());
            std3_l = new Image(new File("res/Boss_chapter2/left/std3_l.png").toURI().toString());
            std4_l = new Image(new File("res/Boss_chapter2/left/std4_l.png").toURI().toString());
            std5_l = new Image(new File("res/Boss_chapter2/left/std5_l.png").toURI().toString());
            std6_l = new Image(new File("res/Boss_chapter2/left/std6_l.png").toURI().toString());
            std7_l = new Image(new File("res/Boss_chapter2/left/std7_l.png").toURI().toString());
            std8_l = new Image(new File("res/Boss_chapter2/left/std8_l.png").toURI().toString());



            // right
            run1_r = new Image(new File("res/Boss_chapter2/right/run1_r.png").toURI().toString());
            run2_r = new Image(new File("res/Boss_chapter2/right/run2_r.png").toURI().toString());
            run3_r = new Image(new File("res/Boss_chapter2/right/run3_r.png").toURI().toString());
            run4_r = new Image(new File("res/Boss_chapter2/right/run4_r.png").toURI().toString());
            run5_r = new Image(new File("res/Boss_chapter2/right/run5_r.png").toURI().toString());
            run6_r = new Image(new File("res/Boss_chapter2/right/run6_r.png").toURI().toString());
            run7_r = new Image(new File("res/Boss_chapter2/right/run7_r.png").toURI().toString());
            run8_r = new Image(new File("res/Boss_chapter2/right/run8_r.png").toURI().toString());

            //left
            run1_l = new Image(new File("res/Boss_chapter2/left/run1_l.png").toURI().toString());
            run2_l = new Image(new File("res/Boss_chapter2/left/run2_l.png").toURI().toString());
            run3_l = new Image(new File("res/Boss_chapter2/left/run3_l.png").toURI().toString());
            run4_l = new Image(new File("res/Boss_chapter2/left/run4_l.png").toURI().toString());
            run5_l = new Image(new File("res/Boss_chapter2/left/run5_l.png").toURI().toString());
            run6_l = new Image(new File("res/Boss_chapter2/left/run6_l.png").toURI().toString());
            run7_l = new Image(new File("res/Boss_chapter2/left/run7_l.png").toURI().toString());
            run8_l = new Image(new File("res/Boss_chapter2/left/run8_l.png").toURI().toString());







            // skill k
            //right
            at1_1r = new Image(new File("res/Boss_chapter2/right/at1_r.png").toURI().toString());
            at1_2r = new Image(new File("res/Boss_chapter2/right/at2_r.png").toURI().toString());
            at1_3r = new Image(new File("res/Boss_chapter2/right/at3_r.png").toURI().toString());
            at1_4r = new Image(new File("res/Boss_chapter2/right/at4_r.png").toURI().toString());
            at1_5r = new Image(new File("res/Boss_chapter2/right/at5_r.png").toURI().toString());
            at1_6r = new Image(new File("res/Boss_chapter2/right/at6_r.png").toURI().toString());
            at1_7r = new Image(new File("res/Boss_chapter2/right/at7_r.png").toURI().toString());
            at1_8r = new Image(new File("res/Boss_chapter2/right/at8_r.png").toURI().toString());
            //left
            at1_1l = new Image(new File("res/Boss_chapter2/left/at1_l.png").toURI().toString());
            at1_2l = new Image(new File("res/Boss_chapter2/left/at2_l.png").toURI().toString());
            at1_3l = new Image(new File("res/Boss_chapter2/left/at3_l.png").toURI().toString());
            at1_4l = new Image(new File("res/Boss_chapter2/left/at4_l.png").toURI().toString());
            at1_5l = new Image(new File("res/Boss_chapter2/left/at5_l.png").toURI().toString());
            at1_6l = new Image(new File("res/Boss_chapter2/left/at6_l.png").toURI().toString());
            at1_7l = new Image(new File("res/Boss_chapter2/left/at7_l.png").toURI().toString());
            at1_8l = new Image(new File("res/Boss_chapter2/left/at8_l.png").toURI().toString());


            //skill2//
            att2_1r = new Image(new File("res/Boss_chapter2/right/att2_1r.png").toURI().toString());
            att2_2r = new Image(new File("res/Boss_chapter2/right/att2_2r.png").toURI().toString());
            att2_3r = new Image(new File("res/Boss_chapter2/right/att2_3r.png").toURI().toString());
            att2_4r = new Image(new File("res/Boss_chapter2/right/att2_4r.png").toURI().toString());
            att2_5r = new Image(new File("res/Boss_chapter2/right/att2_5r.png").toURI().toString());
            att2_6r = new Image(new File("res/Boss_chapter2/right/att2_6r.png").toURI().toString());
            att2_7r = new Image(new File("res/Boss_chapter2/right/att2_7r.png").toURI().toString());
            att2_8r = new Image(new File("res/Boss_chapter2/right/att2_8r.png").toURI().toString());
            //left
            att2_1l = new Image(new File("res/Boss_chapter2/left/att2_1l.png").toURI().toString());
            att2_2l = new Image(new File("res/Boss_chapter2/left/att2_2l.png").toURI().toString());
            att2_3l = new Image(new File("res/Boss_chapter2/left/att2_3l.png").toURI().toString());
            att2_4l = new Image(new File("res/Boss_chapter2/left/att2_4l.png").toURI().toString());
            att2_5l = new Image(new File("res/Boss_chapter2/left/att2_5l.png").toURI().toString());
            att2_6l = new Image(new File("res/Boss_chapter2/left/att2_6l.png").toURI().toString());
            att2_7l = new Image(new File("res/Boss_chapter2/left/att2_7l.png").toURI().toString());
            att2_8l = new Image(new File("res/Boss_chapter2/left/att2_8l.png").toURI().toString());
            //Taken hit

            hit1_r = new Image(new File("res/Boss_chapter2/right/hit1_r.png").toURI().toString());
            hit2_r = new Image(new File("res/Boss_chapter2/right/hit2_r.png").toURI().toString());
            hit3_r = new Image(new File("res/Boss_chapter2/right/hit3_r.png").toURI().toString());

            hit1_l = new Image(new File("res/Boss_chapter2/left/hit1_l.png").toURI().toString());
            hit2_l = new Image(new File("res/Boss_chapter2/left/hit2_l.png").toURI().toString());
            hit3_l = new Image(new File("res/Boss_chapter2/left/hit3_l.png").toURI().toString());




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHealth() {
        if(player1_2 != null && player1_2.isAttacking() && player1_2.getAttackBody().intersects(body)){
            currentHealth -= 0.0001;
        }
        return currentHealth;
    }

    public void updateHealth(int damage) {
        getHealth();
    }








    public void update() {
        // Kiểm tra xem phím "J" có được nhấn không
        if (keyH.Pressed1) {
            isUsingSkill = true; // Bắt đầu sử dụng skill
            if(direction == "left") {
                attackBody.setX(x+50);
            } else {attackBody.setX(x + 220);};
            attackBody.setY(y+300);
            skillCounter = 0; // Reset bộ đếm thời gian cho skill
            playAttackSound2();
        }
        if (keyH.Pressed2) {
            isUsingSkillK = true; // Bắt đầu sử dụng skill qua phím K
            attackBody.setX(direction.equals("left") ? x+50 : x + 220);
            attackBody.setY(y+300);
            skillKCounter = 0; // Reset bộ đếm thời gian cho skill K
            playAttackingSound1();
        }



        // Chỉ cho phép di chuyển nếu không sử dụng skill
        if (!isUsingSkill) {
            // Giả định ban đầu là nhân vật không di chuyển
            isMoving = false;


            if (keyH.leftPressed1) {
                direction = "left";
                x -= speed;
                isMoving = true;
            }
            else if (keyH.rightPressed1) {
                direction = "right";
                x += speed;
                isMoving = true;
            }
            if (direction == "right") {
                body.setX(x);
                body.setY(y+300);
            } else {
                body.setX(x + 268.6);
                body.setY(y+300);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        Image image = null;
        drawHealthBar(gc);  // Giả sử bạn đã có phương thức vẽ thanh máu
        gc.save();
        // Tăng animationCounter mỗi lần vẽ
        animationCounter++;
        if (animationCounter > animationSpeed) {
            animationCounter = 0;

            // Xử lý khi đang sử dụng skill qua phím J
            if (isUsingSkill) {
                animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 6 khung hình skill J
                skillCounter++;

                if (skillCounter >= 8   ) {
                    isUsingSkill = false; // Dừng sử dụng skill J
                    stopAttackSound2();
                }
            }
            // Xử lý khi đang sử dụng skill qua phím K
            else if (isUsingSkillK) {
                animationFrame = (animationFrame + 1) % 7; // Chuyển đổi giữa 8 khung hình skill K
                skillKCounter++;

                if (skillKCounter >= 7) {
                    isUsingSkillK = false; // Dừng sử dụng skill K
                    stopAttackingSound1();
                }
            }
            else if(player1_2.isAttacking()) {
                animationFrame = (animationFrame + 1) % 3;
                skillCounter++;

            }

            // Xử lý di chuyển hoặc đứng yên
            else if (isMoving) {
                switch (direction) {
                    case "left":
                    case "right":
                        animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 3 khung hình
                        break;
                }
            } else {
                animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 5 khung hình đứng yên
            }
        }

        // Vẽ hình ảnh cho skill J
        if (isUsingSkill) {
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = att2_1r; break;
                    case 1: image = att2_2r; break;
                    case 2: image = att2_3r; break;
                    case 3: image = att2_4r; break;
                    case 4: image = att2_5r; break;
                    case 5: image = att2_6r; break;
                    case 6: image = att2_7r; break;
                    case 7: image = att2_8r; break;


                }
            } else {
                switch (animationFrame) {
                    case 0: image = att2_1l; break;
                    case 1: image = att2_2l; break;
                    case 2: image = att2_3l; break;
                    case 3: image = att2_4l; break;
                    case 4: image = att2_5l; break;
                    case 5: image = att2_6l; break;
                    case 6: image = att2_7l; break;
                    case 7: image = att2_8l; break;
                }
            }
        }
        // Vẽ hình ảnh cho skill K
        else if (isUsingSkillK) {
            controlDamage++;
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = at1_1r; break;
                    case 1: image = at1_2r; break;
                    case 2: image = at1_3r; break;
                    case 3: image = at1_4r; break;
                    case 4: image = at1_5r; x+=2;break;
                    case 5: image = at1_6r; break;
                    case 6: image = at1_7r; break;

                }
            } else {
                switch (animationFrame) {
                    case 0: image = at1_1l; break;
                    case 1: image = at1_2l; break;
                    case 2: image = at1_3l; break;
                    case 3: image = at1_4l; break;
                    case 4: image = at1_5l; x-=2;break;
                    case 5: image = at1_6l; break;
                    case 6: image = at1_7l; break;


                }
            }
        }
        else if (player1_2.isAttacking()) {
             if (direction.equals("right")) {
                 switch (animationFrame) {
                     case 0: image = hit1_r;
                     case 1: image = hit2_r;
                     case 2: image = hit3_r;
                 }
             } else {
                 switch (animationFrame) {
                     case 0: image = hit1_l;
                     case 1: image = hit2_l;
                     case 2: image = hit3_l;
                 }
             }
        }

        // Di chuyển hoặc đứng yên
        else if(!isMoving) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = std1_l; break;
                    case 1: image = std2_l; break;
                    case 2: image = std3_l; break;
                    case 3: image = std4_l; break;
                    case 4: image = std5_l; break;
                    case 5: image = std6_l; break;
                    case 6: image = std7_l; break;
                    case 7: image = std8_l; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = std1_r; break;
                    case 1: image = std2_r; break;
                    case 2: image = std3_r; break;
                    case 3: image = std4_r; break;
                    case 4: image = std5_r; break;
                    case 5: image = std6_r; break;
                    case 6: image = std7_r; break;
                    case 7: image = std8_r; break;
                }
            }
        } else  {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = run1_l; break;
                    case 1: image = run2_l; break;
                    case 2: image = run3_l; break;
                    case 3: image = run4_l; break;
                    case 4: image = run5_l; break;
                    case 5: image = run6_l; break;
                    case 6: image = run7_l; break;
                    case 7: image = run8_l; break;
                }
            }
            // Xử lý di chuyển sang phải
            else if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = run1_r; break;
                    case 1: image = run2_r; break;
                    case 2: image = run3_r; break;
                    case 3: image = run4_r; break;
                    case 4: image = run5_r; break;
                    case 5: image = run6_r; break;
                    case 6: image = run7_r; break;
                    case 7: image = run8_r; break;
                }
            }
        }

        // Vẽ hình ảnh lên Canvas
        gc.drawImage(image,  x, y,image.getWidth() * 3.3, image.getHeight() * 3.3); // x, y là tọa độ vẽ hình ảnh
        if (isUsingSkill) {
            gc.setStroke(Color.BLUE);
            gc.strokeRect(attackBody.getX(), attackBody.getY(), attackBody.getWidth(), attackBody.getHeight());
        }
        if (isUsingSkillK) {
            gc.setStroke(Color.BLUE);
            gc.strokeRect(attackBody.getX(), attackBody.getY(), attackBody.getWidth(), attackBody.getHeight());
        }

        gc.setStroke(Color.RED); // Màu hitbox
        gc.strokeRect(body.getX(), body.getY(), body.getWidth(), body.getHeight());
    }
    public int getX() {
        return x;
    }
    public String getDirection(){
        return direction;
    }


    public boolean distance(){
        int distanceToPlayer = Math.abs(x - player1_2.getX());
        if (distanceToPlayer < 30) return true;
        else return false;
    }
    private void playAttackingSound1() {
        if (runningSoundPlayer != null && runningSoundPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            runningSoundPlayer.play();
        }
    }

    private void stopAttackingSound1() {
        if (runningSoundPlayer != null && runningSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            runningSoundPlayer.stop();
        }
    }

    private void playAttackSound2() {
        if (attackSoundPlayer != null && attackSoundPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            attackSoundPlayer.play();
        }
    }

    private void stopAttackSound2() {
        if (attackSoundPlayer != null && attackSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            attackSoundPlayer.stop();
        }
    }


}
