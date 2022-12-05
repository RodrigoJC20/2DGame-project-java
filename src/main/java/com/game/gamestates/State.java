package com.game.gamestates;

import com.game.engine.Game;
import com.game.ui.IMenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e, IMenuButton mb) {
        return mb.getBounds().contains(e.getPoint());
    }

    public Game getGame() {
        return game;
    }
}
