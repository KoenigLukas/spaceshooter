package com.gdx.game.holster;

import com.gdx.game.bullets.Bullet;
import com.gdx.game.collectables.weapons.Weapon;

public class WeaponHolster {

    private Weapon weapon1 = null;
    private Weapon weapon2 = null;
    private boolean selection = true;

    public WeaponHolster(Weapon weapon1) {
        this.weapon1 = weapon1;
    }

    public Weapon getActive() {
        return selection ? weapon1 : weapon2;
    }

    public Weapon getInActive() {
        return selection ? weapon2 : weapon1;
    }

    public void switchSelection() {
        if (weapon2 != null) selection = !selection;
    }

    public void collectWeapon(Weapon weapon) {
        if (weapon.getBulletType() == weapon1.getBulletType()) {
            weapon1.collectAmmo(weapon.getAmmo());
        } else if (weapon2 == null) {
            weapon2 = weapon;
            switchSelection();
        } else if (weapon.getBulletType() == weapon2.getBulletType()) {
            weapon2.collectAmmo(weapon.getAmmo());
        } else if (weapon2.getBulletType() == Bullet.BulletType.BASIC) {
            weapon2 = weapon;
            switchSelection();
        } else if (weapon1.getBulletType() == Bullet.BulletType.BASIC) {
            weapon1 = weapon;
            switchSelection();
        } else {
            if (!selection) weapon2 = weapon;
            else weapon1 = weapon;
        }
    }

    public boolean hasSecondary() {
        return weapon2 != null;
    }

    public void setWeapon1(Weapon weapon1) {
        this.weapon1 = weapon1;
    }

    public void setWeapon2(Weapon weapon2) {
        this.weapon2 = weapon2;
    }
}
