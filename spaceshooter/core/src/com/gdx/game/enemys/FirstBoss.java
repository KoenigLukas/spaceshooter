package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class FirstBoss extends Enemy {
    public FirstBoss(float x, float y, Texture texture) {
        super(x, y, 128, 128, 0, 10, EnemyType.FIRSTBOSS, texture);
    }

    @Override
    public void moveEnemy(int score) {
        speed =(300* Gdx.graphics.getDeltaTime()+movSpeedFactor+(score/350));
        this.x -= speed;
    }

    @Override
    public void shootBullet() {

    }


}
