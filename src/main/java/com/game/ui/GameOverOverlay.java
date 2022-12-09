package com.game.ui;

import com.game.engine.Game;
import com.game.gamestates.Gamestate;
import com.game.gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameOverOverlay extends ScreenOverlay {

    public GameOverOverlay(Playing playing) {
        super(playing);
    }

    @Override
    public void draw(Graphics g) {
        // Test game over screen
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);

        g.drawString("Game Over", Game.GAME_WIDTH/2, 150);
        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH/2, 300);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
