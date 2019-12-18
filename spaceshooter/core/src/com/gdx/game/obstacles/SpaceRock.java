package com.gdx.game.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpaceRock extends Obstacle {
    public SpaceRock(float x, float y, float width, float height, float movSpeedFactor, Texture texture, ObstacleType type, float speed) {
        super(x, y, width, height, movSpeedFactor, texture, type, speed);
    }

    @Override
    public void moveObstacle() {
        x-= Gdx.graphics.getDeltaTime()*100;
    }
}
