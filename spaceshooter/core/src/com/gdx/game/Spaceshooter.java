package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.spaceships.BasicSpaceShip;
import com.gdx.game.spaceships.SpaceShip;

import java.awt.*;

public class Spaceshooter extends ApplicationAdapter {
	SpriteBatch batch;
	Texture shipImg;
	SpaceShip ship;

	private OrthographicCamera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shipImg = new Texture("spaceship.png");

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
		batch.draw(shipImg, ship.x, ship.y);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		shipImg.dispose();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			ship.y -= 500 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			ship.y += 500 * Gdx.graphics.getDeltaTime();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			ship.x += 500 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			ship.x -= 500 * Gdx.graphics.getDeltaTime();
		}
	}
}
