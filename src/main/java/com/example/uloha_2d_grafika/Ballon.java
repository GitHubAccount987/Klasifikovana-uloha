package com.example.uloha_2d_grafika;

import javafx.scene.image.Image;

import static com.example.uloha_2d_grafika.MainApplication.SCREEN_HEIGHT;
import static com.example.uloha_2d_grafika.MainApplication.SCREEN_WIDTH;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ballon extends Rectangle {

    private static final List<String> STANDARD_TEXTURES = List.of("pohyb1.png", "pohyb2.png", "pohyb3.png", "pohyb4.png", "pohyb5.png", "pohyb6.png", "pohyb7.png", "pohyb8.png", "pohyb9.png", "pohyb10.png", "pohyb11.png", "pohyb12.png", "pohyb13.png", "pohyb14.png", "pohyb15.png", "pohyb16.png", "pohyb17.png", "pohyb18.png", "pohyb19.png", "pohyb20.png");
    private static final List<String> POP_TEXTURES = List.of("pop2.png", "pop3.png", "pop4.png", "pop5.png", "pop6.png", "pop7.png", "pop8.png", "pop9.png", "pop10.png", "pop11.png", "pop12.png");

    private Random random = new Random();

    int currentImage, offset, bound = STANDARD_TEXTURES.size();
    boolean dying;
    List<Image> textureCache = new ArrayList<>();
    int directionX, directionY;

    public Ballon(int x, int y) {
        super(x, y);
        directionX = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
        directionY = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
        loadTextures();
    }

    public Ballon(int x, int y, int height, int width) {
        super(x, y, height, width);
        directionX = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
        directionY = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
        loadTextures();
    }

    private void loadTextures() {

        STANDARD_TEXTURES.forEach(imgName -> { textureCache.add(new Image(getClass().getResource("/images/" + imgName).toString())); });
        POP_TEXTURES.forEach(imgName -> { textureCache.add(new Image(getClass().getResource("/images/" + imgName).toString())); });

    }

    public void wander() {

        directionX *= (x + width) >= SCREEN_WIDTH || x <= 0 ? -1 : 1;
        directionY *= (y + height) >= SCREEN_HEIGHT || y <= 0 ? -1 : 1;

        if (!dying) add(directionX, directionY);
    }

    public void change() {

        if (dying && currentImage == (bound - 1)) { reset(); return; }

        currentImage = (currentImage + 1)%bound;
    }

    public void youShouldKillYourselfNOW() {
        if (dying) return;
        dying = true;
        currentImage = -1;
        offset = STANDARD_TEXTURES.size();
        bound = POP_TEXTURES.size();
    }

    public Image getCurrentImage() { return textureCache.get(currentImage + offset); }

    public void reset() {
        dying = false;
        offset = 0;
        bound = STANDARD_TEXTURES.size();

        x = random.nextInt(SCREEN_WIDTH - width);
        y = random.nextInt(SCREEN_HEIGHT - height);

        directionX = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
        directionY = (random.nextInt(2) == 1 ? -1 : 1)*random.nextInt(4) + 1;
    }
}
