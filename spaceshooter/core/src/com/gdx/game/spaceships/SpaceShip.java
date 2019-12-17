package com.gdx.game.spaceships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.game.bullets.Bullet;

public abstract class SpaceShip extends Rectangle {

    protected float movSpeedFactor;
    protected float lifes;
    private Texture texture;

    public SpaceShip(float x, float y, float width, float height, float movSpeedFactor, float lifes, Texture texture) {
        super(x, y, width, height);
        this.movSpeedFactor = movSpeedFactor;
        this.lifes = lifes;
        this.texture = texture;
    }

    public abstract void shootBullet(Bullet bullet);

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
