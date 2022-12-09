package com.game.entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.Directions.LEFT;
import static com.game.utilz.Constants.Directions.RIGHT;

public abstract class Entity {
	protected float x, y;
	protected int width, height;

	protected int walkDirection;
	protected float movingSpeed;
	protected float fallSpeed;

	protected Rectangle2D.Float hitbox;
	protected Rectangle2D.Float attackBox;

	protected int currentHealth;

	protected int animationIndex, animationTick;
	protected BufferedImage[][] entityAnimations;
	protected int entityState;

	protected boolean inAir;
	protected boolean attackChecked;

	protected int flipX = 0;
	protected int flipW = 1;

	protected static final int animationSpeed = 25;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected void drawHitbox(Graphics g, int xLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void drawAttackbox(Graphics g, int lvlOffset) {
		// For debugging the attackbox
		g.setColor(Color.RED);
		g.drawRect((int) (attackBox.x - lvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}

	protected void initAttackBox(float x, float y, float width, float height) {
		attackBox = new Rectangle2D.Float(x, y, width, height);
	}

	protected void updateAttackBox(int ATTACK_BOX_OFFSET) {
		if (walkDirection == RIGHT) {
			attackBox.x = hitbox.x + hitbox.width + ATTACK_BOX_OFFSET;
		} else if (walkDirection == LEFT) {
			attackBox.x = (hitbox.x - attackBox.width) - ATTACK_BOX_OFFSET;
		}

		attackBox.y = hitbox.y + ATTACK_BOX_OFFSET;
	}

	protected void checkEntityHit(Entity entity, int damage, int maxHealth) {
		if (attackBox.intersects(entity.hitbox)) {
			entity.updateHealth(-damage, maxHealth);
			attackChecked = true;
		}
	}

	protected abstract void loadAnimations();

	protected abstract void initProperties();

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public abstract void draw(Graphics g, int xLvlOffset);

	public void updateHealth(int damage, int maxHealth) {
		currentHealth += damage;

		if (currentHealth <= 0) {
			currentHealth = 0;
			// Game Over
		} else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}
}
