package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BasicEnemy extends Enemy {

    public BasicEnemy(float x, float y, Texture texture) {
        super(x, y, 32, 32, 0, 1, EnemyType.BASIC, texture);
    }

    @Override
    public void moveEnemy() {
        this.x -=300* Gdx.graphics.getDeltaTime()+movSpeedFactor;
    }

    @Override
    public void shootBullet() {

    }
}
