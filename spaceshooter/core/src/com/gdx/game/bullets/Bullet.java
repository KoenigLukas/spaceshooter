package com.gdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bullet extends Rectangle {


    private int damage;
    private float movSpeedFactor;
    private Texture texture;

    public Bullet(float x, float y, float width, float height, int damage, float movSpeedFactor,Texture texture) {
        super(x, y, width, height);
        this.damage = damage;
        this.movSpeedFactor = movSpeedFactor;
        this.texture = texture;
    }

    public abstract void spawnBullet();
    public abstract void moveBullet();

    public int getDamage() {
        return damage;
    }

    public float getMovSpeedFactor() {
        return movSpeedFactor;
    }

    public Texture getTexture() {
        return texture;
    }
}
