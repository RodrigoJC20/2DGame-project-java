package com.game.entities;

import static com.game.utilz.Constants.Directions.*;
import static com.game.utilz.Constants.EnemyConstants.*;
import static com.game.utilz.Constants.GeneralConstants.GRAVITY;
import static com.game.utilz.HelpMethods.*;

public abstract class Enemy extends Entity {
    private boolean firstUpdate = true;
    private int walkDir = LEFT;
    private int enemyType;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);

        initHitbox(x, y, width, height);

        this.enemyType = enemyType;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, entityState)) {
                animationIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData) {
        updateAnimationTick();
        updatePosition(lvlData);
    }

    protected void updatePosition(int[][] lvlData) {
        if (firstUpdate) {
            if (!IsEntityOnFloor(hitbox, lvlData)) {
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += fallSpeed;
                fallSpeed += GRAVITY;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else {
            switch (entityState) {
                case IDLE -> entityState = RUNNING;
                case RUNNING -> updateRun(lvlData);
            }
        }
    }

    private void updateRun(int[][] lvlData) {
        float xSpeed;

        if (walkDir == LEFT) {
            xSpeed = -movingSpeed;
        } else {
            xSpeed = movingSpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, (walkDir == LEFT ? xSpeed : xSpeed + hitbox.width), lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    public int getEnemyState() {
        return entityState;
    }
}
