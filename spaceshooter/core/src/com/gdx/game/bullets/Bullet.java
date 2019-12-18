package com.gdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bullet extends Rectangle {


    protected int damage;
    protected float movSpeedFactor;
    private Texture texture;
    private BulletType type;

    public Bullet(float x, float y, float width, float height, int damage, float movSpeedFactor, BulletType type,Texture texture) {
        super(x, y, width, height);
        this.damage = damage;
        this.movSpeedFactor = movSpeedFactor;
        this.texture = texture;
        this.type = type;

    }

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

    public BulletType getType() {
        return type;
    }

    public enum BulletType {
        BASIC(500000000),
        SHOTGUN(900000000),
        HOMINGBULLET(900000000),
        ;

        private int delay;

        BulletType(int delay) {
            this.delay = delay;
        }

        public int getDelay(){
            return this.delay;
        }
    }
}
