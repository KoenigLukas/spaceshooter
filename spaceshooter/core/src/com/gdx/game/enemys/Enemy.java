package com.gdx.game.enemys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public abstract class Enemy extends Rectangle {

    protected float movSpeedFactor;
    protected int lifes;
    private Texture texture;

    public Enemy(float x, float y, float width, float height, float movSpeedFactor, int lifes,Texture texture) {
        super(x, y, width, height);
        this.movSpeedFactor = movSpeedFactor;
        this.lifes = lifes;
        this.texture = texture;
    }

    public abstract void moveEnemy();
    public abstract void shootBullet();

    public float getMovSpeedFactor() {
        return movSpeedFactor;
    }

    public int getLifes() {
        return lifes;
    }

    public Texture getTexture() {
        return texture;
    }
}
