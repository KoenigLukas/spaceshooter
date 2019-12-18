package com.gdx.game.collectables.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.collectables.Collectable;

import java.util.Objects;

public abstract class Weapon extends Collectable {

    private Bullet.BulletType bulletType;
    protected int ammo;
    protected boolean infiniteAmmo;

    public Weapon(float x, float y, float width, float height, Texture texture, Bullet.BulletType bulletType, int ammo) {
        super(x, y, width, height, texture);
        this.bulletType = bulletType;
        this.ammo = ammo;
    }

    public boolean useWeapon(){
        if (!infiniteAmmo) ammo--;
        return ammo > 0;
    }

    public void collectAmmo(int amount){
        ammo += amount;
    }

    public Bullet.BulletType getBulletType() {
        return bulletType;
    }

    public int getDelay() {
        return bulletType.getDelay();
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean isInfiniteAmmo() {
        return infiniteAmmo;
    }
}
