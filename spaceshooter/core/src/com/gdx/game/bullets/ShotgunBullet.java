package com.gdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ShotgunBullet extends Bullet {

    private int bulletCount;                            //für 3 verschiedene Richtungen zum unterscheiden
    private ShotgunBullet bullet2;
    private ShotgunBullet bullet3;

    public ShotgunBullet(float x, float y, Texture texture, int bulletCount) {
        super(x, y, 32, 32, 2, 1, BulletType.SHOTGUN, texture);
        this.bulletCount=bulletCount;
        if(bulletCount==0){
            bulletCount++;
            bullet2 = new ShotgunBullet(x,y,texture,bulletCount);
            bulletCount++;
            bullet3 = new ShotgunBullet(x,y,texture,bulletCount);
        }
    }

    @Override
    public void moveBullet() {
        this.x += 100 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        System.out.println(bulletCount);
        if(bulletCount==1){
            this.y += 75*Gdx.graphics.getDeltaTime()+movSpeedFactor;
        }else if(bulletCount==2){
            this.y -= 75*Gdx.graphics.getDeltaTime()+movSpeedFactor;
        }
    }


    public ShotgunBullet getBullet2(){
        return bullet2;
    }

    public ShotgunBullet getBullet3(){
        return bullet3;
    }

}
