package com.gdx.game.collectables;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.BulletType;

public abstract class Weapon extends Collectable {

    private BulletType bulletType;

    public Weapon(float x, float y, float width, float height, Texture texture, BulletType bulletType) {
        super(x, y, width, height, texture);
        this.bulletType = bulletType;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getDelay() {
        return bulletType.getDelay();
    }
}
