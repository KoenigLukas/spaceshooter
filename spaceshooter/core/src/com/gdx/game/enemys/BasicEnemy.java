package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.spaceships.SpaceShip;

import java.util.LinkedList;

public class BasicEnemy extends Enemy {

    private SpaceShip ship;

    public BasicEnemy(float x, float y, Texture texture) {
        super(x, y, 64, 64, 0, 1, EnemyType.BASIC, texture);
    }

    @Override
    public void moveEnemy(int score) {
        speed = (300 * Gdx.graphics.getDeltaTime() + movSpeedFactor + (score / 350));
        this.x -= speed;
    }

    @Override
    public void shootBullet() {

    }

    @Override
    public LinkedList<Bullet> getBullets() {
        return null;
    }

    @Override
    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }
}
