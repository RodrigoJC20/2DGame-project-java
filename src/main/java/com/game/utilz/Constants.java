package com.game.utilz;

import com.game.engine.Game;

public class Constants {
	public static class GeneralConstants {
		public static final float GRAVITY = 0.04f * Game.SCALE;
	}

	public static class EnemyConstants {

		public static final int BEAR = 0;
		public static final int MINION = 1;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HURT = 3;
		public static final int DEAD = 4;

		public static class Bear {
			public static final int BEAR_SIZE_DEFAULT = 40;
			public static final int BEAR_SIZE = (int) (BEAR_SIZE_DEFAULT * Game.SCALE);

			public static final int BEAR_HITBOX_WIDTH = (int) (21 * Game.SCALE);
			public static final int BEAR_HITBOX_HEIGHT = (int) (27 * Game.SCALE);

			public static final int BEAR_ATTACKBOX_SIZE = (int) (15 * Game.SCALE);
			public static final int BEAR_ATTACKBOX_OFFSET = (int) (5 * Game.SCALE);

			public static final int BEAR_DRAW_OFFSET = (int) (13 * Game.SCALE);
			public static final int BEAR_FLIP_OFFSET = (int) (8 * Game.SCALE);

			public static final int BEAR_ROWS = 5;
			public static final int BEAR_COLS = 6;

			public static final int BEAR_MAX_HEALTH = 10;
			public static final int BEAR_DAMAGE = 15;
			public static final int BEAR_ATTACK_FRAME = 3;
			public static final float BEAR_ATTACK_DISTANCE = Game.TILES_SIZE * 0.8f;
		}

		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch (enemyType) {
				case BEAR -> {
					return switch (enemyState) {
						case IDLE, HURT -> 4;
						case RUNNING, ATTACK, DEAD -> 6;
						default -> 1;
					};
				}
				case MINION -> {
					return 2;
				}
				default -> {
					return 1;
				}
			}
		}
	}

	public static class LevelConstants {
		public static final int BLANK_TILE = 41;

		public static class LevelOne {
			public static final int LEVEL_ROWS = 6;
			public static final int LEVEL_COLS = 7;
			public static final int TOTAL_TILES = 42;
		}
	}

	public static class Environment {
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}

	public static class UI {
		public static class MenuButtons {
			public static final int B_WIDTH_DEFAULT = 26;
			public static final int B_PLAY_WIDTH_DEFAULT = 77;
			public static final int B_HEIGHT_DEFAULT = 26;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
			public static final int B_WIDTH_PLAY = (int) (B_PLAY_WIDTH_DEFAULT * Game.SCALE);
		}

		public static class SoundButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);

		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
		}

		public static class VolumeButton {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}

		public static class StatusBar {
			public static final int STATUS_BAR_WIDTH = (int) (192 * Game.SCALE);
			public static final int STATUS_BAR_HEIGHT = (int) (58 * Game.SCALE);
			public static final int STATUS_BAR_X = (int) (10 * Game.SCALE);
			public static final int STATUS_BAR_Y = (int) (10 * Game.SCALE);

			public static final int HEALTH_BAR_WIDTH = (int) (150 * Game.SCALE);
			public static final int HEALTH_BAR_HEIGHT = (int) (4 * Game.SCALE);
			public static final int HEALTH_BAR_X_START = (int) (34 * Game.SCALE);
			public static final int HEALTH_BAR_Y_START = (int) (14 * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int PLAYER_SIZE = 32;

		public static final	int PLAYER_HITBOX_WIDTH_DEFAULT = 17;
		public static final	int PLAYER_HITBOX_HEIGHT_DEFAULT = 27;

		public static final int PLAYER_HITBOX_WIDTH = (int) (PLAYER_HITBOX_WIDTH_DEFAULT * Game.SCALE);
		public static final int PLAYER_HITBOX_HEIGHT = (int) (PLAYER_HITBOX_HEIGHT_DEFAULT * Game.SCALE);
		public static final	int PLAYER_ATTACKBOX_SIZE = (int) (20 * Game.SCALE);
		public static final	int PLAYER_ATTACKBOX_OFFSET = (int) (5 * Game.SCALE);
		public static final int PLAYER_X_DRAW_OFFSET = (int) (7 * Game.SCALE);
		public static final int PLAYER_Y_DRAW_OFFSET = (int) (5 * Game.SCALE);

		public static final int PLAYER_SPRITE_ROWS = 7;
		public static final int PLAYER_SPRITE_COLS = 8;

		public static final int PLAYER_MAX_HEALTH = 100;
		public static final int PLAYER_DAMAGE = 10;
		public static final int PLAYER_ATTACK_FRAME_1 = 2;
		public static final int PLAYER_ATTACK_FRAME_2 = 4;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HURT = 5;
		public static final int DEATH = 6;

		public static int GetSpriteAmount(int player_action) {
			return switch (player_action) {
				case IDLE, HURT -> 4;
				case RUNNING, ATTACK -> 6;
				case JUMP -> 5;
				case FALLING -> 1;
				case DEATH -> 8;
				default -> 1;
			};
		}
	}
}
