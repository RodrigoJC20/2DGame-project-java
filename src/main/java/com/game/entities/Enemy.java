package com.game.entities;

import com.game.engine.Game;

import static com.game.utilz.Constants.Directions.LEFT;
import static com.game.utilz.Constants.Directions.RIGHT;
import static com.game.utilz.Constants.EnemyConstants.*;
import static com.game.utilz.Constants.GeneralConstants.GRAVITY;
import static com.game.utilz.HelpMethods.*;

public abstract class Enemy extends Entity {
    private final int enemyType;

    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int enemyYTile;
    protected float attackDistance = Game.TILES_SIZE * 0.8f;

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
                if (entityState == ATTACK) {
                    entityState = IDLE;
                }
            }
        }
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            enemyYTile = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
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

    protected void newState(int enemyState) {
        entityState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.hitbox.y / Game.TILES_SIZE);

        if (playerTileY != enemyYTile) {
            return false;
        }

        if (!isPlayerInRange(player)) {
            return false;
        }

        return IsSightClear(lvlData, hitbox, player.hitbox, enemyYTile);
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean isPlayerInRange(Player player) {
        int absouluteValue = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absouluteValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absouluteValue = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absouluteValue <= attackDistance;
    }

    public int getEnemyState() {
        return entityState;
    }
}
