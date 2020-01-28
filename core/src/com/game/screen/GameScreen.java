package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.MysticalMain;

import static com.game.MysticalMain.BIT_BOX;
import static com.game.MysticalMain.BIT_PLAYER;
import static com.game.MysticalMain.BIT_GROUND;

public class GameScreen extends AbstractScreen {

    private final String TAG = GameScreen.class.getSimpleName();
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Body player;
    public GameScreen(MysticalMain context) {
        super(context);
        Gdx.app.debug(TAG, "Inicio construtor");

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //create a player
        bodyDef.position.set(4.5f, 3);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("Player");

        fixtureDef.density = 1;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();
        Gdx.app.debug(TAG, "Criado o player");

        //create a room
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setUserData("Ground");

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        final ChainShape chainShape = new ChainShape();
        chainShape.createChain(new float[]{1, 1, 1, 15, 8, 15, 8, 1, 1, 1});
        fixtureDef.shape = chainShape;
        body.createFixture(fixtureDef);
        chainShape.dispose();
        Gdx.app.debug(TAG, "Criada a sala");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.B)){
            context.setScreen(ScreenType.LOADING);
        }
        final float speedX;
        final float speedY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            speedX = -3;
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            speedX = 3;
        }else{
            speedX = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            speedY = -3;
        }else if (Gdx.input.isKeyPressed(Input.Keys.W)){
            speedY = 3;
        }else{
            speedY = 0;
        }
//TODO stopped in lesson 10
        player.applyLinearImpulse(
                (speedX - player.getLinearVelocity().x) * player.getMass(),
                (speedY - player.getLinearVelocity().y) * player.getMass(),
                player.getWorldCenter().x,
                player.getWorldCenter().y,
                true
        );

        viewport.apply(true);
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
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
