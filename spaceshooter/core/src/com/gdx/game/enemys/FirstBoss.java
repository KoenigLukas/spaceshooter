package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.bullets.BasicBullet;
import com.gdx.game.bullets.BossBullet;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

import java.util.LinkedList;
import java.util.Random;

public class FirstBoss extends Enemy {
    private Random r;
    private int rand;
    private long lastRand;
    private int randcount;
    private SpaceShip ship;
    private LinkedList<Bullet> bullets;
    private long lastBulletSpawn;
    private Texture bulletImg;

    public FirstBoss(float x, float y, Texture texture) {
        super(x, y, 128, 128, 0, 10, EnemyType.FIRSTBOSS, texture);
        r = new Random();
        rand=0;
        lastRand=0;
        randcount=0;
        bullets = new LinkedList<>();
        lastBulletSpawn=0;
        bulletImg = new Texture("bullet.png");
        ship = new BasicSpaceShip(0,0,bulletImg);
    }

    @Override
    public void moveEnemy(int score) {
        speed = (300 * Gdx.graphics.getDeltaTime() + movSpeedFactor + (score / 350));
        if(x>900) {
            this.x -= speed;
        }else {
            if(TimeUtils.millis()-lastRand>600) {
                int randsave = rand;
                rand = r.nextInt(2);     //Get new Random movements every 600ms

                if(rand==randsave)randcount++;   //3 mal selbe Richtung -> Richtung changen
                else randcount=0;

                if(randcount>2){
                    if(rand==1)rand=0;
                    else rand=1;
                }

                lastRand=TimeUtils.millis();
            }
            if(rand>0&&this.y<600){    //Random auf und ab
                this.y+=speed;
            }else if(this.y>0){
                this.y-=speed;
            }
        }

        for (int i = 0; i <25 ; i++) {
            if((int)ship.y+i==(int)this.y) shootBullet();     //Wenn der Boss auf der gleichen Hähe wie der Ship ist -> Shoot  +/- 10
        }
    }

    @Override
    public void shootBullet() {
        if(TimeUtils.nanoTime()-lastBulletSpawn>500000000) {
            lastBulletSpawn = TimeUtils.nanoTime();
            bullets.add(new BossBullet(this.x, this.y, bulletImg));
        }
    }

    @Override
    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

}