package com.game.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.engine.Game;
import com.game.utilz.LoadSave;

import static com.game.utilz.Constants.PlayerConstants.*;
import static com.game.utilz.HelpMethods.*;

public class Player extends Entity {
	private static final float playerSpeed = Game.SCALE;

	private BufferedImage[][] animations;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private int[][] lvlData;
	private float xDrawOffset = 7 *  Game.SCALE, yDrawOffset = 4 * Game.SCALE;

	// Jumping / Gravity
	private boolean jump, inAir = false;
	private float airSpeed = 0f;
	private static final float gravity = 0.04f * Game.SCALE;
	private static final float jumpSpeed = -2.25f * Game.SCALE;
	private static final float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);

		loadAnimations();

		initHitbox(x, y, (int) (17 * Game.SCALE), (int) (27 * Game.SCALE));
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
		//drawHitbox(g);
	}

	private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(playerAction)) {
				animationIndex = 0;
				attacking = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}

		if (attacking)
			playerAction = ATTACK_1;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		animationTick = 0;
		animationIndex = 0;
	}

	private void updatePos() {
		moving = false;
		
		if (jump) jump();

//		if (!left && !right && !inAir) return;

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData)) inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
			}
		}

		updateXPos(xSpeed);

		moving = true;
	}

	private void jump() {
		if (inAir) return;

		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[10][8];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
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
