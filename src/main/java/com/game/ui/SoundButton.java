package com.game.ui;

import static com.game.utilz.Constants.UI.SoundButtons.*;

import com.game.utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImgs;
    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadImgs();
    }

    private void loadImgs() {
        BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];

        for(int i=0; i<soundImgs.length; i++) {
            for(int j=0; j<soundImgs[i].length; j++) {
                soundImgs[i][j] = tmp.getSubimage(j*SOUND_SIZE_DEFAULT, i*SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    @Override
    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if (mouseOver)
            colIndex = 1;
        if (mousePressed)
            colIndex = 2;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
