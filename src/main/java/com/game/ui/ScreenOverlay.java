package com.game.ui;

import com.game.gamestates.Playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class ScreenOverlay {
    protected Playing playing;

    ScreenOverlay(Playing playing) {
        this.playing = playing;
    }

    public abstract void draw(Graphics g);

    public abstract void keyPressed(KeyEvent e);

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);
}
