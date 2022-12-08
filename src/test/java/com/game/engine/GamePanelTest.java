package com.game.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GamePanelTest {
    @Test
    void whenNewGamePanel_thenSetGame() {
        // Given
        Game game = Mockito.mock(Game.class);

        // When
        GamePanel gamePanel = new GamePanel(game);

        // Then
        Assertions.assertEquals(game, gamePanel.getGame());
    }

    @Test
    void givenAGamePanel_whenPaintComponent_thenGameRepaint() {

    }
}
