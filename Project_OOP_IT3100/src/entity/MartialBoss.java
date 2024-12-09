package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Random;

import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Collections;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import javafx.scene.image.Image;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import Chapter1.Chapter1;

import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class MartialBoss extends Entity{
    private static final int ATTACK_DISTANCE = 40;  // Khoảng cách tấn công (20 pixels)

    private boolean isStanding = false;  // Kiểm tra trạng thái đứng yên
    private int count =0;
    private int animationFrame = 0;  // Khung hình hiện tại
    private int animationCounter = 0; // Bộ đếm cho các khung hình
    private int animationSpeed = 30;  // Tốc độ chuyển đổi khung hình
    private long skillStartTime = 0;
    //private int speed = 6;

    private long lastFrameTime = 0; // Lưu thời gian của lần cập nhật khung hình cuối

    private boolean isMoving = false; // Trạng thái di chuyển
    private boolean isUsingSkill1 = false; // Trạng thái sử dụng skill 1
    private boolean isUsingSkill2 = false; // Trạng thái sử dụng skill 2
    private boolean isTakenHit = false;
    private boolean isDied = false;
    private String direction = "right";  // Hướng di chuyển của boss (right/left)
    private String currentAction = "standing";

    private long lastSkillTime = 0;
    private static final long SKILL_COOLDOWN = 5000; // 3 giây giữa mỗi lần ra chiêu
    private static final long STAND_DURATION = 3000;


    private int barWidth = 400;  // Chiều rộng của thanh máu
    private int barHeight = 20;

    private int currentHealth = 100;  // Máu hiện tại
    private int maxHealth = 100;      // Máu tối đa

    private Player player;
    Chapter1 gp;

    public void setDefaultValues() {
        x = 500;
        y = 500;

        speed = 4;
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
        double healthRatio = (double) currentHealth / maxHealth;
        gc.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.YELLOW)));
        gc.fillRoundRect(healthBarX + 2, healthBarY + 2, barWidth * healthRatio, barHeight, 8, 8);
    }

    public void updateHealth(int newHealth) {
        currentHealth = newHealth;
        // Gọi lại hàm vẽ thanh máu
    }
    public void setY0(){
        this.y = 450;
    }
    public void setY1(){
        this.y = 500;
    }

    public void useSkill1() {
        // Bắt đầu chiêu 1
        isUsingSkill1 = true;
        skillStartTime = System.currentTimeMillis(); // Ghi lại thời gian bắt đầu chiêu 1


        // Đợi 1 giây (hoặc thời gian của hoạt ảnh) rồi chuyển sang chiêu 2
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isUsingSkill1 =false;
            }
        }, 5000); // 1000 ms = 1 giây
    }

    public void useSkill2() {
        // Bắt đầu chiêu 2
        isUsingSkill2 = true;
        skillStartTime = System.currentTimeMillis(); // Ghi lại thời gian bắt đầu chiêu 2


        // Đợi 1 giây (hoặc thời gian của hoạt ảnh) rồi quay lại chiêu 1
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isUsingSkill2 = false;
            }
        }, 5000); // 1000 ms = 1 giây
    }

    public void randomSkillCloseRange() {
        ArrayList<Integer> skills = new ArrayList<>();
        skills.add(1); // Chiêu 1
        skills.add(2); // Chiêu 2

        // Shuffle the skills list using Fisher-Yates
        Collections.shuffle(skills);

        // Chọn chiêu đầu tiên trong danh sách
        int skill = skills.get(0);
        if (skill == 1) {
            useSkill1();
        } else if (skill == 2) {
            useSkill2();
        }
    }


    public void update() {
        animationCounter++;

        if (animationCounter >= animationSpeed) {
            animationCounter = 0;

            if (isMoving) {
                if (count == 1){
                    y += 50;
                    count --;
                }
                animationFrame = (animationFrame + 1) % 6;
            } else if (isUsingSkill1) {
                if (animationFrame == 0) {
                    count ++;
                    y -= 50;
                }
                animationFrame = (animationFrame + 1) % 6; // 6 khung hình cho skill 1
            } else if (isUsingSkill2) {
                if (animationFrame == 0) {
                    count ++;
                    y -= 50;
                }
                animationFrame = (animationFrame + 1) % 6; // 6 khung hình cho skill 2
            } else if (isTakenHit) {
                if (count == 1){
                    y += 50;
                    count --;
                }
                animationFrame = (animationFrame + 1) % 4; // 4 khung hình cho dính chiêu
            } else if (isDied) {
                animationFrame = (animationFrame + 1) % 6; // 6 khung hình cho chết
            } else if (isStanding) {
                if (count == 1){
                    y+= 50;
                    count --;
                }
                animationFrame = (animationFrame + 1) % 8; // 8 khung hình cho đứng yên
            }
            // Tính khoảng cách tới người chơi
            int distanceToPlayer = Math.abs(x - player.getX());
            long currentTime = System.currentTimeMillis();

            if (distanceToPlayer > 50) {
                moveTowardsPlayer();
            } else {
                // Gần người chơi: trộn chiêu 1 và chiêu 2
                isMoving = false;
                randomSkillCloseRange();
            }
        }

        // Di chuyển Boss nếu cần
        if (isMoving) {
            if (direction.equals("left")) {
                x -= speed/3; // Di chuyển sang trái
            } else if (direction.equals("right")) {
                x += speed/3; // Di chuyển sang phải
            }
        }
//        if(isUsingSkill1){
//            if(animationFrame == 0){
//                y+=50;
//            }
//            else if(animationFrame == 6){
//                y-=50;
//            }
//        }
//        if(isUsingSkill2){
//            if(animationFrame ==0){
//                y+=50;
//            } else if (animationFrame == 6) {
//                y-=50;
//
//            }
//        }
    }

    public void moveTowardsPlayer() {
        isMoving = true;

        // Cập nhật hướng di chuyển
        if (player.getX() < x) {
            direction = "left";
        } else {
            direction = "right";
        }

        // Dừng di chuyển nếu đã đủ gần
        int distanceToPlayer = Math.abs(x - player.getX());
        if (distanceToPlayer <= 50) {
            isMoving = false;
        }
    }

    public MartialBoss(Player player, Chapter1 gp) {
        this.player = player;
        this.gp = gp;
        setDefaultValues();
        getBossImage();
    }


    public void getBossImage() {
        try {
            // IDLE Right
            R_IDLE_1 = new Image(new File("res/MartialBoss/Right/IDLE/1.png").toURI().toString());
            R_IDLE_2 = new Image(new File("res/MartialBoss/Right/IDLE/2.png").toURI().toString());
            R_IDLE_3 = new Image(new File("res/MartialBoss/Right/IDLE/3.png").toURI().toString());
            R_IDLE_4 = new Image(new File("res/MartialBoss/Right/IDLE/4.png").toURI().toString());
            R_IDLE_5 = new Image(new File("res/MartialBoss/Right/IDLE/5.png").toURI().toString());
            R_IDLE_6 = new Image(new File("res/MartialBoss/Right/IDLE/6.png").toURI().toString());
            R_IDLE_7 = new Image(new File("res/MartialBoss/Right/IDLE/7.png").toURI().toString());
            R_IDLE_8 = new Image(new File("res/MartialBoss/Right/IDLE/8.png").toURI().toString());

            // IDLE Left
            L_IDLE_1 = new Image(new File("res/MartialBoss/Left/IDLE/1.png").toURI().toString());
            L_IDLE_2 = new Image(new File("res/MartialBoss/Left/IDLE/2.png").toURI().toString());
            L_IDLE_3 = new Image(new File("res/MartialBoss/Left/IDLE/3.png").toURI().toString());
            L_IDLE_4 = new Image(new File("res/MartialBoss/Left/IDLE/4.png").toURI().toString());
            L_IDLE_5 = new Image(new File("res/MartialBoss/Left/IDLE/5.png").toURI().toString());
            L_IDLE_6 = new Image(new File("res/MartialBoss/Left/IDLE/6.png").toURI().toString());
            L_IDLE_7 = new Image(new File("res/MartialBoss/Left/IDLE/7.png").toURI().toString());
            L_IDLE_8 = new Image(new File("res/MartialBoss/Left/IDLE/8.png").toURI().toString());

            // Run Right
            R_Run_1 = new Image(new File("res/MartialBoss/Right/Run/1.png").toURI().toString());
            R_Run_2 = new Image(new File("res/MartialBoss/Right/Run/2.png").toURI().toString());
            R_Run_3 = new Image(new File("res/MartialBoss/Right/Run/3.png").toURI().toString());
            R_Run_4 = new Image(new File("res/MartialBoss/Right/Run/4.png").toURI().toString());
            R_Run_5 = new Image(new File("res/MartialBoss/Right/Run/5.png").toURI().toString());
            R_Run_6 = new Image(new File("res/MartialBoss/Right/Run/6.png").toURI().toString());
            R_Run_7 = new Image(new File("res/MartialBoss/Right/Run/7.png").toURI().toString());
            R_Run_8 = new Image(new File("res/MartialBoss/Right/Run/8.png").toURI().toString());

            // Run Left
            L_Run_1 = new Image(new File("res/MartialBoss/Left/Run/1.png").toURI().toString());
            L_Run_2 = new Image(new File("res/MartialBoss/Left/Run/2.png").toURI().toString());
            L_Run_3 = new Image(new File("res/MartialBoss/Left/Run/3.png").toURI().toString());
            L_Run_4 = new Image(new File("res/MartialBoss/Left/Run/4.png").toURI().toString());
            L_Run_5 = new Image(new File("res/MartialBoss/Left/Run/5.png").toURI().toString());
            L_Run_6 = new Image(new File("res/MartialBoss/Left/Run/6.png").toURI().toString());
            L_Run_7 = new Image(new File("res/MartialBoss/Left/Run/7.png").toURI().toString());
            L_Run_8 = new Image(new File("res/MartialBoss/Left/Run/8.png").toURI().toString());

            // TakenHit Right
            R_Take_Hit_1 = new Image(new File("res/MartialBoss/Right/TakeHit/1.png").toURI().toString());
            R_Take_Hit_2 = new Image(new File("res/MartialBoss/Right/TakeHit/2.png").toURI().toString());
            R_Take_Hit_3 = new Image(new File("res/MartialBoss/Right/TakeHit/3.png").toURI().toString());
            R_Take_Hit_4 = new Image(new File("res/MartialBoss/Right/TakeHit/4.png").toURI().toString());

            // TakenHit Left
            L_Take_Hit_1 = new Image(new File("res/MartialBoss/Left/TakeHit/1.png").toURI().toString());
            L_Take_Hit_2 = new Image(new File("res/MartialBoss/Left/TakeHit/2.png").toURI().toString());
            L_Take_Hit_3 = new Image(new File("res/MartialBoss/Left/TakeHit/3.png").toURI().toString());
            L_Take_Hit_4 = new Image(new File("res/MartialBoss/Left/TakeHit/4.png").toURI().toString());

            // Attack1 Right
            R_Attack1_1 = new Image(new File("res/MartialBoss/Right/Attack1/1.png").toURI().toString());
            R_Attack1_2 = new Image(new File("res/MartialBoss/Right/Attack1/2.png").toURI().toString());
            R_Attack1_3 = new Image(new File("res/MartialBoss/Right/Attack1/3.png").toURI().toString());
            R_Attack1_4 = new Image(new File("res/MartialBoss/Right/Attack1/4.png").toURI().toString());
            R_Attack1_5 = new Image(new File("res/MartialBoss/Right/Attack1/5.png").toURI().toString());
            R_Attack1_6 = new Image(new File("res/MartialBoss/Right/Attack1/6.png").toURI().toString());

            // Attack1 Left
            L_Attack1_1 = new Image(new File("res/MartialBoss/Left/Attack1/1.png").toURI().toString());
            L_Attack1_2 = new Image(new File("res/MartialBoss/Left/Attack1/2.png").toURI().toString());
            L_Attack1_3 = new Image(new File("res/MartialBoss/Left/Attack1/3.png").toURI().toString());
            L_Attack1_4 = new Image(new File("res/MartialBoss/Left/Attack1/4.png").toURI().toString());
            L_Attack1_5 = new Image(new File("res/MartialBoss/Left/Attack1/5.png").toURI().toString());
            L_Attack1_6 = new Image(new File("res/MartialBoss/Left/Attack1/6.png").toURI().toString());

            // Attack2 Right
            R_Attack2_1 = new Image(new File("res/MartialBoss/Right/Attack2/1.png").toURI().toString());
            R_Attack2_2 = new Image(new File("res/MartialBoss/Right/Attack2/2.png").toURI().toString());
            R_Attack2_3 = new Image(new File("res/MartialBoss/Right/Attack2/3.png").toURI().toString());
            R_Attack2_4 = new Image(new File("res/MartialBoss/Right/Attack2/4.png").toURI().toString());
            R_Attack2_5 = new Image(new File("res/MartialBoss/Right/Attack2/5.png").toURI().toString());
            R_Attack2_6 = new Image(new File("res/MartialBoss/Right/Attack2/6.png").toURI().toString());

            // Attack2 Left
            L_Attack2_1 = new Image(new File("res/MartialBoss/Left/Attack2/1.png").toURI().toString());
            L_Attack2_2 = new Image(new File("res/MartialBoss/Left/Attack2/2.png").toURI().toString());
            L_Attack2_3 = new Image(new File("res/MartialBoss/Left/Attack2/3.png").toURI().toString());
            L_Attack2_4 = new Image(new File("res/MartialBoss/Left/Attack2/4.png").toURI().toString());
            L_Attack2_5 = new Image(new File("res/MartialBoss/Left/Attack2/5.png").toURI().toString());
            L_Attack2_6 = new Image(new File("res/MartialBoss/Left/Attack2/6.png").toURI().toString());

            // Death Right
            R_Death_1 = new Image(new File("res/MartialBoss/Right/Death/1.png").toURI().toString());
            R_Death_2 = new Image(new File("res/MartialBoss/Right/Death/2.png").toURI().toString());
            R_Death_3 = new Image(new File("res/MartialBoss/Right/Death/3.png").toURI().toString());
            R_Death_4 = new Image(new File("res/MartialBoss/Right/Death/4.png").toURI().toString());
            R_Death_5 = new Image(new File("res/MartialBoss/Right/Death/5.png").toURI().toString());
            R_Death_6 = new Image(new File("res/MartialBoss/Right/Death/6.png").toURI().toString());

            // Death Left
            L_Death_1 = new Image(new File("res/MartialBoss/Left/Death/1.png").toURI().toString());
            L_Death_2 = new Image(new File("res/MartialBoss/Left/Death/2.png").toURI().toString());
            L_Death_3 = new Image(new File("res/MartialBoss/Left/Death/3.png").toURI().toString());
            L_Death_4 = new Image(new File("res/MartialBoss/Left/Death/4.png").toURI().toString());
            L_Death_5 = new Image(new File("res/MartialBoss/Left/Death/5.png").toURI().toString());
            L_Death_6 = new Image(new File("res/MartialBoss/Left/Death/6.png").toURI().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {

        Image image = null;
        drawHealthBar(gc);

        gc.scale(3.5, 3.5);
        if (isMoving) {
            // Di chuyển - điều chỉnh frame tương ứng
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Run_1; break;
                    case 1: image = R_Run_2; break;
                    case 2: image = R_Run_3; break;
                    case 3: image = R_Run_4; break;
                    case 4: image = R_Run_5; break;
                    case 5: image = R_Run_6; break;
                    case 6: image = R_Run_7; break;
                    case 7: image = R_Run_8; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Run_1; break;
                    case 1: image = L_Run_2; break;
                    case 2: image = L_Run_3; break;
                    case 3: image = L_Run_4; break;
                    case 4: image = L_Run_5; break;
                    case 5: image = L_Run_6; break;
                    case 6: image = L_Run_7; break;
                    case 7: image = L_Run_8; break;
                }
            }
        }
        else if (isUsingSkill1) {
            setY0();
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Attack1_1; break;
                    case 1: image = R_Attack1_2; break;
                    case 2: image = R_Attack1_3; break;
                    case 3: image = R_Attack1_4; break;
                    case 4: image = R_Attack1_5; break;
                    case 5: image = R_Attack1_6; setY1();break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Attack1_1; break;
                    case 1: image = L_Attack1_2; break;
                    case 2: image = L_Attack1_3; break;
                    case 3: image = L_Attack1_4; break;
                    case 4: image = L_Attack1_5; break;
                    case 5: image = L_Attack1_6; setY1();break;

                }
            }
            setY1();
        }
        else if (isUsingSkill2) {
            // Kỹ năng 2
            setY0();
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Attack2_1; break;
                    case 1: image = R_Attack2_2; break;
                    case 2: image = R_Attack2_3; break;
                    case 3: image = R_Attack2_4; break;
                    case 4: image = R_Attack2_5; break;
                    case 5: image = R_Attack2_6; setY1();break;

                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Attack2_1; break;
                    case 1: image = L_Attack2_2; break;
                    case 2: image = L_Attack2_3; break;
                    case 3: image = L_Attack2_4; break;
                    case 4: image = L_Attack2_5; break;
                    case 5: image = L_Attack2_6; setY1();break;

                }
            }
            setY1();
        }

        else if (isStanding) {
            // Đứng yên
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_IDLE_1; break;
                    case 1: image = R_IDLE_2; break;
                    case 2: image = R_IDLE_3; break;
                    case 3: image = R_IDLE_4; break;
                    case 4: image = R_IDLE_5; break;
                    case 5: image = R_IDLE_6; break;
                    case 6: image = R_IDLE_7; break;
                    case 7: image = R_IDLE_8; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_IDLE_1; break;
                    case 1: image = L_IDLE_2; break;
                    case 2: image = L_IDLE_3; break;
                    case 3: image = L_IDLE_4; break;
                    case 4: image = L_IDLE_5; break;
                    case 5: image = L_IDLE_6; break;
                    case 6: image = L_IDLE_7; break;
                    case 7: image = L_IDLE_8; break;
                }
            }
        }
        else {
            if (direction.equals("right")) {
                image = R_IDLE_1;
            } else if (direction.equals("left")) {
                image = L_IDLE_1;
            }
        }
        gc.drawImage(image,  x/3.5, y/3.5); // x, y là tọa độ vẽ hình ảnh

        gc.restore();
    }


}
