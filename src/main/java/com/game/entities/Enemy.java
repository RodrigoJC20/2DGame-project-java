package com.game.entities;

import static com.game.utilz.Constants.EnemyConstants.GetSpriteAmount;

public abstract class Enemy extends Entity {
    protected int enemyState, enemyType;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);

        initHitbox(x, y, width, height);

        this.enemyType = enemyType;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    public void update() {
        updateAnimationTick();
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
