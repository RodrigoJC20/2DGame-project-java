package com.game.gamestates;

import com.game.entities.Player;
import com.game.levels.LevelManager;
import com.game.engine.Game;
import com.game.ui.PauseOverlay;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);

    public Playing(Game game) {
        super(game);

        initClasses();
    }

    private void initClasses() {
        pauseOverlay = new PauseOverlay(this);
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
        } else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);

        if (paused)
            pauseOverlay.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_K -> player.setAttacking(true);
            case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
        }
    }

    public void unpauseGame() {
        paused = false;
    }

    public Player getPlayer() {
        return player;
    }

}
