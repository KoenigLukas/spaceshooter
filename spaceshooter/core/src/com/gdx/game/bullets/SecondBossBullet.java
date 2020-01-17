package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

public class SecondBossBullet extends Bullet {

    SpaceShip ship;
    float lastTargetTime;
    public SecondBossBullet(float x, float y, Texture texture, SpaceShip ship) {
        super(x, y, 32, 32, 1, 1, BulletType.BASIC, texture, BulletDirection.BACK);
        this.ship=ship;
        lastTargetTime=0;
    }

    @Override
    public void moveBullet() {
        this.x-=40*Gdx.graphics.getDeltaTime();

        if(TimeUtils.millis()-lastTargetTime>5) {
            lastTargetTime=TimeUtils.millis();
            if (ship.getY() > y) {
                y+=1;
            } else {
                y-=1;
            }
        }
    }
}
