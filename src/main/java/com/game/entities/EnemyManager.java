package com.game.entities;

import com.game.gamestates.Playing;
import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private int[][] lvlData;

    private ArrayList<BearMonster> bearMonsters = new ArrayList<>();

    public EnemyManager(Playing playing, int[][] lvlData) {
        this.playing = playing;
        this.lvlData = lvlData;

        addEnemies();
    }

    private void addEnemies() {
        bearMonsters = LoadSave.GetBears();
    }

    public void update() {
        updateBears(lvlData);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBears(g, xLvlOffset);
    }

    private void drawBears(Graphics g, int xLvlOffset) {
        for (BearMonster bear : bearMonsters) {
            bear.draw(g, xLvlOffset);
        }
    }

    private void updateBears(int[][] lvlData) {
        for (BearMonster bearMonster : bearMonsters) {
            bearMonster.update(lvlData);
        }
    }
}
