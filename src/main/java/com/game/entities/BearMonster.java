package com.game.entities;

import com.game.engine.Game;
import com.game.gamestates.Playing;
import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.EnemyConstants.*;
import static com.game.utilz.Constants.EnemyConstants.Bear.*;
import static com.game.utilz.Constants.EnemyConstants.BEAR;
import static com.game.utilz.Constants.PlayerConstants.PLAYER_MAX_HEALTH;

public class BearMonster extends Enemy {

    public BearMonster(float x, float y) {
        super(x, y, BEAR_SIZE, BEAR_SIZE, BEAR);

        initProperties();
        loadAnimations();
        initHitbox(x, y, BEAR_HITBOX_WIDTH, BEAR_HITBOX_HEIGHT);
        initAttackBox(x, y, BEAR_ATTACKBOX_SIZE, BEAR_ATTACKBOX_SIZE);
    }

    @Override
    protected void initProperties() {
        movingSpeed = Game.SCALE * 0.3f;
        entityState = IDLE;
        currentHealth = BEAR_MAX_HEALTH;
        alive = true;
        firstUpdate = true;
    }

    public void update(int[][] lvlData, Player player) {
        updateAnimationTick();
        updateBehavior(lvlData, player);
        updateAttackBox(BEAR_ATTACKBOX_OFFSET, BEAR_ATTACKBOX_OFFSET);
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
            return;
        }

        switch (entityState) {
            case IDLE -> newState(RUNNING);
            case RUNNING -> {
                if (canSeePlayer(lvlData, player, BEAR_ATTACK_DISTANCE)) {
                    turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player, BEAR_ATTACK_DISTANCE)) {
                        newState(ATTACK);
                    }
                }
                move(lvlData);
            }
            case ATTACK -> {
                if (animationIndex == 0) {
                    attackChecked = false;
                }
                if (animationIndex == BEAR_ATTACK_FRAME && !attackChecked) {
                    checkEntityHit(player, BEAR_DAMAGE, PLAYER_MAX_HEALTH);
                }
            }
        }
    }

    @Override
    public void updateHealth(int damage, int maxHealth) {
        super.updateHealth(damage, maxHealth);

        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HURT);
        }
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(entityAnimations[entityState][animationIndex],
                (int) (hitbox.x - BEAR_DRAW_OFFSET - xLvlOffset + flipX(BEAR_FLIP_OFFSET)),
                (int) hitbox.y - BEAR_DRAW_OFFSET,
                BEAR_SIZE * flipW(),
                BEAR_SIZE,null);

        if (Playing.debugMode) {
            drawHitbox(g, xLvlOffset);
            drawAttackbox(g, xLvlOffset);
        }
    }

    @Override
    protected void loadAnimations() {
        entityAnimations = new BufferedImage[BEAR_ROWS][BEAR_COLS];

        BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.MINI_BEAR_SPRITE);

        for(int i = 0; i< entityAnimations.length; i++) {
            for(int j = 0; j< entityAnimations[0].length; j++) {
                entityAnimations[i][j] = tmp.getSubimage(j*BEAR_SIZE_DEFAULT,
                        i*BEAR_SIZE_DEFAULT,
                        BEAR_SIZE_DEFAULT,
                        BEAR_SIZE_DEFAULT);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();

        currentHealth = BEAR_MAX_HEALTH;
        entityState = IDLE;
    }
}
