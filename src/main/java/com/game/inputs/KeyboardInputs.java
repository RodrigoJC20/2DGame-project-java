package com.game.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.gamestates.Gamestate;
import com.game.engine.GamePanel;

public class KeyboardInputs implements KeyListener {

	private final GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
			case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
			case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
			case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
			case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
