package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.enemys.Enemy;

import java.util.Iterator;
import java.util.LinkedList;

public class HomingBullet extends Bullet {

    LinkedList<Enemy> enemyList = new LinkedList<>();
    Iterator<Enemy> enemyIterator;
    Enemy enemy;
    private boolean targetaquired = false;

    public HomingBullet(float x, float y, BulletType type, Texture texture, LinkedList<Enemy> enemyList) {
        super(x, y, 32, 32, 5, 0, type, texture, BulletDirection.STRAIGHT);
        this.enemyList = enemyList;
        enemy = null;

    }

    @Override
    public void moveBullet() {

        enemyIterator = enemyList.iterator();
        float proportion = 1;
        if (enemyIterator.hasNext() && (!targetaquired)) {                             //Ersten Enemy zuweisen
            enemy = enemyIterator.next();
        }

        if (enemy != null) {

            while (enemy.isTargeted() && enemyIterator.hasNext() && (!targetaquired)) {      //Check if enemy is targetted
                enemy = enemyIterator.next();
            }

            if (!enemy.isTargeted()) {                                                   //Target statements setzen
                targetaquired = true;
                enemy.setTargeted(true);
            }

            x += Gdx.graphics.getDeltaTime() * enemy.getSpeed() * 100;                          //Move x
            if (enemy.y > y) {
                y += Gdx.graphics.getDeltaTime() * enemy.getSpeed() * 120;                    //Move y
            } else if (enemy.y < y - 10) {
                y -= Gdx.graphics.getDeltaTime() * enemy.getSpeed() * 100;                    //Move y
            }
        } else {
            x += Gdx.graphics.getDeltaTime() * 100;
        }
    }
}
