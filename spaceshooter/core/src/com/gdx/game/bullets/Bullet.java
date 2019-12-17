package com.gdx.game.bullets;

public abstract class Bullet {


    private int damage;
    private float movSpeedFactor;

    public abstract void spawnBullet();
    public abstract void moveBullet();
}
