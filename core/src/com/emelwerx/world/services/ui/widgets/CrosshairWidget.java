package com.emelwerx.world.services.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.emelwerx.world.services.Settings;


public class CrosshairWidget extends Actor {

    private Image crosshairDot,
            crosshairInnerRing;

    public CrosshairWidget() {
        crosshairDot = new Image(new Texture(Gdx.files.internal("crosshair/crossHairPoint.png")));
        crosshairInnerRing = new Image(new Texture(Gdx.files.internal("crosshair/crossHairInnerRing.png")));
    }

    @Override
    public void act(float delta) {
        if (Settings.isPaused()) return;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (Settings.isPaused()) return;
        crosshairDot.draw(batch, parentAlpha);
        crosshairInnerRing.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        crosshairDot.setPosition(x - 16, y - 16);
        crosshairInnerRing.setPosition(x - 16, y - 16);
        crosshairInnerRing.setOrigin(crosshairInnerRing.getWidth() / 2, crosshairInnerRing.getHeight() / 2);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        crosshairDot.setSize(width * 2, height * 2);
        crosshairInnerRing.setSize(width * 2, height * 2);
    }

}
