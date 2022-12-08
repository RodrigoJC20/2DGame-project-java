package com.game.entities;

import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static com.game.utilz.Constants.EnemyConstants.*;

public class BearMonster extends Enemy {

    public BearMonster(float x, float y) {
        super(x, y, BEAR_SIZE, BEAR_SIZE, BEAR);

        loadAnimations();
        initHitbox(x, y, BEAR_HITBOX_WIDTH, BEAR_HITBOX_HEIGHT);
    }


    public void update(int[][] lvlData) {
        updateAnimationTick();
        updatePosition(lvlData);
    }

    private void updatePosition(int[][] lvlData) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch (entityState) {
                case IDLE -> newState(RUNNING);
                case RUNNING -> move(lvlData);
            }
        }
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(entityAnimations[entityState][animationIndex],
                (int) hitbox.x - BEAR_DRAW_OFFSET - xLvlOffset,
                (int) hitbox.y - BEAR_DRAW_OFFSET,
                BEAR_SIZE,
                BEAR_SIZE,null);
        drawHitbox(g, xLvlOffset);
    }

    @Override
    protected void loadAnimations() {
        movingSpeed *= 0.5;
        entityState = IDLE;
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
}
