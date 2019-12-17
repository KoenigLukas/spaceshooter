package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BasicEnemy extends Enemy {

    public BasicEnemy(float x, float y, Texture texture) {
        super(x, y, 64, 64, 0, 1, EnemyType.BASIC, texture);
    }

    @Override
    public void moveEnemy(int score) {
        this.x -=300* Gdx.graphics.getDeltaTime()+movSpeedFactor+(score/350);
    }

    @Override
    public void shootBullet() {

    }
}
