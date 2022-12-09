package com.game.entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import com.game.engine.Game;
import com.game.gamestates.Playing;
import com.game.utilz.LoadSave;

import static com.game.utilz.Constants.Directions.LEFT;
import static com.game.utilz.Constants.Directions.RIGHT;
import static com.game.utilz.Constants.PlayerConstants.*;
import static com.game.utilz.Constants.GeneralConstants.*;
import static com.game.utilz.Constants.UI.StatusBar.*;
import static com.game.utilz.HelpMethods.*;

public class Player extends Entity {
	// Move
	private boolean moving = false, attacking = false;
	private boolean left, right;

	// Game data
	private int[][] lvlData;
	private final Playing playing;

	// Jumping / Gravity
	private boolean jump;
	private static final float jumpSpeed = -2.25f * Game.SCALE;
	private static final float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	// StatusBarUI
	private BufferedImage stausBarImg;
	private int healthWidth = HEALTH_BAR_WIDTH;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);

		this.playing = playing;
		initProperties();
		loadAnimations();
		initHitbox(x, y, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT);
		initAttackBox(x, y, PLAYER_ATTACKBOX_SIZE, PLAYER_ATTACKBOX_SIZE);
	}

	protected void initProperties() {
		entityState = IDLE;
		walkDirection = RIGHT;
		currentHealth = PLAYER_MAX_HEALTH;
		movingSpeed = Game.SCALE;
		attackChecked = false;
	}

	public void update() {
		updateHealthBar();
		updateAttackBox(PLAYER_ATTACKBOX_X_OFFSET, PLAYER_ATTACKBOX_Y_OFFSET);
		updatePosition();
		updateAnimationTick();
		checkHitEnemy();
		setAnimation();
		checkPlayerHealth();
	}

	private void checkPlayerHealth() {
		if (currentHealth <= 0) {
			playing.setGameOver(true);
		}
	}

	private void checkHitEnemy() {
		if (!attacking || attackChecked) {
			return;
		}

		if (animationIndex == PLAYER_ATTACK_FRAME_1 || animationIndex == PLAYER_ATTACK_FRAME_2) {
			playing.checkEnemyHit();
		}
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) PLAYER_MAX_HEALTH) * HEALTH_BAR_WIDTH);
	}

	private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(entityState)) {
				animationIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void setAnimation() {
		int startAnimation = entityState;

		if (moving) {
			entityState = RUNNING;
		}
		else {
			entityState = IDLE;
		}

		if (inAir) {
			if (fallSpeed < 0) {
				entityState = JUMP;
			} else {
				entityState = FALLING;
			}
		}

		if (attacking) {
			entityState = ATTACK;
			if (startAnimation != ATTACK) {
				animationIndex = 2;
				animationTick = 0;
				return;
			}
		}

		if (startAnimation != entityState) {
			resetAniTick();
		}
	}

	private void resetAniTick() {
		animationTick = 0;
		animationIndex = 0;
	}

	private void updatePosition() {
		moving = false;
		
		if (jump) {
			jump();
		}

		if (!inAir) {
			if ((!left && !right) || (right && left)) {
				return;
			}
		}

		float xSpeed = 0;

		if (left) {
			xSpeed -= movingSpeed;
			flipX = width;
			flipW = -1;
			walkDirection = LEFT;
		}
		if (right) {
			xSpeed += movingSpeed;
			flipX = 0;
			flipW = 1;
			walkDirection = RIGHT;
		}

		if (!inAir) {
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += fallSpeed;
				fallSpeed += GRAVITY;
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
				if (fallSpeed > 0) {
					resetInAir();
				} else {
					fallSpeed = fallSpeedAfterCollision;
				}
			}
		}

		updateXPos(xSpeed);

		moving = true;
	}

	private void jump() {
		if (inAir) return;

		inAir = true;
		fallSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		fallSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	@Override
	public void draw(Graphics g, int lvlOffset) {
		g.drawImage(entityAnimations[entityState][animationIndex],
				(int) (hitbox.x - PLAYER_X_DRAW_OFFSET) - lvlOffset + flipX,
				(int) (hitbox.y - PLAYER_Y_DRAW_OFFSET),
				width * flipW,
				height,
				null);

		drawUI(g);

		if (Playing.debugMode) {
			drawHitbox(g, lvlOffset);
			drawAttackbox(g, lvlOffset);
		}
	}

	private void drawUI(Graphics g) {
		g.drawImage(stausBarImg, STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT, null);
		g.setColor(Color.RED);
		g.fillRect(HEALTH_BAR_X_START + STATUS_BAR_X, HEALTH_BAR_Y_START + STATUS_BAR_Y, healthWidth, HEALTH_BAR_HEIGHT);
	}

	@Override
	protected void loadAnimations() {


		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		entityAnimations = new BufferedImage[PLAYER_SPRITE_ROWS][PLAYER_SPRITE_COLS];
		for (int j = 0; j < entityAnimations.length; j++) {
			for (int i = 0; i < entityAnimations[j].length; i++) {
				entityAnimations[j][i] = img.getSubimage(i * PLAYER_SIZE, j * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
			}
		}

		stausBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}

	public void resetDirectionBooleans() {
		left = false;
		right = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	@Override
	public void reset() {
		super.reset();

		resetDirectionBooleans();
		attacking = false;
		moving = false;
		entityState = IDLE;
		currentHealth = PLAYER_MAX_HEALTH;
		walkDirection = RIGHT;

		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}
}
