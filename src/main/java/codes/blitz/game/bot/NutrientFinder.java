package codes.blitz.game.bot;

import codes.blitz.game.generated.Position;

public class NutrientFinder {
    int distance = Integer.MAX_VALUE;
    int[][] nutrientGrid;
    int x = 0;
    int y = 0;

    NutrientFinder(int[][] nutrientGrid, int x, int y) {
        this.nutrientGrid = nutrientGrid;
        this.x = x;
        this.y = y;
    }

    public Position findNearestNutrientTileFromSpore() {
        int nearestNutrientTileX = -1;
        int nearestNutrientTileY = -1;
        //simple pathfinding, issues with obstacles TODO pathfinding for NEUTRAL tiles
        for (int i = 0; i < nutrientGrid.length; i++) {
            for (int j = 0; j < nutrientGrid[i].length; j++) {
                if (nutrientGrid[i][j] > 0) {
                    int dist = Math.abs(x - i) + Math.abs(y - j);
                    if (dist != 0 && dist < distance) {
                        distance = dist;
                        nearestNutrientTileX = i;
                        nearestNutrientTileY = j;
                    }
                }
            }
        }
        if (nearestNutrientTileX == -1 || nearestNutrientTileY == -1)
            throw new RuntimeException("No nutrient tile found");
        return new Position(nearestNutrientTileX, nearestNutrientTileY);
    }
}
