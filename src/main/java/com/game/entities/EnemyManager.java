package com.game.entities;

import com.game.gamestates.Playing;
import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.util.ArrayList;

import static com.game.utilz.Constants.EnemyConstants.Bear.BEAR_MAX_HEALTH;
import static com.game.utilz.Constants.PlayerConstants.PLAYER_DAMAGE;

public class EnemyManager {
    private Playing playing;
    private final Player player;
    private final int[][] lvlData;

    private ArrayList<BearMonster> bearMonsters = new ArrayList<>();

    public EnemyManager(Playing playing, int[][] lvlData, Player player) {
        this.player = player;
        this.playing = playing;
        this.lvlData = lvlData;

        addEnemies();
    }

    public void checkEnemyHit() {
        for (BearMonster bearMonster : bearMonsters) {
            player.checkEntityHit(bearMonster, PLAYER_DAMAGE, BEAR_MAX_HEALTH);
        }
    }

    private void addEnemies() {
        bearMonsters = LoadSave.GetBears();
    }

    public void update() {
        updateBears(lvlData, player);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBears(g, xLvlOffset);
    }

    private void drawBears(Graphics g, int xLvlOffset) {
        for (BearMonster bear : bearMonsters) {
            if (bear.isAlive()) {
                bear.draw(g, xLvlOffset);
            }
        }
    }

    private void updateBears(int[][] lvlData, Player player) {
        for (BearMonster bearMonster : bearMonsters) {
            if (bearMonster.isAlive()) {
                bearMonster.update(lvlData, player);
            }
        }
    }

    public void resetAllEnemies() {
        for(BearMonster bearMonster : bearMonsters) {
            bearMonster.reset();
        }
    }
}
