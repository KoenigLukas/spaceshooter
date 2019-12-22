package com.gdx.game.enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.spaceships.BasicSpaceShip;

import java.util.Random;

public class FirstBoss extends Enemy {
    private Random r;
    private int rand;
    private long lastRand;
    private int randcount;
    private BasicSpaceShip ship;

    public FirstBoss(float x, float y, Texture texture) {
        super(x, y, 128, 128, 0, 10, EnemyType.FIRSTBOSS, texture);
        r = new Random();
        rand=0;
        lastRand=0;
        randcount=0;
    }

    @Override
    public void moveEnemy(int score) {
        speed = (300 * Gdx.graphics.getDeltaTime() + movSpeedFactor + (score / 350));
        if(x>900) {
            this.x -= speed;
        }else {
            if(TimeUtils.millis()-lastRand>600) {
                int randsave = rand;
                rand = r.nextInt(2);                        //Get new Random movements every 600ms

                if(rand==randsave)randcount++;                     //3 mal selbe Richtung -> Richtung changen
                else randcount=0;

                if(randcount>2){
                    if(rand==1)rand=0;
                    else rand=1;
                }

                lastRand=TimeUtils.millis();
            }
            if(rand>0&&this.y<600){                                 //Random auf und ab
                this.y+=speed;
            }else if(this.y>0){
                this.y-=speed;
            }
        }

    }

    @Override
    public void shootBullet() {

    }

    public void renewShip(BasicSpaceShip ship){
        this.ship=ship;
    }


}
