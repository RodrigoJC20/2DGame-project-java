package com.game.entities;

import com.game.engine.Game;

import static com.game.utilz.Constants.Directions.LEFT;
import static com.game.utilz.Constants.Directions.RIGHT;
import static com.game.utilz.Constants.EnemyConstants.*;
import static com.game.utilz.Constants.GeneralConstants.GRAVITY;
import static com.game.utilz.Constants.PlayerConstants.IDLE;
import static com.game.utilz.Constants.PlayerConstants.PLAYER_MAX_HEALTH;
import static com.game.utilz.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected boolean firstUpdate;

    protected int enemyYTile;

    protected boolean alive;

    protected final int enemyType;

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
                switch (entityState) {
                    case ATTACK, HURT -> entityState = IDLE;
                    case DEAD -> alive = false;
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

    protected int flipX(int ENEMY_FLIP_OFFSET) {
        if (walkDirection == RIGHT) {
            return width + ENEMY_FLIP_OFFSET;
        } else {
            return 0;
        }
    }

    protected int flipW() {
        if (walkDirection == RIGHT) {
            return -1;
        } else {
            return 1;
        }

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

        if (walkDirection == LEFT) {
            xSpeed = -movingSpeed;
        } else {
            xSpeed = movingSpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected void newState(int enemyState) {
        entityState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player, float attackDistance) {
        int playerTileY = (int) (player.hitbox.y / Game.TILES_SIZE);

        if (playerTileY != enemyYTile) {
            return false;
        }

        if (!isPlayerInRange(player, attackDistance)) {
            return false;
        }

        return IsSightClear(lvlData, hitbox, player.hitbox, enemyYTile);
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected boolean isPlayerInRange(Player player, float attackDistance) {
        float visionRange = attackDistance * 5;
        int absouluteValue_1 = (int) (Math.abs(player.hitbox.x - hitbox.x));
        int absouluteValue_2 = (int) (Math.abs((player.hitbox.x + player.hitbox.width) - (hitbox.x + hitbox.width)));

        return (absouluteValue_1 <= visionRange) || (absouluteValue_2 <= visionRange);
    }

    protected boolean isPlayerCloseForAttack(Player player, float attackDistance) {
        int absouluteValue_1 = (int) (Math.abs(player.hitbox.x - hitbox.x));
        int absouluteValue_2 = (int) (Math.abs((player.hitbox.x + player.hitbox.width) - (hitbox.x + hitbox.width)));

        return (absouluteValue_1 <= attackDistance) || (absouluteValue_2 <= attackDistance);
    }

    public int getEnemyState() {
        return entityState;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void reset() {
        super.reset();
        alive = true;
        firstUpdate = true;
        fallSpeed = 0;
    }
}
