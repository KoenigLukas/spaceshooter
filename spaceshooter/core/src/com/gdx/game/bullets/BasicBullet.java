package com.gdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;

public class BasicBullet extends Bullet {
    
    public BasicBullet(float x, float y, float width, float height, int damage, float movSpeedFactor, Texture texture) {
        super(x, y, width, height, damage, movSpeedFactor, texture);
    }

    @Override
    public void spawnBullet() {

    }

    @Override
    public void moveBullet() {

    }
}
