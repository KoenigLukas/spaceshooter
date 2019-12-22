package com.gdx.game.collectibles.weapons;


import com.gdx.game.bullets.Bullet;

public class BasicWeapon extends Weapon {

    public BasicWeapon() {
        super(0, 0, 0, 0, null, Bullet.BulletType.BASIC, 1);
        infiniteAmmo = true;
    }

    @Override
    public void moveEntity() {
    }
}
