package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.enemys.Enemy;

import java.util.Iterator;
import java.util.LinkedList;

public class HomingBullet extends Bullet {

    LinkedList<Enemy> enemyList = new LinkedList<>();
    private boolean targetaquired=false;
    Iterator<Enemy> enemyIterator;
    Enemy enemy;

    public HomingBullet(float x, float y, BulletType type, Texture texture, LinkedList<Enemy> enemyList) {
        super(x, y, 32, 32, 5, 0, type, texture);
        this.enemyList = enemyList;
        enemy=null;

    }

    @Override
    public void moveBullet() {

        enemyIterator= enemyList.iterator();
        float proportion=1;
        if(enemyIterator.hasNext()&&(!targetaquired)) {                             //Ersten Enemy zuweisen
            enemy = enemyIterator.next();
        }

        while (enemy.isTargeted()&&enemyIterator.hasNext()&&(!targetaquired)){      //Check if enemy is targetted
            enemy=enemyIterator.next();
        }

        if(!enemy.isTargeted()) {                                                   //Target statements setzen
            targetaquired = true;
            enemy.setTargeted(true);
        }

        x+=Gdx.graphics.getDeltaTime()*enemy.getSpeed()*100;                          //Move x
        if(enemy.y>y){
            y+=Gdx.graphics.getDeltaTime()*enemy.getSpeed()*120;                    //Move y
        }else if(enemy.y<y-10){
            y-=Gdx.graphics.getDeltaTime()*enemy.getSpeed()*100;                    //Move y
        }








       /* float dist = Float.MAX_VALUE;
        Iterator<Enemy> it = enemyList.iterator();
        Enemy target = null;
        while(it.hasNext()){
            Enemy enemy = it.next();
            float tmp = (x-enemy.x) + (y-enemy.y);
            if(tmp < dist){
                dist = tmp;
                target = enemy;
            }
        }
        float ratio = (target.y/target.x);
        System.out.println(ratio);
        x += 100* Gdx.graphics.getDeltaTime() + movSpeedFactor;
        y += (100*ratio*10)* Gdx.graphics.getDeltaTime() + movSpeedFactor;
        */
    }
}
