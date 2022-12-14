package com.game.utilz;

import com.game.engine.Game;
import com.game.entities.BearMonster;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import static com.game.utilz.Constants.EnemyConstants.*;
import static com.game.utilz.Constants.LevelConstants.LevelOne.*;

public class LoadSave {

	public static final String PLAYER_ATLAS = "player_1_sprites.png";
	public static final String LEVEL_ONE_TILESET = "tileset_map_1.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String MENU_BUTTONS = "menu_buttons.png";
	public static final String MENU_BACKGROUND = "menu_background_1.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_buttons.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "menu_background_2.png";
	public static final String PLAYING_BACKGROUND_IMG = "playing_background.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	public static final String MINI_BEAR_SPRITE = "mini_bear_sprites.png";
	public static final String HEALTH_BAR = "health_power_bar.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if (value >= TOTAL_TILES)
					value = 0;
				lvlData[i][j] = value;
			}
		}

		return lvlData;
	}

	public static ArrayList<BearMonster> GetBears() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<BearMonster> bearMonsterList = new ArrayList<>();

		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getGreen();
				if (value == BEAR) {
					bearMonsterList.add(new BearMonster(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}

		return bearMonsterList;
	}
}
