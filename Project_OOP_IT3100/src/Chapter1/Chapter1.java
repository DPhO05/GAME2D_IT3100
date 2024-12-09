package Chapter1;

import MainMenu.ChapterMenu;
import MainMenu.GameLauncher;
import entity.KeyHandlers;

import entity.Player;
import entity.Player1;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

public class Chapter1 extends Pane {
    private GraphicsContext gc;
    private final GameLauncher gameLauncher;
    private MediaPlayer chapter1MusicPlayer;
    private boolean isPlaying;
    private boolean isPaused;
    private ChapterMenu chapterMenu;

    private Stage stage;
    private StackPane root;
    private StackPane overlayPane;
    private int count = 0;
    private javafx.animation.AnimationTimer gameLoop;

    private final int originalTileSize = 20;  // Kích thước gốc của tile
    private final int scale = 3;              // Tỷ lệ scale
    private final int tileSize = originalTileSize * scale;  // Kích thước của tile sau khi scale

    private final int maxScreenCol = 23;
    private final int maxScreenRow = 13;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    public Image backgroundImage;
    private Player player;
    private Player1 player1;
    KeyHandlers keyH = new KeyHandlers();
    KeyHandlers keyH1 = new KeyHandlers();
    private Canvas canvas;



    public Chapter1(GameLauncher gameLauncher, ChapterMenu chapterMenu){
        this.gameLauncher = gameLauncher;
        this.chapterMenu = chapterMenu;
    }



    public void showChapter1(Stage stage) {
        isPlaying = true;
        isPaused = false;
        this.stage = stage;
        String musicPath = "C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/Sound/Chapter1_sound.mp3";
        Media music = new Media(Paths.get(musicPath).toUri().toString());
        chapter1MusicPlayer = new MediaPlayer(music);

        // Bat dau phat nhac cua Chapter 1
        chapter1MusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        chapter1MusicPlayer.setVolume(0.5);
        chapter1MusicPlayer.play();

        // tao man hinh cot truyen
        Label storyLabel1 = new Label("Ngộ Không bắt đầu cuộc hành trình của mình và gặp tên Azure trên Thiên Đình.");
        storyLabel1.setFont(new Font("Arial", 24));
        storyLabel1.setTextAlignment(TextAlignment.CENTER);
        storyLabel1.setStyle("-fx-text-fill: white;");

        Label storyLabel2 = new Label("Sau khi đấu khẩu, cả hai bên đã quyết định lao vào nhau và đánh một trận chiến nảy lửa...");
        storyLabel2.setFont(new Font("Arial", 24));
        storyLabel2.setTextAlignment(TextAlignment.CENTER);
        storyLabel2.setStyle("-fx-text-fill: white;");
        AnchorPane.setLeftAnchor(storyLabel2, 225.0);
        AnchorPane.setTopAnchor(storyLabel2, 410.0);

        AnchorPane story = new AnchorPane(storyLabel2);
        StackPane storyPane = new StackPane(storyLabel1, story);
        storyPane.setStyle("-fx-background-color: black;");

        Scene storyScene = new Scene(storyPane, 1380, 800);

        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(e -> {
            showChapter1Background(stage);
        });
        delay.play();
        // thêm thao tac nhan nut space de co the bo qua
        storyScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                delay.stop();
                showChapter1Background(stage);
            }
        });

        stage.setScene(storyScene);
        stage.setTitle("Chapter 1: Story Intro");
        stage.show();



    }

    public void showChapter1Background(Stage stage) {
        // Tạo giao diện cho Chapter 1
        this.stage = stage;
        if (player == null) {
            player = new Player(this, keyH, player1); // Chỉ khởi tạo nếu chưa có
        }
        if (player1 == null) {
            player1 = new Player1(this, keyH1, player);  // Chỉ khởi tạo Player 2 nếu chưa có
        }
        if (canvas == null){
            canvas = new Canvas(screenWidth, screenHeight);
            getChildren().add(canvas);
        }
        gc = canvas.getGraphicsContext2D();

        //Vẽ background
        String backgroundPath = "C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/Background/background_chap1.jpg";
        backgroundImage = new Image(Paths.get(backgroundPath).toUri().toString());

        // Vẽ background đã scale
        gc.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight);
        // Bạn có thể thêm mã để vẽ player và các đối tượng khác tại đây
        //player.draw(gc);  // Giả sử bạn có phương thức draw trong lớp Player để vẽ người chơi

        // Nut pause
        String pauseButtonPath = "C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/ChapterImage/pause_button.png";
        Image pauseButtonImage = new Image(Paths.get(pauseButtonPath).toUri().toString());
        ImageView pauseButtonView = new ImageView(pauseButtonImage);
        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseButtonView);
        pauseButton.setStyle("-fx-background-color: transparent;");
        pauseButton.setLayoutX(665);
        pauseButton.setLayoutY(10);

        PauseMenu pauseMenu = new PauseMenu(this, chapter1MusicPlayer, gameLauncher, player);
        pauseButton.setOnAction(e ->{
            isPaused = !isPaused;  // Đổi trạng thái Pause
            if (isPaused) {
                if (gameLoop != null) gameLoop.stop(); // Dừng vòng lặp game
                pauseMenu.showPauseMenu(stage); // Hiển thị menu Pause
                if (chapter1MusicPlayer != null) chapter1MusicPlayer.pause(); // Dừng nhạc
                count ++;
            } else {
                resumeGameActions(); // Tiếp tục game
            }
        });

        StackPane fightPane = new StackPane();
        String fightPath = "C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/ChapterImage/fight_screen.png";
        Image fightImage = new Image(Paths.get(fightPath).toUri().toString());
        ImageView fightView = new ImageView(fightImage);
        fightPane.getChildren().add(fightView);
        if (count == 0){
            fightPane.setVisible(true);
            PauseTransition fightTransition = new PauseTransition(Duration.seconds(1.2));
            fightTransition.setOnFinished(e -> {
                fightPane.setVisible(false);
            });
            fightTransition.play();
        } else {
            fightPane.setVisible(false);
        }
        PauseTransition result = new PauseTransition(Duration.seconds(5));

        result.play();
        // Tạo giao diện chính
        AnchorPane pausePane = new AnchorPane(pauseButton);
        root = new StackPane(canvas, fightPane, pausePane);  // Thêm canvas vào root
        createOverlayPane(stage);
        // Tạo scene và gắn vào stage
        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setTitle("Chapter 1");
        stage.show();

        // Bắt đầu các hành động game (gọi thread game loop)
        // Thêm Debug
        scene.setOnKeyPressed(event -> {
//            System.out.println("Key pressed: " + event.getCode());
            keyH.handleKeyPressed(event);   // Xử lý sự kiện cho Player 1
            keyH1.handleKeyPressed(event);  // Xử lý sự kiện cho Player
        });

        scene.setOnKeyReleased(event -> {
//            System.out.println("Key released: " + event.getCode());
            keyH.handleKeyReleased(event);  // Xử lý sự kiện thả phím cho Player 1
            keyH1.handleKeyReleased(event); // Xử lý sự kiện thả phím cho Player 2
        });




        //Player1
//        scene.setOnKeyPressed(event -> keyH1.handleKeyPressed(event));
//        scene.setOnKeyReleased(event -> keyH1.handleKeyReleased(event));




        canvas.setFocusTraversable(true);
        canvas.requestFocus();


        startGameLoop(gc);
    }



    public void resumeGameActions() {
        isPaused = false;
        // Tiếp tục vòng lặp game nếu đã tồn tại
        if (gameLoop != null) {
            gameLoop.start();
        }
        // Tiếp tục nhạc nếu đã bị tạm dừng
        if (chapter1MusicPlayer != null && !chapter1MusicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            chapter1MusicPlayer.play();
        }
        // Đảm bảo canvas lấy lại focus
        if (canvas != null) {
            canvas.requestFocus();
        }
    }


    private void createOverlayPane(Stage stage){
        overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        overlayPane.setVisible(false);

        ImageView resultView = new ImageView();
        resultView.setFitWidth(400);
        resultView.setFitHeight(400);

        String continueButtonPath = "C:/Users/Vu/Downloads/Project_OOP_IT3100/res/ChapterImage/continue_button.png";
        Image continueButtonImage = new Image(Paths.get(continueButtonPath).toUri().toString());
        ImageView continueButtonView = new ImageView(continueButtonImage);
        Button continueButton = new Button();
        continueButton.setGraphic(continueButtonView);
        continueButton.setStyle("-fx-background-color: transparent;");
        continueButton.setOnAction(e -> chapterMenu.showChapterMenu(stage));
        AnchorPane.setTopAnchor(continueButton, 505.0);
        AnchorPane.setLeftAnchor(continueButton, 645.0);

        StackPane.setAlignment(resultView, javafx.geometry.Pos.CENTER);
        //StackPane.setAlignment(continueButton, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(continueButton, new javafx.geometry.Insets(20));

        AnchorPane continuePane = new AnchorPane(continueButton);
        overlayPane.getChildren().addAll(resultView, continuePane);
        root.getChildren().add(overlayPane);
    }

    private void showEndOverlay(String result){
        Platform.runLater(() -> {
            if (chapter1MusicPlayer != null){
                chapter1MusicPlayer.stop();
            }

            // lay hinh anh thang/ thua
            String resultPath = "C:/Users/ADMIN/Downloads/ProjectGame2D/Project_OOP_IT3100/res/ChapterImage/" + result.toLowerCase() + ".png";
            Image resultImage = new Image(Paths.get(resultPath).toUri().toString());
            ImageView resultView = (ImageView) overlayPane.getChildren().get(0);
            resultView.setImage(resultImage);
            overlayPane.setVisible(true);
        });
    }
    private void startGameLoop(GraphicsContext gc) {
        if (gameLoop != null) {
            gameLoop.start();
            return;
        }
        // Tạo vòng lặp mới nếu chưa tồn tại
        gameLoop = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPaused) {
                    return; // Dừng vẽ khi game bị tạm dừng
                }

                // Cập nhật trạng thái game
                player.update();
                player1.update();



                if (player.getHealth() == 0) {
                    stop(); // Dừng vòng lặp game
                    showEndOverlay("defeat_screen"); // Hiển thị màn hình thua
                    return;
                }
                if (0 == player1.getHealth()) {
                    stop(); // Dừng vòng lặp game
                    showEndOverlay("victory_screen"); // Hiển thị màn hình thắng
                    return;
                }

                // Xóa màn hình và vẽ lại background
                gc.clearRect(0, 0, screenWidth, screenHeight);
                gc.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight);
                // Vẽ lại nhân vật
                player.draw(gc);
                player1.draw(gc);
            }
        };


        // Bắt đầu vòng lặp game
        gameLoop.start();
    }
}