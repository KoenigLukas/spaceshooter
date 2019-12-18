package com.gdx.game.enemys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public abstract class Enemy extends Rectangle {

    protected float movSpeedFactor;
    protected int lifes;
    private Texture texture;
    private EnemyType type;
    private boolean targeted = false;
    protected float speed;

    public Enemy(float x, float y, float width, float height, float movSpeedFactor, int lifes, EnemyType type, Texture texture) {
        super(x, y, width, height);
        this.movSpeedFactor = movSpeedFactor;
        this.lifes = lifes;
        this.texture = texture;
        this.type = type;
    }

    public abstract void moveEnemy(int score);
    public abstract void shootBullet();

    public void deductLife(int damage){
        lifes -= damage;
        if(lifes < 0) lifes = 0;
    }

    public float getMovSpeedFactor() {
        return movSpeedFactor;
    }

    public int getLifes() {
        return lifes;
    }

    public Texture getTexture() {
        return texture;
    }

    public EnemyType getType() {
        return type;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }


    public float getSpeed() {
        return speed;
    }
}
