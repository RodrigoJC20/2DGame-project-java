package com.game.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.game.gamestates.Gamestate;
import com.game.engine.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private final GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {

		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
			case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU -> gamePanel.getGame().getMenu().mouseMoved(e);
			case PLAYING -> gamePanel.getGame().getPlaying().mouseMoved(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (Gamestate.state == Gamestate.PLAYING) {
			gamePanel.getGame().getPlaying().mouseClicked(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
			case PLAYING -> gamePanel.getGame().getPlaying().mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
			case PLAYING -> gamePanel.getGame().getPlaying().mouseReleased(e);
		}
	}
}
