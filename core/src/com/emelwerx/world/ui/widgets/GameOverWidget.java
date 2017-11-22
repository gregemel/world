package com.emelwerx.world.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.WorldCore;
import com.emelwerx.world.ui.screens.WorldScreen;
import com.emelwerx.world.ui.screens.LeaderboardsScreen;

public class GameOverWidget extends Actor {
    private WorldCore game;
    private Stage stage;
    private Image image;
    private TextButton retryB, leaderB, quitB;

    public GameOverWidget(WorldCore game, Stage stage) {
        this.game = game;
        this.stage = stage;
        setWidgets();
        setListeners();
    }

    private void setWidgets() {
        image = new Image(new Texture(Gdx.files.internal("data/gameOver.png")));
        retryB = new TextButton("Retry", Assets.skin);
        leaderB = new TextButton("Leaderboards", Assets.skin);
        quitB = new TextButton("Quit", Assets.skin);
    }

    private void setListeners() {
        retryB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new WorldScreen(game));
            }
        });
        leaderB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardsScreen(game));
            }
        });
        quitB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(0, 0);
        image.setPosition(x, y + 32);
        retryB.setPosition(x - 45, y - 96);
        leaderB.setPosition(x + retryB.getWidth() - 25, y - 96);
        quitB.setPosition(x + retryB.getWidth() + leaderB.getWidth(), y - 96);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(WorldCore.VIRTUAL_WIDTH, WorldCore.VIRTUAL_HEIGHT);
        image.setSize(width, height);
        retryB.setSize(width / 2.5f, height / 2);
        leaderB.setSize(width / 2.5f, height / 2);
        quitB.setSize(width / 2.5f, height / 2);
    }

    public void gameOver() {
        stage.addActor(image);
        stage.addActor(retryB);
        stage.addActor(leaderB);
        stage.addActor(quitB);
        stage.unfocus(stage.getKeyboardFocus());
        Gdx.input.setCursorCatched(false);
    }
}