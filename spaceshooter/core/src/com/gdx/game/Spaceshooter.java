package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.bullets.BasicBullet;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.enemys.BasicEnemy;
import com.gdx.game.enemys.Enemy;
import com.gdx.game.enemys.EnemyType;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

import java.util.Iterator;
import java.util.LinkedList;

public class Spaceshooter extends ApplicationAdapter {
    SpriteBatch batch;

    Texture shipImg;
    Texture basicBulletImg;
    Texture basicEnemyImg;
	private Texture background;

    SpaceShip ship;
	
	int srcx = 0;

    LinkedList<Bullet> bullets = new LinkedList<>();
    LinkedList<Enemy> enemys = new LinkedList<>();

    long lastBulletSpawn;
    long lastEnemySpawn;

    Integer score;

    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
		background = new Texture("background.png");
		background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        shipImg = new Texture("spaceship.png");
        basicBulletImg = new Texture("bullet.png");
        basicEnemyImg = new Texture("enemy.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        ship = new BasicSpaceShip(20, (camera.viewportHeight / 2), shipImg);

    }

    @Override
    public void render() {
        handleInput();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
		batch.draw(background,0, 0, srcx, 0, (int)camera.viewportWidth , (int)camera.viewportHeight);
		srcx +=2;
        batch.draw(shipImg, ship.x, ship.y);

        if (TimeUtils.nanoTime() - lastEnemySpawn > 1000000000) spawnEnemy(EnemyType.BASIC);

        moveEnemy();
        moveBullets();
        checkBulletImpact();

        for (Bullet bullet : bullets) {
            batch.draw(bullet.getTexture(), bullet.x, bullet.y);
        }
        for (Enemy enemy : enemys) {
            batch.draw(enemy.getTexture(), enemy.x, enemy.y);
        }

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ship.y -= 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ship.y += 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.x += 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.x -= 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (TimeUtils.nanoTime() - lastBulletSpawn > 300000000) spawnBullet();
        }
		if (ship.x<0) ship.x=0;
		if (ship.y>camera.viewportHeight-ship.height) ship.y=camera.viewportHeight-ship.height;
		if (ship.y<0) ship.y=0;
		if (ship.x>camera.viewportWidth- ship.width) ship.x=camera.viewportWidth- ship.width;
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.moveBullet();
        }
    }

    private void spawnBullet() {
        lastBulletSpawn = TimeUtils.nanoTime();
        Bullet bullet = new BasicBullet(ship.x, ship.y, basicBulletImg);
        bullets.add(bullet);
    }

    private void moveEnemy() {
        for (Enemy enemy : enemys) {
            enemy.moveEnemy();
        }
    }

    private void spawnEnemy(EnemyType type) {
        lastEnemySpawn = TimeUtils.nanoTime();
        if (type == EnemyType.BASIC) {
            Enemy enemy = new BasicEnemy(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 32)), basicEnemyImg);
            enemys.add(enemy);
        }
    }

    private void checkBulletImpact() {
        Iterator<Bullet> bit = bullets.iterator();
        while (bit.hasNext()) {
            Bullet tmpbullet = bit.next();
            Iterator<Enemy> eit = enemys.iterator();
            while (eit.hasNext()) {
                Enemy tmpenemy = eit.next();
                if (tmpbullet.overlaps(tmpenemy)) {
                    tmpenemy.deductLife(tmpbullet.getDamage());
                    bit.remove();
                    if (tmpenemy.getLifes() == 0) {
                        eit.remove();
                    }
                }
            }
        }
    }



    @Override
    public void dispose() {
        batch.dispose();
        shipImg.dispose();
        basicBulletImg.dispose();
        basicEnemyImg.dispose();
    }


}
