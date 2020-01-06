package com.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.MysticalMain;

public abstract class AbstractScreen implements Screen {
    protected MysticalMain context;
    protected FitViewport viewport;
    protected World world;
    protected Box2DDebugRenderer box2DDebugRenderer;

    public AbstractScreen(final MysticalMain context) {
        this.context = context;
        viewport = context.getScreenViewport();
        world = context.getWorld();
        box2DDebugRenderer = context.getBox2DDebugRenderer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
