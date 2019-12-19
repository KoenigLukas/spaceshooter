package com.gdx.game.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpaceRock extends Obstacle {
    public SpaceRock(float x, float y, Texture texture) {
        super(x, y, 64, 64, 0, texture, ObstacleType.ROCK);
    }

    @Override
    public void moveObstacle() {
        x -= Gdx.graphics.getDeltaTime() * 200;
    }
}
