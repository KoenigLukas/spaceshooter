package com.gdx.game.bullets;

public enum BulletType {
    BASIC(500000000),
    SHOTGUN(900000000),
    ;

    private int delay;

    BulletType(int delay) {
        this.delay = delay;
    }

    public int getDelay(){
        return this.delay;
    }
}
