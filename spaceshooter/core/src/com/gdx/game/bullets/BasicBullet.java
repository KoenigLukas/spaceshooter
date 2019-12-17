package com.gdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;

public class BasicBullet extends Bullet {

    public BasicBullet(float x, float y, Texture texture) {
        super(x, y, 32, 32, 1, 0, texture);
    }

    @Override
    public void moveBullet() {

    }
}
