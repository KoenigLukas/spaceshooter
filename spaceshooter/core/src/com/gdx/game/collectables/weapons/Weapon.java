package com.gdx.game.collectables.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.BulletType;
import com.gdx.game.collectables.Collectable;

public abstract class Weapon extends Collectable {

    private BulletType bulletType;
    protected int ammo;

    public Weapon(float x, float y, float width, float height, Texture texture, BulletType bulletType) {
        super(x, y, width, height, texture);
        this.bulletType = bulletType;
    }

    public boolean useWeapon(){
        ammo--;
        return ammo > 0;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getDelay() {
        return bulletType.getDelay();
    }

    public int getAmmo() {
        return ammo;
    }

}
