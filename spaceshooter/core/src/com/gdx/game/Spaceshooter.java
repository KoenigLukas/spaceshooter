package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.bullets.BasicBullet;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.bullets.HomingBullet;
import com.gdx.game.bullets.ShotgunBullet;
import com.gdx.game.collectables.Collectable;
import com.gdx.game.collectables.weapons.BasicWeapon;
import com.gdx.game.collectables.weapons.RocketLauncher;
import com.gdx.game.collectables.weapons.ShotGun;
import com.gdx.game.collectables.weapons.Weapon;
import com.gdx.game.enemys.BasicEnemy;
import com.gdx.game.enemys.Enemy;
import com.gdx.game.enemys.FirstBoss;
import com.gdx.game.holster.WeaponHolster;
import com.gdx.game.obstacles.Obstacle;
import com.gdx.game.obstacles.SpaceRock;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;
import com.gdx.game.states.GameStateManager;
import com.gdx.game.states.PlayState;

import java.util.Iterator;
import java.util.LinkedList;

public class Spaceshooter extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameStateManager gsm;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new PlayState(gsm));
        Gdx.gl.glClearColor(1, 0, 0, 1);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


}
