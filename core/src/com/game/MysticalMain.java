package com.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.screen.AbstractScreen;
import com.game.screen.LoadingScreen;
import com.game.screen.ScreenType;

import java.util.EnumMap;

public class MysticalMain extends Game {
	private static final String TAG = MysticalMain.class.getSimpleName();
	private EnumMap<ScreenType, AbstractScreen> screenCach;
	private FitViewport screenViewport;

	public static final short BIT_CIRCLE = 1 << 0;
	public static final short BIT_BOX = 1 << 1;
	public static final short BIT_GROUND = 1 << 2;

	private Box2DDebugRenderer box2DDebugRenderer;
	private World world;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Box2D.init();
		world = new World(new Vector2(0, -9.81f), true);
		box2DDebugRenderer = new Box2DDebugRenderer();

		screenCach = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
		screenViewport = new FitViewport(9, 16);
		setScreen(ScreenType.GAME);
	}

	public void setScreen(final ScreenType screenType){
		final AbstractScreen screen = screenCach.get(screenType);
		if (screen == null){
			try {
				Gdx.app.debug(TAG, "Creating a new screen: " + screenType);
				final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(), MysticalMain.class).newInstance(this);
				screenCach.put(screenType, newScreen);
				setScreen(newScreen);
			} catch (ReflectionException e) {
				throw new GdxRuntimeException("Screen " + screenType + " Could not be created", e);
			}
		}else {
			Gdx.app.debug(TAG, "Switching to screen: " + screenType);
			setScreen(screen);
		}
	}

	public World getWorld() {
		return world;
	}

	public FitViewport getScreenViewport() {
		return screenViewport;
	}

	public Box2DDebugRenderer getBox2DDebugRenderer() {
		return box2DDebugRenderer;
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
		box2DDebugRenderer.dispose();
	}
}
