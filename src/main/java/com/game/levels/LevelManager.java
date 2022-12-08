package com.game.levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.engine.Game;
import com.game.utilz.LoadSave;

import static com.game.utilz.Constants.LevelConstants.LevelOne.*;

public class LevelManager {

	private BufferedImage[] levelSprite;
	private final Level levelOne;

	public LevelManager(Game game) {
		importMapTileset();
		levelOne = new Level(LoadSave.GetLevelData());
	}

	private void importMapTileset() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ONE_TILESET);
		levelSprite = new BufferedImage[TOTAL_TILES];
		for (int i = 0; i < LEVEL_ROWS; i++)
			for (int j = 0; j < LEVEL_COLS; j++) {
				int index = i * LEVEL_COLS + j;
				levelSprite[index] = img.getSubimage(j * Game.TILES_DEFAULT_SIZE,
														i * Game.TILES_DEFAULT_SIZE,
														Game.TILES_DEFAULT_SIZE,
														Game.TILES_DEFAULT_SIZE);
			}
	}

	public void draw(Graphics g, int lvlOffset) {
		for (int i = 0; i < Game.TILES_IN_HEIGHT; i++)
			for (int j = 0; j < levelOne.getLvlData()[0].length; j++) {
				int index = levelOne.getSpriteIndex(j, i);
				g.drawImage(levelSprite[index],
							Game.TILES_SIZE * j - lvlOffset,
							Game.TILES_SIZE * i,
							Game.TILES_SIZE,
							Game.TILES_SIZE,
							null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}
}
