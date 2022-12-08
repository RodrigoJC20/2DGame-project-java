package com.game.engine;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JPanel;
import com.game.inputs.KeyboardInputs;
import com.game.inputs.MouseInputs;

import static com.game.engine.Game.GAME_HEIGHT;
import static com.game.engine.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private final Game game;
	private final MouseInputs mouseInputs;
	private final KeyboardInputs keyboardInputs;

	public GamePanel(Game game) {
		this.game = game;

		mouseInputs = new MouseInputs(this);
		keyboardInputs = new KeyboardInputs(this);

		setPanelSize();
		addKeyListener(keyboardInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		game.render(g);

		Toolkit.getDefaultToolkit().sync();
	}

	public Game getGame() {
		return game;
	}

}