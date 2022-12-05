package com.game.ui;

import com.game.gamestates.Gamestate;
import com.game.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.UI.MenuButtons.*;

public class MenuButtonPlay implements IMenuButton {
    private final int xPos;
    private final int yPos;
    private int index;
    private final int xOffsetCenter = B_WIDTH_PLAY / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButtonPlay(int xPos, int yPos, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;

        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH_PLAY, B_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_PLAY_WIDTH_DEFAULT, 0, B_PLAY_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH_PLAY, B_HEIGHT, null);
    }

    @Override
    public void update() {
        index = 0;
        if (mouseOver) index = 1;
        if (mousePressed) index = 2;
    }

    @Override
    public boolean isMouseOver() {
        return mouseOver;
    }

    @Override
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    @Override
    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void applyGamestate() {
        Gamestate.state = state;
    }

    @Override
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
