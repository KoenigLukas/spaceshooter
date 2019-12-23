package com.gdx.game.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState extends State {

    private BitmapFont text;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        camera = new OrthographicCamera();
        text = new BitmapFont();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();



        batch.end();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void dispose() {

    }
}
