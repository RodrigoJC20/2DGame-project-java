package com.game.gamestates;

import com.game.engine.Game;
import com.game.ui.IMenuButton;
import com.game.ui.MenuButtonBasic;
import com.game.ui.MenuButtonPlay;
import com.game.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {
    private IMenuButton[] buttons;
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (70 * Game.SCALE);
    }

    private void loadButtons() {
        buttons = new IMenuButton[4];

        buttons[0] = new MenuButtonPlay(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT/2 + Game.GAME_HEIGHT/8, Gamestate.PLAYING);
        buttons[1] = new MenuButtonBasic((int) (Game.GAME_WIDTH / 2 - 30 * Game.SCALE), (int) (Game.GAME_HEIGHT/2 + Game.GAME_HEIGHT/8 + 32 * Game.SCALE), 1, Gamestate.SETTINGS);
        buttons[2] = new MenuButtonBasic(Game.GAME_WIDTH / 2, (int) (Game.GAME_HEIGHT/2 + Game.GAME_HEIGHT/8 + 32 * Game.SCALE), 2, Gamestate.SCOREBOARD);
        buttons[3] = new MenuButtonBasic((int) (Game.GAME_WIDTH / 2 + 30 * Game.SCALE), (int) (Game.GAME_HEIGHT/2 + Game.GAME_HEIGHT/8 + 32 * Game.SCALE), 3, Gamestate.QUIT);
    }


    @Override
    public void update() {
        for (IMenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (IMenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (IMenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (IMenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if(mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (IMenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (IMenuButton mb : buttons)
            mb.setMouseOver(false);

        for (IMenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) Gamestate.state = Gamestate.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
