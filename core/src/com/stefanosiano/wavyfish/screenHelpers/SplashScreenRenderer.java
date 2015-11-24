package com.stefanosiano.wavyfish.screenHelpers;

import com.stefanosiano.common.SimpleRenderer;
import com.stefanosiano.common.tochange.TextureLoader;

public class SplashScreenRenderer extends SimpleRenderer{
    
    @Override
    public void render(float delta) {
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(TextureLoader.splashLogo, 0, 0, virtualWidth, 900);
        batcher.end();
    }
}
