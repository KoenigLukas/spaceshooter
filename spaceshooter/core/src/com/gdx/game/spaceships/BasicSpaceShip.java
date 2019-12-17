package com.gdx.game.spaceships;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.Bullet;

public class BasicSpaceShip extends SpaceShip {

    public BasicSpaceShip(float x, float y, Texture texture) {
        super(x, y, 32, 32, 0, 30, texture);
    }

    @Override
    public void shootBullet(Bullet bullet) {

    }
}
