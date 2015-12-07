package com.stefanosiano.wavyfish.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Wavy Fish";
        config.width = 640;
        config.height = 360;

        //TexturePacker.process("C:\\Users\\Stefano\\Desktop\\immagini giochi\\Wavy Fish Data\\data_small", "C:\\Users\\Stefano\\AndroidStudioProjects\\Wavy Fish\\android\\assets\\data\\atlas_small", "atlas_small");
        //TexturePacker.process("C:\\Users\\Stefano\\Desktop\\immagini giochi\\Wavy Fish Data\\data_large", "C:\\Users\\Stefano\\AndroidStudioProjects\\Wavy Fish\\android\\assets\\data\\atlas_large", "atlas_large");

        new LwjglApplication(new WavyFishGame(new DesktopApiController()), config);
	}
}