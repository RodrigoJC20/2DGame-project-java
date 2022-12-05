package com.game.utilz;

import com.game.engine.Game;

public class Constants {
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
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
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
