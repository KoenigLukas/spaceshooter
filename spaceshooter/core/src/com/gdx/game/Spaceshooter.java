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
import com.gdx.game.holster.WeaponHolster;
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
    private Texture rocketLauncherWeaponImg;

    private BitmapFont scoreBoardFont;
    private BitmapFont weaponHolsterFont;

    private SpaceShip ship;

    private int backroundSpeed = 0;

    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Enemy> enemys = new LinkedList<>();
    private LinkedList<Collectable> collectables = new LinkedList<>();
    private LinkedList<Obstacle> obstacles = new LinkedList<>();
    private LinkedList<ParticleEffect> effects = new LinkedList<>();

    private long lastBulletSpawn;
    private long lastEnemySpawn;
    private long lastObstacleSpawn;
    private long lastWeaponSwitch;
    private long lastShotGunSpawned;
    private long lastRocketLauncherSpawned;

    private Integer score = 0;

    private WeaponHolster weaponHolster;

    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("backgroundnew.jpg");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        shipImg = new Texture("spaceship.png");
        basicBulletImg = new Texture("bullet.png");
        basicEnemyImg = new Texture("enemy.png");
        shotgunBulletImg = new Texture("bullet.png");
        homingBulletImg = new Texture("homingbullet.png");
        shotGunWeaponImg = new Texture("shotgun.png");
        spaceRockImg = new Texture("spaceship.png");
        rocketLauncherWeaponImg = new Texture("rocketlauncher.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        scoreBoardFont = new BitmapFont();
        weaponHolsterFont = new BitmapFont();

        weaponHolster = new WeaponHolster(new BasicWeapon());

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

        if (TimeUtils.nanoTime() - lastEnemySpawn > 1000000000 - score * 10000) spawnEnemy(Enemy.EnemyType.BASIC);
        if (TimeUtils.nanoTime() - lastObstacleSpawn > 1000000000 - score * 10000) spawnObstacle();

        if (score % 100 == 0 && (TimeUtils.millis() - lastShotGunSpawned > 3000) && score > 0) {
            lastShotGunSpawned = TimeUtils.millis();
            spawnCollectable(Collectable.CollectableType.SHOTGUN, 50);
        }

        if (score % 400 == 0 && (TimeUtils.millis() - lastRocketLauncherSpawned > 3000) && score > 0) {
            lastRocketLauncherSpawned = TimeUtils.millis();
            spawnCollectable(Collectable.CollectableType.ROCKETLAUNCHER, 10);
        }


        moveEnemy();
        moveBullets();
        moveCollectable();
        checkBulletImpact();
        checkEnemyHit();
        checkCollectableCollision();

        for (Bullet bullet : bullets) {
            batch.draw(bullet.getTexture(), bullet.x, bullet.y);
        }
        for (Enemy enemy : enemys) {
            batch.draw(enemy.getTexture(), enemy.x, enemy.y);
        }
        for (Collectable collectable : collectables) {
            batch.draw(collectable.getTexture(), collectable.x, collectable.y);
        }
        for (ParticleEffect effect : effects) {
            effect.start();
            effect.draw(batch);
        }

        Iterator<ParticleEffect> eit = effects.iterator();

        while(eit.hasNext()){
            ParticleEffect pe = eit.next();
            if(pe.isComplete()){
                eit.remove();
            }
        }

        String scoreBoardText;
        scoreBoardText = "Score: " + score + " Lifes: " + ship.getLifes();
        scoreBoardFont.draw(batch, scoreBoardText, 10, camera.viewportHeight - 10);
        scoreBoardFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreBoardFont.getData().setScale(3, 3);

        String weaponHolsterText;
        weaponHolsterText = "Selected: " + weaponHolster.getActive().getBulletType() + " " + (weaponHolster.getActive().isInfiniteAmmo() ? "Infinite" : weaponHolster.getActive().getAmmo()) +
                " | Secondary: " + (weaponHolster.hasSecondary() ? weaponHolster.getInActive().getBulletType() : "null") + " "
                + ((weaponHolster.hasSecondary()) ? (weaponHolster.getInActive().isInfiniteAmmo() ? "Infinite" : weaponHolster.getInActive().getAmmo()) : "");
        weaponHolsterFont.draw(batch, weaponHolsterText, 30, 30);
        weaponHolsterFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        weaponHolsterFont.getData().setScale(2, 2);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ship.moveShip(Input.Keys.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ship.moveShip(Input.Keys.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.moveShip(Input.Keys.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.moveShip(Input.Keys.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {

            if (TimeUtils.nanoTime() - lastWeaponSwitch > 50000000) weaponHolster.switchSelection();
            lastWeaponSwitch = TimeUtils.nanoTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (weaponHolster.getActive().getAmmo() > 0) {
                if (TimeUtils.nanoTime() - lastBulletSpawn > weaponHolster.getActive().getBulletType().getDelay()) {
                    spawnBullet(weaponHolster.getActive().getBulletType());
                    weaponHolster.getActive().useWeapon();
                }
            } else {
                if (weaponHolster.hasSecondary() && weaponHolster.getInActive().getAmmo() > 0) {
                    weaponHolster.switchSelection();
                    if (TimeUtils.nanoTime() - lastBulletSpawn > weaponHolster.getActive().getBulletType().getDelay()) {
                        spawnBullet(weaponHolster.getActive().getBulletType());
                        weaponHolster.getActive().useWeapon();
                    }
                } else {
                    weaponHolster.setWeapon1(new BasicWeapon());
                    weaponHolster.setWeapon2(new BasicWeapon());
                    if (TimeUtils.nanoTime() - lastBulletSpawn > Bullet.BulletType.BASIC.getDelay())
                        spawnBullet(Bullet.BulletType.BASIC);
                }
            }

        }

        //DEBUG BINDS:
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            spawnCollectable(Collectable.CollectableType.SHOTGUN, 100);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            spawnCollectable(Collectable.CollectableType.ROCKETLAUNCHER, 10);
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

    private void createExplosion(float x, float y){
        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal("explosion.p"),Gdx.files.internal(""));
        effect.setPosition(x, y);
        effects.add(effect);
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.moveBullet();
        }
    }

    private void spawnBullet(Bullet.BulletType type) {
        lastBulletSpawn = TimeUtils.nanoTime();
        if (type == Bullet.BulletType.BASIC) {
            Bullet bullet = new BasicBullet(ship.x, ship.y, basicBulletImg);
            bullets.add(bullet);
        } else if (type == Bullet.BulletType.SHOTGUN) {
            Bullet bullet = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.STRAIGHT);
            Bullet bullet2 = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALUP);
            Bullet bullet3 = new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALDOWN);
            bullets.add(bullet);
            bullets.add(bullet2);
            bullets.add(bullet3);
        } else if (type == Bullet.BulletType.HOMINGBULLET) {
            Bullet bullet = new HomingBullet(ship.x, ship.y, Bullet.BulletType.HOMINGBULLET, homingBulletImg, enemys);
            bullets.add(bullet);
        }
    }

    private void moveEnemy() {
        for (Enemy enemy : enemys) {
            enemy.moveEnemy(score);
        }
    }

    private void spawnEnemy(Enemy.EnemyType type) {
        lastEnemySpawn = TimeUtils.nanoTime();
        if (type == Enemy.EnemyType.BASIC) {
            Enemy enemy = new BasicEnemy(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), basicEnemyImg);
            enemys.add(enemy);
        }
    }

    private void spawnObstacle() {
        lastObstacleSpawn = TimeUtils.nanoTime();
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
                        createExplosion(tmpenemy.x,tmpenemy.y);
                        score += 10;
                    }
                }
            }
        }
    }

    private void spawnCollectable(Collectable.CollectableType type) {

    }

    private void spawnCollectable(Collectable.CollectableType type, int ammo) {
        if (type == Collectable.CollectableType.SHOTGUN) {
            Weapon weapon = new ShotGun(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), shotGunWeaponImg, ammo);
            collectables.add(weapon);
        } else if (type == Collectable.CollectableType.ROCKETLAUNCHER) {
            Weapon weapon = new RocketLauncher(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), rocketLauncherWeaponImg, ammo);
            collectables.add(weapon);
        }
    }

    private void moveCollectable() {
        for (Collectable collectable : collectables) {
            collectable.moveEntity();
        }
    }

    private void checkCollectableCollision() {
        Iterator<Collectable> it = collectables.iterator();
        while (it.hasNext()) {
            Collectable collectable = it.next();
            if (collectable.overlaps(ship)) {
                if (Weapon.class.isAssignableFrom(collectable.getClass())) {
                    weaponHolster.collectWeapon((Weapon) collectable);
                    it.remove();
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
        rocketLauncherWeaponImg.dispose();
        spaceRockImg.dispose();
    }


}
