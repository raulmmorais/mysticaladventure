package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.MysticalMain;

import static com.game.MysticalMain.BIT_BOX;
import static com.game.MysticalMain.BIT_CIRCLE;
import static com.game.MysticalMain.BIT_GROUND;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public GameScreen(MysticalMain context) {
        super(context);
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //create a circle
        bodyDef.position.set(4.5f, 15);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        body.setUserData("Circle");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 1.2f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        final CircleShape cShape = new CircleShape();
        cShape.setRadius(.5f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
        //create a box
        bodyDef.position.set(5.3f, 6);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        body.setUserData("Box");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 1.5f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_CIRCLE;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
        //create a platform
        bodyDef.position.set(4.5f, 1);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Platform");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(4f, 0.25f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
        //create a platform
        bodyDef.position.set(0.5f, 8);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData("Platform Corner L");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(0.25f, 7f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
        //create a platform
        bodyDef.position.set(8.5f, 8);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData("Platform Corner R");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(0.25f, 7f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
        //create a platform
        bodyDef.position.set(4.5f, 16);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Platform Top");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(4f, 0.25f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        cShape.dispose();
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
