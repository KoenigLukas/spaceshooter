package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ShotgunBullet extends Bullet {

    BulletDirection direction;

    public enum BulletDirection {
        STRAIGHT,
        DIAGONALUP,
        DIAGONALDOWN,
    }

    public ShotgunBullet(float x, float y, Texture texture, BulletDirection direction) {
        super(x, y, 32, 32, 2, 1, BulletType.SHOTGUN, texture);
        this.direction = direction;
    }

    @Override
    public void moveBullet() {
        if (direction == BulletDirection.STRAIGHT) {
            this.x += 100 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        } else if (direction == BulletDirection.DIAGONALUP) {
            this.x += 100 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
            this.y += 30 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        } else if (direction == BulletDirection.DIAGONALDOWN) {
            this.x += 100 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
            this.y -= 30 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        }
    }

}


