package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.bullets.*;
import com.gdx.game.collectables.Collectable;
import com.gdx.game.collectables.Weapon;
import com.gdx.game.enemys.BasicEnemy;
import com.gdx.game.enemys.Enemy;
import com.gdx.game.enemys.EnemyType;
import com.gdx.game.obstacles.Obstacle;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

import java.util.Iterator;
import java.util.LinkedList;

public class Spaceshooter extends ApplicationAdapter {
    SpriteBatch batch;

    private Texture shipImg;
    private Texture basicBulletImg;
    private Texture basicEnemyImg;
    private Texture background;
    private Texture shotgunBulletImg;
    private Texture homingBulletImg;
    private Texture shotGunWeaponImg;
    private Texture spaceRockImg;

    BitmapFont scoreBoard;

    SpaceShip ship;

    int backroundSpeed = 0;

    LinkedList<Bullet> bullets = new LinkedList<>();
    LinkedList<Enemy> enemys = new LinkedList<>();
    LinkedList<Weapon> weapons = new LinkedList<>();
    LinkedList<Collectable> collectables = new LinkedList<>();
    LinkedList<Obstacle> obstacles = new LinkedList<>();

    Iterator<Weapon> weaponIterator = weapons.iterator();

    long lastBulletSpawn;
    long lastEnemySpawn;
    long lastObstacleSpawn;

    Integer score = 0;

    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        shipImg = new Texture("spaceship.png");
        basicBulletImg = new Texture("bullet.png");
        basicEnemyImg = new Texture("enemy.png");
        shotgunBulletImg = new Texture("bullet.png");
        homingBulletImg = new Texture("homingbullet.png");
        shotGunWeaponImg = new Texture("shotgun.png");
        spaceRockImg = new Texture("spaceship.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        scoreBoard = new BitmapFont();

        ship = new BasicSpaceShip(20, (camera.viewportHeight / 2), shipImg);

    }

    @Override
    public void render() {
        handleInput();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, 0, 0, backroundSpeed, 0, (int) camera.viewportWidth, (int) camera.viewportHeight);
        backroundSpeed += 2;

        batch.draw(shipImg, ship.x, ship.y);

        if (TimeUtils.nanoTime() - lastEnemySpawn > 1000000000 - score*10000) spawnEnemy(EnemyType.BASIC);
        if(TimeUtils.nanoTime() - lastObstacleSpawn > 1000000000 - score*10000) spawnObstacle();

        moveEnemy();
        moveBullets();
        checkBulletImpact();
        checkEnemyHit();

        for (Bullet bullet : bullets) {
            batch.draw(bullet.getTexture(), bullet.x, bullet.y);
        }
        for (Enemy enemy : enemys) {
            batch.draw(enemy.getTexture(), enemy.x, enemy.y);
        }

        String text;
        text = "Score: " + score + " Lifes: " + ship.getLifes();
        scoreBoard.draw(batch, text, 10, camera.viewportHeight - 10);
        scoreBoard.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreBoard.getData().setScale(3, 3);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ship.y -= 650 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ship.y += 650 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.x += 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.x -= 600 * Gdx.graphics.getDeltaTime() + ship.getMovSpeedFactor();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (TimeUtils.nanoTime() - lastBulletSpawn > BulletType.HOMINGBULLET.getDelay()) spawnBullet(BulletType.HOMINGBULLET);
        }

        if (ship.x < 0) ship.x = 0;
        if (ship.y > camera.viewportHeight - ship.height) ship.y = camera.viewportHeight - ship.height;
        if (ship.y < 0) ship.y = 0;
        if (ship.x > camera.viewportWidth - ship.width) ship.x = camera.viewportWidth - ship.width;
    }

    private void checkEnemyHit() {
        Iterator<Enemy> it = enemys.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (enemy.overlaps(ship)) {
                it.remove();
                ship.deductLife(1);
                score += 10;
            } else if (enemy.x <= 0) {
                it.remove();
                ship.deductLife(1);
            }
        }
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.moveBullet();
        }
    }

    private void spawnBullet(BulletType type) {
        lastBulletSpawn = TimeUtils.nanoTime();
        if (type == BulletType.BASIC) {
            Bullet bullet = new BasicBullet(ship.x, ship.y, basicBulletImg);
            bullets.add(bullet);
        } else if (type == BulletType.SHOTGUN) {
            Bullet bullet = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.STRAIGHT);
            Bullet bullet2 = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALUP);
            Bullet bullet3 = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALDOWN);
            bullets.add(bullet);
            bullets.add(bullet2);
            bullets.add(bullet3);
        } else if(type == BulletType.HOMINGBULLET){
            Bullet bullet = new HomingBullet(ship.x,ship.y,BulletType.HOMINGBULLET,homingBulletImg,enemys);
            bullets.add(bullet);
        }
    }

    private void moveEnemy() {
        for (Enemy enemy : enemys) {
            enemy.moveEnemy(score);
        }
    }

    private void spawnEnemy(EnemyType type) {
        lastEnemySpawn = TimeUtils.nanoTime();
        if (type == EnemyType.BASIC) {
            Enemy enemy = new BasicEnemy(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), basicEnemyImg);
            enemys.add(enemy);
        }
    }

    private void spawnObstacle(){
        lastObstacleSpawn=TimeUtils.nanoTime();
    }

    private void checkBulletImpact() {
        Iterator<Bullet> bit = bullets.iterator();
        while (bit.hasNext()) {
            Bullet tmpbullet = bit.next();
            if (tmpbullet.x > camera.viewportWidth || tmpbullet.y > camera.viewportHeight || tmpbullet.y < 0)
                bit.remove();
            Iterator<Enemy> eit = enemys.iterator();
            while (eit.hasNext()) {
                Enemy tmpenemy = eit.next();
                if (tmpbullet.overlaps(tmpenemy)) {
                    tmpenemy.deductLife(tmpbullet.getDamage());
                    bit.remove();
                    if (tmpenemy.getLifes() == 0) {
                        eit.remove();
                        score += 10;
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
        shotgunBulletImg.dispose();
        shotGunWeaponImg.dispose();
        homingBulletImg.dispose();
        background.dispose();
    }


}
