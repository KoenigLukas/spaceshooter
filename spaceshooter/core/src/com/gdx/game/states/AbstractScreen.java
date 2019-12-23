package com.gdx.game.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.gdx.game.Spaceshooter;
import com.gdx.game.spaceships.SpaceShip;

public abstract class AbstractScreen implements Screen {
    protected OrthographicCamera camera;
    protected Spaceshooter game;
    protected SpriteBatch batch;

    public AbstractScreen(Spaceshooter game) {
        this.game = game;
        this.batch = game.batch;
    }

    protected abstract void handleInput();
}
