package com.gdx.game.collectibles.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.Bullet;

public class ShotGun extends Weapon {

    public ShotGun(float x, float y, Texture texture, int ammo) {
        super(x, y, 64, 32, texture, Bullet.BulletType.SHOTGUN, ammo);
    }

    @Override
    public void moveEntity() {
        x -= 300 * Gdx.graphics.getDeltaTime();
    }
}
