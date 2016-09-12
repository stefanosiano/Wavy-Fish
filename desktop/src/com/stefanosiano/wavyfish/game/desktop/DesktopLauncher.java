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

        String pathIn = "/home/stefano/Desktop/Giochi Android Miei/immagini giochi";
        String pathOut = "/home/stefano/AndroidStudioProjects\\Wavy-Fish\\android\\assets\\data\\";
        //TexturePacker.process(pathIn + "Wavy Fish Data\\data_small", pathOut + "atlas_small", "atlas_small");
        //TexturePacker.process(pathIn + "Wavy Fish Data\\data_large", pathOut + "atlas_large", "atlas_large");
        //TexturePacker.process(pathIn + "Wavy Fish Fonts", pathOut + "atlas_fonts", "atlas_fonts");

        new LwjglApplication(new WavyFishGame(new DesktopApiController()), config);
	}
}