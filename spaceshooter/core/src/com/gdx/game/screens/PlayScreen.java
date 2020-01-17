package com.gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.Spaceshooter;
import com.gdx.game.bullets.BasicBullet;
import com.gdx.game.bullets.Bullet;
import com.gdx.game.bullets.HomingBullet;
import com.gdx.game.bullets.ShotgunBullet;
import com.gdx.game.collectibles.Collectible;
import com.gdx.game.collectibles.weapons.BasicWeapon;
import com.gdx.game.collectibles.weapons.RocketLauncher;
import com.gdx.game.collectibles.weapons.ShotGun;
import com.gdx.game.collectibles.weapons.Weapon;
import com.gdx.game.enemys.BasicEnemy;
import com.gdx.game.enemys.Enemy;
import com.gdx.game.enemys.boss.FirstBoss;
import com.gdx.game.enemys.boss.SecondBoss;
import com.gdx.game.holster.WeaponHolster;
import com.gdx.game.obstacles.Obstacle;
import com.gdx.game.obstacles.SpaceRock;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

import java.util.Iterator;
import java.util.LinkedList;

public class PlayScreen extends AbstractScreen {

    private Texture shipImg;
    private Texture basicBulletImg;
    private Texture basicEnemyImg;
    private Texture background;
    private Texture shotgunBulletImg;
    private Texture homingBulletImg;
    private Texture shotGunWeaponImg;
    private Texture spaceRockImg;
    private Texture rocketLauncherWeaponImg;
    private Texture satelliteImg;
    private Texture firstBossImg;
    private Texture secondBossImg;

    private BitmapFont scoreBoardFont;
    private BitmapFont weaponHolsterFont;

    private SpaceShip ship;

    private boolean firstBossSpawn;
    private boolean firstBossAlive;

    private boolean secondBossSpawn;
    private boolean secondBossAlive;

    private int backroundSpeed = 0;

    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Enemy> enemys = new LinkedList<>();
    private LinkedList<Collectible> collectibles = new LinkedList<>();
    private LinkedList<Obstacle> obstacles = new LinkedList<>();
    private Obstacle.ObstacleType[] obstacleTypes;


    private ParticleEffect explosionEffect;

    private long lastBulletSpawn;
    private long lastEnemySpawn;
    private long lastObstacleSpawn;
    private long lastWeaponSwitch;
    private long lastShotGunSpawned;
    private long lastRocketLauncherSpawned;
    private long lastExplosion;
    private long lastBossSpawned;
    private long lastsecondBossSpawned;

    private Integer score = 0;

    private WeaponHolster weaponHolster;
    private FirstBoss firstBoss;
    private SecondBoss secondBoss;

    public PlayScreen(Spaceshooter game) {
        super(game);

        background = new Texture("backgroundnew.jpg");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        shipImg = new Texture("spaceship.png");
        basicBulletImg = new Texture("bullet.png");
        basicEnemyImg = new Texture("enemy.png");
        shotgunBulletImg = new Texture("bullet.png");
        homingBulletImg = new Texture("homingbullet.png");
        shotGunWeaponImg = new Texture("shotgun.png");
        spaceRockImg = new Texture("spacerock.png");
        rocketLauncherWeaponImg = new Texture("rocketlauncher.png");
        satelliteImg = new Texture("satellite.png");
        firstBossImg = new Texture("FirstBoss.png");
        secondBossImg = new Texture("SecondBoss.png");

        firstBoss = null;

        firstBossSpawn = false;
        firstBossAlive = false;

        secondBoss = null;

        secondBossSpawn = false;
        secondBossAlive = false;

        obstacleTypes = Obstacle.ObstacleType.class.getEnumConstants();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        scoreBoardFont = new BitmapFont();
        weaponHolsterFont = new BitmapFont();

        weaponHolster = new WeaponHolster(new BasicWeapon());

        ship = new BasicSpaceShip(20, (camera.viewportHeight / 2), shipImg);

        explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
        explosionEffect.setPosition(-1000, -1000);

        lastExplosion = 0;
        lastBossSpawned = 0;

        lastsecondBossSpawned = 0;
    }

    @Override
    protected void handleInput() {
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

        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            spawnCollectable(Collectible.CollectibleType.SHOTGUN, 100);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            spawnCollectable(Collectible.CollectibleType.ROCKETLAUNCHER, 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            score += 10;
        }

        if (ship.x < 0) ship.x = 0;
        if (ship.y > camera.viewportHeight - ship.height) ship.y = camera.viewportHeight - ship.height;
        if (ship.y < 0) ship.y = 0;
        if (ship.x > camera.viewportWidth - ship.width) ship.x = camera.viewportWidth - ship.width;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, 0, 0, backroundSpeed, 0, (int) camera.viewportWidth, (int) camera.viewportHeight);
        backroundSpeed += 2;

        batch.draw(shipImg, ship.x, ship.y);


        if (score == 500) firstBossSpawn = true;

        if(score == 1500) secondBossSpawn = true;


        if (TimeUtils.millis() - lastBossSpawned > 900000000 && firstBossSpawn) {
            lastBossSpawned = TimeUtils.millis();
            spawnEnemy(Enemy.EnemyType.FIRSTBOSS);
        }

        if (TimeUtils.millis() - lastsecondBossSpawned > 900000000 && secondBossSpawn) {
            lastsecondBossSpawned = TimeUtils.millis();
            spawnEnemy(Enemy.EnemyType.SECONDBOSS);
        }

        if (TimeUtils.nanoTime() - lastEnemySpawn > 1000000000 - score * 10000 && !firstBossAlive && !secondBossAlive)
            spawnEnemy(Enemy.EnemyType.BASIC);

        if (TimeUtils.millis() - lastObstacleSpawn > 4000 - score * 0.002) spawnObstacle();


        if (score % 200 == 0 && (TimeUtils.millis() - lastShotGunSpawned > 3000) && score > 0) {
            lastShotGunSpawned = TimeUtils.millis();
            spawnCollectable(Collectible.CollectibleType.SHOTGUN, 50);
        }

        if (score % 400 == 0 && (TimeUtils.millis() - lastRocketLauncherSpawned > 3000) && score > 0) {
            lastRocketLauncherSpawned = TimeUtils.millis();
            spawnCollectable(Collectible.CollectibleType.ROCKETLAUNCHER, 10);
        }

        moveEnemy();
        moveBullets(bullets);    //Bullets vom Ship
        checkBulletImpact(bullets);

        if (firstBossAlive && firstBoss != null) {
            bullets.addAll(firstBoss.getBullets());
            moveBullets(firstBoss.getBullets());
            checkBulletImpact(firstBoss.getBullets());   //Bullets vom ersten Boss
        }

        if (secondBossAlive && secondBoss != null) {
            bullets.addAll(secondBoss.getBullets());
            moveBullets(secondBoss.getBullets());
            checkBulletImpact(secondBoss.getBullets());   //Bullets vom zweiten Boss
        }

        moveCollectible();
        moveObstacle();
        checkObstacleImpact();
        checkEnemyImpact();
        checkCollectibleImpact();

        for (Bullet bullet : bullets) {
            batch.draw(bullet.getTexture(), bullet.x, bullet.y);
        }
        if (firstBossAlive) {
            for (Bullet bullet : firstBoss.getBullets()) {
                batch.draw(bullet.getTexture(), bullet.x, bullet.y);
            }
        }

        for (Enemy enemy : enemys) {
            batch.draw(enemy.getTexture(), enemy.x, enemy.y);
            if (enemy.getType() == Enemy.EnemyType.FIRSTBOSS)
                enemy.setShip(ship);  //Übergibt das "Player-Ship"
        }
        for (Collectible collectible : collectibles) {
            batch.draw(collectible.getTexture(), collectible.x, collectible.y);
        }
        for (Obstacle obstacle : obstacles) {
            batch.draw(obstacle.getTexture(), obstacle.x, obstacle.y);
        }

        explosionEffect.start();
        explosionEffect.draw(batch, Gdx.graphics.getDeltaTime());

        if (TimeUtils.nanoTime() - lastExplosion > 100000000) explosionEffect.setPosition(-100, -100);

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

//        if(ship.getLifes() == 0) gsm.push(new GameOverState(gsm));

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shipImg.dispose();
        basicBulletImg.dispose();
        basicEnemyImg.dispose();
        shotgunBulletImg.dispose();
        shotGunWeaponImg.dispose();
        homingBulletImg.dispose();
        background.dispose();
        rocketLauncherWeaponImg.dispose();
        spaceRockImg.dispose();
        satelliteImg.dispose();
    }

    private void checkEnemyImpact() {
        Iterator<Enemy> it = enemys.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (enemy.overlaps(ship)) {
                enemy.deductLife(1);
                if (enemy.getLifes() == 0) it.remove();
                if (enemy.getType() == Enemy.EnemyType.FIRSTBOSS) firstBossAlive = false;
                ship.deductLife(1);
                score += 10;
            } else if (enemy.x <= 0) {
                it.remove();
                ship.deductLife(1);
            }
        }
    }

    private void createExplosion(float x, float y) {
        explosionEffect.setPosition(x, y);
        lastExplosion = TimeUtils.nanoTime();
    }

    private void moveBullets(LinkedList<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            bullet.moveBullet();
        }
    }

    private void moveObstacle() {
        for (Obstacle obstacle : obstacles) {
            obstacle.moveObstacle();
        }
    }

    private void spawnBullet(Bullet.BulletType type) {
        lastBulletSpawn = TimeUtils.nanoTime();
        if (type == Bullet.BulletType.BASIC) {
            bullets.add(new BasicBullet(ship.x, ship.y, basicBulletImg));
        } else if (type == Bullet.BulletType.SHOTGUN) {
            bullets.add(new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.STRAIGHT));
            bullets.add(new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALUP));
            bullets.add(new ShotgunBullet(ship.x, ship.y, shotgunBulletImg, ShotgunBullet.BulletDirection.DIAGONALDOWN));
        } else if (type == Bullet.BulletType.HOMINGBULLET) {
            bullets.add(new HomingBullet(ship.x, ship.y, Bullet.BulletType.HOMINGBULLET, homingBulletImg, enemys));
        }
    }

    private void spawnObstacle() {
        Obstacle.ObstacleType type = obstacleTypes[(int) (Math.random() * 10) % obstacleTypes.length];

        Obstacle obstacle = null;
        if (type == Obstacle.ObstacleType.ROCK)
            obstacle = new SpaceRock(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), spaceRockImg);
        if (type == Obstacle.ObstacleType.SATELLITE)
            obstacle = new SpaceRock(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), satelliteImg);
        if (obstacle != null) obstacles.add(obstacle);
        lastObstacleSpawn = TimeUtils.millis();
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
        if (type == Enemy.EnemyType.FIRSTBOSS) {
            firstBossSpawn = false;
            firstBossAlive = true;
            FirstBoss fb = new FirstBoss(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), firstBossImg);
            firstBoss = fb;
            enemys.add(fb);
        }
        if (type == Enemy.EnemyType.SECONDBOSS) {
            secondBossSpawn = false;
            secondBossAlive = true;
            SecondBoss sb = new SecondBoss(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), secondBossImg);
            secondBoss = sb;
            enemys.add(sb);
        }
    }

    private void checkBulletImpact(LinkedList<Bullet> bullets) {
        Iterator<Bullet> bit = bullets.iterator();
        while (bit.hasNext()) {
            Bullet tmpbullet = bit.next();
            if (tmpbullet.x > camera.viewportWidth || tmpbullet.y > camera.viewportHeight || tmpbullet.y < 0)
                bit.remove();
            Iterator<Enemy> eit = enemys.iterator();
            while (eit.hasNext()) {
                Enemy tmpenemy = eit.next();
                if (tmpbullet.getDirection() != Bullet.BulletDirection.BACK){
                    if (tmpbullet.overlaps(tmpenemy)) {
                        tmpenemy.deductLife(tmpbullet.getDamage());
                        try {
                            bit.remove();
                        } catch (Exception e) {
                            System.out.println("blos");
                        }
                        if (tmpenemy.getLifes() == 0) {
                            if (tmpenemy.getType() == Enemy.EnemyType.FIRSTBOSS) {
                                firstBossAlive = false;
                            }
                            if(tmpenemy.getType() == Enemy.EnemyType.SECONDBOSS){
                                secondBossAlive = false;
                            }
                            eit.remove();
                            createExplosion(tmpenemy.x, tmpenemy.y);
                            score += 10;
                        }
                    }
                }else{
                    if(tmpbullet.overlaps(ship)){
                        try {
                            ship.deductLife(tmpbullet.getDamage());
                            bit.remove();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void spawnCollectable(Collectible.CollectibleType type) {

    }

    private void spawnCollectable(Collectible.CollectibleType type, int ammo) {
        if (type == Collectible.CollectibleType.SHOTGUN) {
            Weapon weapon = new ShotGun(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), shotGunWeaponImg, ammo);
            collectibles.add(weapon);
        } else if (type == Collectible.CollectibleType.ROCKETLAUNCHER) {
            Weapon weapon = new RocketLauncher(camera.viewportWidth, (MathUtils.random(0, camera.viewportHeight - 64)), rocketLauncherWeaponImg, ammo);
            collectibles.add(weapon);
        }
    }

    private void moveCollectible() {
        for (Collectible collectible : collectibles) {
            collectible.moveEntity();
        }
    }

    private void checkCollectibleImpact() {
        Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            Collectible collectible = it.next();
            if (collectible.overlaps(ship)) {
                if (Weapon.class.isAssignableFrom(collectible.getClass())) {
                    weaponHolster.collectWeapon((Weapon) collectible);
                    it.remove();
                }
            }
        }
    }

    private void checkObstacleImpact() {
        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (obstacle.overlaps(ship)) ship.deductLife(1);
        }
    }
}

