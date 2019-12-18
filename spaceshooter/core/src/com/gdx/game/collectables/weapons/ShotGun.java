package com.gdx.game.collectables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.BulletType;

public class ShotGun extends Weapon {

    public ShotGun(float x, float y, Texture texture) {
        super(x, y, 64, 32, texture, BulletType.SHOTGUN);
    }

    @Override
    public void moveEntity() {
        x -= 100 * Gdx.graphics.getDeltaTime();
    }

}
