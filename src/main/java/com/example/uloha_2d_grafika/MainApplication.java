package com.example.uloha_2d_grafika;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class MainApplication extends Application {
    public static final int SCREEN_WIDTH = 1900;
    public static final int SCREEN_HEIGHT = 920;

    public static final int IMAGE_WIDTH = 500;
    public static final int IMAGE_HEIGHT = 500;

    GraphicsContext graphicsContext;
    Ballon ballon;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Klasifikovaná úloha");
        stage.show();

        ballon = new Ballon(SCREEN_WIDTH/2 - IMAGE_WIDTH/2, SCREEN_HEIGHT/2 - IMAGE_HEIGHT/2, IMAGE_WIDTH, IMAGE_HEIGHT);

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouse -> {

            double mouseX = mouse.getX(), mouseY = mouse.getY();

            if (!((mouseX >= ballon.x && mouseX <= (ballon.x + ballon.getWidth())) && (mouseY >= ballon.y && mouseY <= (ballon.y + ballon.getHeight())))) return;

            ballon.youShouldKillYourselfNOW();

        });

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastTick = 0;

            long speed = 20;

            @Override
            public void handle(long now) {
                if (now - lastTick > 1000000000l/speed) {
                    lastTick = now;
                    tick();
                }
            }
        };

        animationTimer.start();
    }

    private void tick() {

        clearScreen();

        graphicsContext.drawImage(ballon.getCurrentImage(), ballon.x, ballon.y, ballon.getWidth(), ballon.getHeight());

        ballon.change();
        ballon.wander();

    }

    public void clearScreen() { graphicsContext.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT); }

    public static void main(String[] args) {
        launch();
    }
}