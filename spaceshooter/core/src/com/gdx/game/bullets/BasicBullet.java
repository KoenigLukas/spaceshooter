package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BasicBullet extends Bullet {

    public BasicBullet(float x, float y, Texture texture) {
        super(x, y, 32, 32, 1, 1, BulletType.BASIC, texture);
    }

    @Override
    public void moveBullet() {
        this.x += 400 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
    }
}
