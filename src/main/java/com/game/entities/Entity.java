package com.game.entities;

import com.game.engine.Game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected float movingSpeed = Game.SCALE;

	protected int animationIndex, animationTick;
	protected BufferedImage[][] entityAnimations;
	protected int entityState;

	protected boolean inAir;
	protected float fallSpeed;

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

	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public int getAnimationIndex() {
		return animationIndex;
	}

	public abstract void draw(Graphics g, int xLvlOffset);

	protected abstract void loadAnimations();
}
