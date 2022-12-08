package com.game.utilz;

import java.awt.geom.Rectangle2D;

import com.game.engine.Game;

import static com.game.utilz.Constants.LevelConstants.BLANK_TILE;
import static com.game.utilz.Constants.LevelConstants.LevelOne.TOTAL_TILES;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    return !IsSolid(x, y + height, lvlData);

        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[xTile][yTile];

        if (value >= TOTAL_TILES || value < 0)
            return true;

        return value != BLANK_TILE;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottom-left and bottom-right
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);

        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int yTile) {
        int xTileStart = (int) (hitbox1.x / Game.TILES_SIZE);
        int xTileEnd = (int) (hitbox2.x / Game.TILES_SIZE);

        if (xTileStart > xTileEnd) {
            xTileStart = xTileStart ^ xTileEnd ^ (xTileEnd = xTileStart);
        }

        for (int i = 0; i < xTileEnd - xTileStart; i++) {
            if (IsTileSolid(xTileStart + i, yTile, lvlData)) {
                return false;
            }
        }

        return true;
    }
}