package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BasicBullet extends Bullet {

    public BasicBullet(float x, float y, Texture texture) {
        super(x, y, 32, 32, 1, 1, BulletType.BASIC, texture, BulletDirection.STRAIGHT);
    }

    @Override
    public void moveBullet() {
        if(direction == BulletDirection.STRAIGHT) this.x += 400 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        else if(direction == BulletDirection.BACK) this.x -= 400 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
    }
}
