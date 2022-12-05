package com.game.ui;

import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.UI.URMButtons.*;

public class UrmButton extends PauseButton {
    private BufferedImage[] imgs;
    private final int rowIndex;
    private int index;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);

        this.rowIndex = rowIndex;

        loadImgs();
    }

    private void loadImgs() {
        BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];

        for(int i=0; i< imgs.length; i++) {
            imgs[i] = tmp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex*URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
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
