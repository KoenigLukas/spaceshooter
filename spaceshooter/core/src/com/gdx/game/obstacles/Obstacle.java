package com.gdx.game.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Obstacle extends Rectangle {

    protected float movSpeedFactor;
    private Texture texture;
    private ObstacleType type;

    public Obstacle(float x, float y, float width, float height, float movSpeedFactor, Texture texture, ObstacleType type) {
        super(x, y, width, height);
        this.movSpeedFactor = movSpeedFactor;
        this.texture = texture;
        this.type = type;
    }

    public abstract void moveObstacle();

    public float getMovSpeedFactor() {
        return movSpeedFactor;
    }

    public Texture getTexture() {
        return texture;
    }

    public ObstacleType getType() {
        return type;
    }

    public enum ObstacleType {
        ROCK,
        SATELLITE,
    }
}
