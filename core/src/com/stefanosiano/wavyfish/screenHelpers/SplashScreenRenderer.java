package com.stefanosiano.wavyfish.screenHelpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleRenderer;
import com.stefanosiano.common.tochange.TextureLoader;

public class SplashScreenRenderer extends SimpleRenderer{
    private TextureRegion splash;

    public void setSplash(TextureRegion splash){
        this.splash = splash;
    }

    @Override
    public void render(float delta) {
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(splash, 0, 0, virtualWidth, 900);
        batcher.end();
    }
}
