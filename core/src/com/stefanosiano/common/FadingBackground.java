package com.stefanosiano.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.stefanosiano.common.tweenAccessors.FloatAccessor;
import com.stefanosiano.common.tweenAccessors.FloatValue;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Stefano on 07/12/2015.
 */
public class FadingBackground extends SimpleGameObject {
    private FloatValue alphaFading;
    private boolean fadingBack;
    private TweenManager managerFade;
    private Color c;

    public FadingBackground (Color c, float x, float y, float w, float h){
        super(null, x, y, w, h, 0, 0);
        this.c = c;
        this.fadingBack = false;
    }

    public void update(float delta){
        super.update(delta);
        if(fadingBack)
            managerFade.update(delta);
    }

    public void setValues(Color c, float x, float y, float w, float h){
        this.c = c;
        position.x = x;
        position.y = y;
        width = w;
        height = h;
    }

    /**
     * This method starts the background fading.
     * Be sure to call setValues() before this method!
     *
     * @param startAlpha
     * @param stopAlpha
     * @param duration
     * @param onFadeFinished
     */
    public void startFading(float startAlpha, float stopAlpha, float duration, final Runnable onFadeFinished){
        alphaFading = new FloatValue(startAlpha);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerFade = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int arg0, BaseTween<?> arg1) {
                fadingBack = false;
                if(onFadeFinished != null)
                    onFadeFinished.run();
            }
        };
        Tween.to(alphaFading, -1, duration).target(stopAlpha)
                .ease(TweenEquations.easeNone)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(managerFade);
        this.fadingBack = true;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(c.r, c.g, c.b, alphaFading.getValue());
        shapeRenderer.rect(position.x, position.y, width, height);
        shapeRenderer.end();
    }
}
