package com.game.ui;

import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.UI.VolumeButton.*;

public class VolumeButton extends PauseButton {
    private BufferedImage[] buttonImgs;
    private BufferedImage sliderImg;
    private int index = 0, buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width/2, y, VOLUME_WIDTH, height);

        initXValues(x, width);
        loadImgs();
    }

    public void initXValues(int x, int width) {
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width/2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH/2;
        maxX = x + width - VOLUME_WIDTH/2;
    }

    public void loadImgs() {
        BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        buttonImgs = new BufferedImage[3];
        for(int i=0; i<buttonImgs.length; i++) {
            buttonImgs[i] = tmp.getSubimage(i*VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }

        sliderImg = tmp.getSubimage(3*VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sliderImg, x, y, width, height, null);
        g.drawImage(buttonImgs[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH, VOLUME_HEIGHT, null);
    }

    public void changeX(int x) {
        if (x < minX)
            buttonX = minX;
        else buttonX = Math.min(x, maxX);

        bounds.x = buttonX - VOLUME_WIDTH/2;
    }

    @Override
    public void update() {
        index = 0;

        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }
}
