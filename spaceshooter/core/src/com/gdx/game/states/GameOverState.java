package com.gdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gdx.game.Spaceshooter;

public class GameOverState extends AbstractScreen {


    public GameOverState(Spaceshooter game) {
        super(game);
        camera = new OrthographicCamera();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
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

    }
}
