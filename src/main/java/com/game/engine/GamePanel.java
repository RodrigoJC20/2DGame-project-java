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

	public GamePanel(Game game) {
		MouseInputs mouseInputs = new MouseInputs(this);

		this.game = game;

		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

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