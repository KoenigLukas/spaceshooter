package com.gdx.game.collectibles;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Collectible extends Rectangle {

    private Texture texture;

    public Collectible(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height);
        this.texture = texture;
    }

    public abstract void moveEntity();

    public Texture getTexture() {
        return texture;
    }

    public enum CollectibleType {
        SHOTGUN,
        ROCKETLAUNCHER,
    }
}
