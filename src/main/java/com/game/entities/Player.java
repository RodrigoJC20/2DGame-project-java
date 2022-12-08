package com.game.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.engine.Game;
import com.game.utilz.LoadSave;

import static com.game.utilz.Constants.PlayerConstants.*;
import static com.game.utilz.Constants.GeneralConstants.*;
import static com.game.utilz.HelpMethods.*;

public class Player extends Entity {
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private int[][] lvlData;

	// Jumping / Gravity
	private boolean jump;

	private static final float jumpSpeed = -2.25f * Game.SCALE;
	private static final float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);

		loadAnimations();

		initHitbox(x, y, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT);
	}

	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();
	}

	@Override
	public void draw(Graphics g, int lvlOffset) {
		g.drawImage(entityAnimations[entityState][animationIndex],
					(int) (hitbox.x - PLAYER_X_DRAW_OFFSET) - lvlOffset,
					(int) (hitbox.y - PLAYER_Y_DRAW_OFFSET),
					width,
					height,
					null);

		//drawHitbox(g, lvlOffset);
	}

	private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(entityState)) {
				animationIndex = 0;
				attacking = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = entityState;

		if (moving)
			entityState = RUNNING;
		else
			entityState = IDLE;

		if (inAir) {
			if (fallSpeed < 0)
				entityState = JUMP;
			else
				entityState = FALLING;
		}

		if (attacking)
			entityState = ATTACK_1;

		if (startAni != entityState)
			resetAniTick();
	}

	private void resetAniTick() {
		animationTick = 0;
		animationIndex = 0;
	}

	private void updatePosition() {
		moving = false;
		
		if (jump) jump();

		if (!inAir) {
			if ((!left && !right) || (right && left)) {
				return;
			}
		}

		float xSpeed = 0;

		if (left) {
			xSpeed -= movingSpeed;
		}
		if (right) {
			xSpeed += movingSpeed;
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
	protected void loadAnimations() {
		entityState = IDLE;

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		entityAnimations = new BufferedImage[10][8];
		for (int j = 0; j < entityAnimations.length; j++) {
			for (int i = 0; i < entityAnimations[j].length; i++) {
				entityAnimations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
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

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
