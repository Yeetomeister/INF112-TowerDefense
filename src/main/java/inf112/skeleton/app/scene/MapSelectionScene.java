package inf112.skeleton.app.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import inf112.skeleton.app.util.GameConstants;
import inf112.skeleton.app.util.GameSettings;
import inf112.skeleton.app.util.MusicManager;

public class MapSelectionScene extends AbstractGameScene {
    private Stage stage;
    private Skin uimenuskin;

    /**
     * Creates the scene where user is selecting which map to be played
     * @param game the game
     */
    public MapSelectionScene(Game game) {
        super(game);
    }

    private void build() {
        uimenuskin = new Skin(Gdx.files.internal(GameConstants.SKIN_UI),
                new TextureAtlas(GameConstants.TEXTURE_ATLAS_UI));

        Table layerBackground = buildBg();
        Table layerControls = buildControls();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(GameConstants.UI_WIDTH, GameConstants.UI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerControls);

    }

    private Table buildBg() {
        Table layer = new Table();
        layer.setFillParent(true);
        // + Background
        Image image = new Image(uimenuskin, "background");
        image.setScaling(Scaling.stretch);
        image.setFillParent(true);
        layer.add(image).expand().fill();
        return layer;
    }


    private Table buildControls() {
        Table layer = new Table();
        // + map one Button
        //BUTTONS
        Button mapOneButton = new Button(uimenuskin, "play");
        layer.add(mapOneButton);
        mapOneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMapClicked(1);
            }
        });
        // + map two Button
        Button mapTwoButton = new Button(uimenuskin, "play");
        layer.add(mapTwoButton);
        mapTwoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMapClicked(2);
            }
        });
        layer.row();
        //+ back button
        Button backButton = new Button(uimenuskin, "back");
        layer.add(backButton).colspan(2).center();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onBackClicked();
            }
        });
        return layer;
    }

    private void onBackClicked() {
        game.setScreen(new MenuScene(game));
    }

    private void onMapClicked (int mapNumber) {
        game.setScreen(new PlayScene(game, mapNumber));
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void hide () {
        stage.dispose();
        uimenuskin.dispose();
    }
    @Override
    public void show () {
        GameSettings preference = GameSettings.instance;
        stage = new Stage(new StretchViewport(GameConstants.UI_WIDTH, GameConstants.UI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        build();
        preference.load();
        MusicManager.changeMusicVolume();
        if(!GameSettings.getMusic()) {
            MusicManager.stopCurrentMusic();
        } else {
            MusicManager.play("menumusic.ogg", true);
        }

    }
    @Override public void pause () { }
}