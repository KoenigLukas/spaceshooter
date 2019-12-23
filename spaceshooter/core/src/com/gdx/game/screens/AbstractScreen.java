package com.gdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.Spaceshooter;

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
