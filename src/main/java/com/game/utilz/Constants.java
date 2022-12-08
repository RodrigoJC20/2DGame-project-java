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
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int BEAR_SIZE_DEFAULT = 40;
		public static final int BEAR_HITBOX_WIDTH_DEFAULT = 21;
		public static final int BEAR_HITBOX_HEIGHT_DEFAULT = 27;

		public static final int BEAR_HITBOX_WIDTH = (int) (BEAR_HITBOX_WIDTH_DEFAULT * Game.SCALE);
		public static final int BEAR_HITBOX_HEIGHT = (int) (BEAR_HITBOX_HEIGHT_DEFAULT * Game.SCALE);
		public static final int BEAR_SIZE = (int) (BEAR_SIZE_DEFAULT * Game.SCALE);
		public static final int BEAR_DRAW_OFFSET = (int) (13 * Game.SCALE);

		public static final int BEAR_ROWS = 5;
		public static final int BEAR_COLS = 6;

		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch (enemyType) {
				case BEAR -> {
					return switch (enemyState) {
						case IDLE, HIT -> 4;
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

	public static class Enviroment {
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
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final	int PLAYER_HITBOX_WIDTH_DEFAULT = 17;
		public static final	int PLAYER_HITBOX_HEIGHT_DEFAULT = 27;

		public static final int PLAYER_HITBOX_WIDTH = (int) (PLAYER_HITBOX_WIDTH_DEFAULT * Game.SCALE);
		public static final int PLAYER_HITBOX_HEIGHT = (int) (PLAYER_HITBOX_HEIGHT_DEFAULT * Game.SCALE);
		public static final int PLAYER_X_DRAW_OFFSET = (int) (7 * Game.SCALE);
		public static final int PLAYER_Y_DRAW_OFFSET = (int) (4 * Game.SCALE);

		public static final int IDLE = 0;
		public static final int JUMP = 1;
		public static final int ATTACK_1 = 2;
		public static final int ATTACK_2 = 3;
		public static final int HIT = 4;
		public static final int RUNNING = 5;
		public static final int THROW = 6;
		public static final int ATTACK_RUN = 7;
		public static final int DEATH = 8;
		public static final int FALLING = 9;

		public static int GetSpriteAmount(int player_action) {
			return switch (player_action) {
				case RUNNING, ATTACK_2, ATTACK_RUN -> 6;
				case IDLE, HIT, ATTACK_1, THROW -> 4;
				case JUMP -> 3;
				case DEATH -> 8;
				case FALLING -> 1;
				default -> 1;
			};
		}
	}
}
