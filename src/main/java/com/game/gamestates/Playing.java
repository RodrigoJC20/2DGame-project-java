package com.game.gamestates;

import com.game.entities.EnemyManager;
import com.game.entities.Player;
import com.game.levels.LevelManager;
import com.game.engine.Game;
import com.game.ui.GameOverOverlay;
import com.game.ui.PauseOverlay;
import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static com.game.utilz.Constants.Environment.*;

public class Playing extends State implements StateMethods {
    // COMPONENTS
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;

    // SCREEN_OVERLAYS BOOLEANS
    private boolean paused = false;
    private boolean gameOver = false;

    // ENVIRONMENT
    private BufferedImage backgroundImg, smallCloud;
    private int[] smallCloudPos;
    private final Random rnd = new Random();

    // CAMERA MOVEMENT
    private int xLvlOffset;
    private final int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private final int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private final int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    // Degug mode
    public static boolean debugMode = false;

    public Playing(Game game) {
        super(game);

        initClasses();
        loadImgs();
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudPos = new int[8];
        for(int i=0; i<smallCloudPos.length; i++) {
            smallCloudPos[i] = (int) (50 * Game.SCALE) + rnd.nextInt((int) (70 * Game.SCALE));
        }

    }

    private void initClasses() {
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelManager = new LevelManager(game);

        player = new Player(200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());

        enemyManager = new EnemyManager(this, levelManager.getCurrentLevel().getLvlData(), player);
    }

    @Override
    public void update() {
        if (gameOver) {
            return;
        }

        if (!paused) {
            levelManager.update();
            player.update();
            enemyManager.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
        int rightBorder = (int) (0.8 * Game.GAME_WIDTH);

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset  < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {

        drawBackground(g);
        levelManager.draw(g, xLvlOffset);
        player.draw(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if (paused) {
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        for(int i=0; i<smallCloudPos.length; i++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
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
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_K -> player.setAttacking(true);
            case KeyEvent.VK_ESCAPE -> paused = !paused;
            case KeyEvent.VK_F2 -> debugMode = !debugMode;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
        }
    }

    public void resetAll() {
        // Reset playing, enemy, level, etc.
        gameOver = false;
        paused = false;
        player.reset();
        enemyManager.resetAllEnemies();
    }

    public void checkEnemyHit() {
        enemyManager.checkEnemyHit();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void unpauseGame() {
        paused = false;
    }

    public Player getPlayer() {
        return player;
    }
}
