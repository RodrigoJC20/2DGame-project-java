package com.game.ui;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface IMenuButton {
    void draw(Graphics g);

    void update();

    void applyGamestate();

    void resetBools();

    Rectangle getBounds();

    boolean isMousePressed();

    boolean isMouseOver();

    void setMouseOver(boolean mouseOver);

    void setMousePressed(boolean mousePressed);
}
