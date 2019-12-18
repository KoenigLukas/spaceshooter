package com.gdx.game.spaceships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class SpaceShip extends Rectangle {

    protected float movSpeedFactor;
    protected int lifes;
    private Texture texture;

    public SpaceShip(float x, float y, float width, float height, float movSpeedFactor, int lifes, Texture texture) {
        super(x, y, width, height);
        this.movSpeedFactor = movSpeedFactor;
        this.lifes = lifes;
        this.texture = texture;
    }

    public void deductLife(int damage) {
        lifes -= damage;
        if (lifes < 0) lifes = 0;
    }

    public abstract void moveShip(int direction);

    public float getMovSpeedFactor() {
        return movSpeedFactor;
    }

    public float getLifes() {
        return lifes;
    }

    public Texture getTexture() {
        return texture;
    }

}
