package com.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.screen.AbstractScreen;
import com.game.screen.ScreenType;

import java.util.EnumMap;

public class MysticalMain extends Game {
	private static final String TAG = MysticalMain.class.getSimpleName();
	private SpriteBatch spriteBatch;
	private EnumMap<ScreenType, AbstractScreen> screenCache;
	private OrthographicCamera gameCamera;
	private FitViewport screenViewport;

	public static final float UNIT_SCALE = 1/32f;
	public static final short BIT_PLAYER = 1 << 0;
	public static final short BIT_BOX = 1 << 1;
	public static final short BIT_GROUND = 1 << 2;

	private Box2DDebugRenderer box2DDebugRenderer;
	private World world;
	private GameContactListener contactListener;

	private static final float FIXED_TIME_STEP = 1 / 60f;
	private float accumulator;

	private AssetManager assetManager;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		spriteBatch = new SpriteBatch();

		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		contactListener = new GameContactListener();
		world.setContactListener(contactListener);
		box2DDebugRenderer = new Box2DDebugRenderer();

		//initialize assetmanager
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));

		gameCamera = new OrthographicCamera();
		screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
		screenViewport = new FitViewport(9, 16, gameCamera);
		setScreen(ScreenType.LOADING);
	}

	public void setScreen(final ScreenType screenType){
		final AbstractScreen screen = screenCache.get(screenType);
		if (screen == null){
			try {
				Gdx.app.debug(TAG, "Creating a new screen: " + screenType);
				final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(), MysticalMain.class).newInstance(this);
				screenCache.put(screenType, newScreen);
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

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public OrthographicCamera getGameCamera() {
		return gameCamera;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	@Override
	public void render() {
		super.render();
		accumulator += Math.min(0.25f, Gdx.graphics.getRawDeltaTime());
		while (accumulator >= FIXED_TIME_STEP){
			world.step(FIXED_TIME_STEP, 6, 2);
			accumulator -= FIXED_TIME_STEP;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
		box2DDebugRenderer.dispose();
		assetManager.dispose();
	}
}
