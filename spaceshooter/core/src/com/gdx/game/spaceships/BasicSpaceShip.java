package com.gdx.game.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class BasicSpaceShip extends SpaceShip {

    public BasicSpaceShip(float x, float y, Texture texture) {
        super(x, y, 32, 32, 0, 10, texture);
    }

    @Override
    public void moveShip(int direction) {
        if (direction == Input.Keys.UP)
            y += 650 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        if (direction == Input.Keys.DOWN)
            y -= 650 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        if (direction == Input.Keys.LEFT)
            x -= 650 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
        if (direction == Input.Keys.RIGHT)
            x += 650 * Gdx.graphics.getDeltaTime() + movSpeedFactor;
    }
}
