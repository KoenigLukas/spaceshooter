package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BossBullet extends Bullet {

    public BossBullet(float x, float y, Texture texture) {
        super(x, y, 32, 32, 1, 1, BulletType.BASIC, texture, BulletDirection.BACK);
    }

    @Override
    public void moveBullet() {
        this.x-=4*Gdx.graphics.getDeltaTime();
    }
}
