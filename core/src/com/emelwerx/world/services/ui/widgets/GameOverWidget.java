package com.emelwerx.world.services.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.emelwerx.world.WorldAdapter;
import com.emelwerx.world.services.Assets;
import com.emelwerx.world.services.ui.screens.WorldScreen;
import com.emelwerx.world.services.ui.screens.LeaderboardsScreen;

public class GameOverWidget extends Actor {
    private WorldAdapter adapter;
    private Stage stage;
    private Image image;
    private TextButton retryB, leaderB, quitB;

    public GameOverWidget(WorldAdapter adapter, Stage stage) {
        this.adapter = adapter;
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
                adapter.setScreen(new WorldScreen(adapter));
            }
        });
        leaderB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adapter.setScreen(new LeaderboardsScreen(adapter));
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
        super.setSize(WorldAdapter.VIRTUAL_WIDTH, WorldAdapter.VIRTUAL_HEIGHT);
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