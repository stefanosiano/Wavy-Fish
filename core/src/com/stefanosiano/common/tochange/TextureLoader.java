package com.stefanosiano.common.tochange;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.wavyfish.utilities.Settings;

public class TextureLoader {
	private static String path;
	public static Texture texture;
	private static TextureRegion textureRegion;
	private static TextureAtlas atlas, fontAtlas;
	public static TextureRegion 
		//info
		back, swipeSignInfo, swipeSignInfoFlipped,
		//menu 1
		menu, credits, speakerOn, speakerOff, fullScreenOn, fullScreenOff, musicOn, musicOff, swipeBackground, swipeFlippedBackground, options, shareText,
		moreLives,
		//menu 2
		buttonSelected, panel, buttonYellow, buttonGreen, buttonCyan, buttonAzure, buttonRed,
		//game
		background, background2, net, net2, resume, restart, star, blackStar, highScoreBackground, experienceBarBackground, experienceBarFront,
		fish0, fish1, fish2, fish3, fish4, fish5, fish6, fish7, piranha0, piranha1, piranha2, piranha3, piranha4, piranha5, piranha6, piranha7, 
		piranhaFlipped0, piranhaFlipped1, piranhaFlipped2, piranhaFlipped3, piranhaFlipped4, piranhaFlipped5, piranhaFlipped6, piranhaFlipped7,
		redScreen, experienceLeft, experienceRight, experienceTop, experienceBottom,
		lifeBarLeft, lifeBarMid, lifeBarRight, shareScore, cup,
		//others
		buttonDisabled, splashLogo, wavyFish, fishTutor, cloudTutor, shroud;
	
	public static BitmapFont fontYellow, fontBlue, fontWhite, fontOrange;
	
	public static void load() {
		createAtlas();
		
		//GLOBAL images creation
		path = "menu/";
	    
	    splashLogo = loadTexture(path + "logo");
		buttonDisabled = loadTexture(path + "button_disabled");
		wavyFish = loadTexture(path + "wavy_fish");
		shroud = loadTexture(path + "alone");
        cup = loadTexture(path + "cup");
		
		
		//TUTORIAL items creation
		path = "menu/";
		
		fishTutor = loadTexture(path + "fish_tutor");
		cloudTutor = loadTexture(path + "cloud_tutor");
	    
		
	    //MENU1 items creation
		path = "menu/";

		speakerOn = loadTexture(path + "speaker_on");
	    speakerOff = loadTexture(path + "speaker_off");
	    fullScreenOn = loadTexture(path + "fullscreen");
	    fullScreenOff = loadTexture(path + "fullscreen_off");
	    musicOn = loadTexture(path + "music_on");
	    musicOff = loadTexture(path + "music_off");
	    swipeBackground = loadTexture(path + "swipe_background");
	    swipeFlippedBackground = new TextureRegion(swipeBackground);
	    swipeFlippedBackground.flip(true, false);
	    options = loadTexture(path + "settings");
	    shareText = loadTexture(path + "share_text");
	    shareScore = loadTexture(path + "share_score");
	    moreLives = loadTexture(path + "more_lives");

	    //MENU2 items creation
		path = "menu/";

	    panel = loadTexture(path + "panel");
	    buttonYellow = loadTexture(path + "button1");
	    buttonGreen = loadTexture(path + "button2");
	    buttonCyan = loadTexture(path + "button3");
	    buttonAzure = loadTexture(path + "button4");
	    buttonRed = loadTexture(path + "button5");
	    buttonSelected = loadTexture(path + "mode_selected");


	    //INFO items creation
		path = "menu/";

		back = loadTexture(path + "back");
		swipeSignInfo = loadTexture(path + "swipe_background_info");
		swipeSignInfoFlipped = new TextureRegion(swipeSignInfo);
		swipeSignInfoFlipped.flip(true, false);
		

	    //GAME items creation
		path = "menu/";

		menu = loadTexture(path + "menu");
		restart = loadTexture(path + "retry");
		resume = loadTexture(path + "resume");
	    star = loadTexture(path + "star");
	    blackStar = loadTexture(path + "black_star");
	    experienceBarBackground = loadTexture(path + "experience_back");
	    experienceBarFront = loadTexture(path + "experience_front");
	    
	    experienceLeft = loadTexture(path + "experience_border");
	    experienceRight = loadTexture(path + "experience_border");
	    experienceTop = loadTexture(path + "experience_border");
	    experienceBottom = loadTexture(path + "experience_border");
	    
	    
	    //text items creation
		path = "data/fonts/";
		
        fontYellow = new BitmapFont(Gdx.files.internal(path + "font_yello/yellow.fnt"), fontAtlas.findRegion("yellow"));
        fontBlue= new BitmapFont(Gdx.files.internal(path + "font_blue/blue.fnt"), fontAtlas.findRegion("blue"));
        fontWhite = new BitmapFont(Gdx.files.internal(path + "font_white/shadow2.fnt"), fontAtlas.findRegion("shadow2"));
        fontOrange = new BitmapFont(Gdx.files.internal(path + "font_orange/orange.fnt"), fontAtlas.findRegion("orange"));
        
        fontYellow.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontBlue.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontWhite.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontOrange.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        loadImageTypeDependentTextures();
	}
	
	private static void loadImageTypeDependentTextures(){
		path = "" + Settings.imageType + "/";

	    highScoreBackground = loadTexture(path + "highscore_background");
		net = loadTexture(path + "net");
		net2 = new TextureRegion(net);
		net2.flip(false, true);
		new TextureRegion(net2).flip(false, true);
		
		background = loadTexture(path + "fondale");
		background2 = loadTexture(path + "fondale");
		//this set the nearest filter to all of the images in its atlas (all textures used in game)
		background.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		fish0 = loadTexture(path + "fish", 0);
		fish1 = loadTexture(path + "fish", 1);
		fish2 = loadTexture(path + "fish", 2);
		fish3 = loadTexture(path + "fish", 3);
		fish4 = loadTexture(path + "fish", 4);
		fish5 = loadTexture(path + "fish", 5);
		fish6 = loadTexture(path + "fish", 6);
		fish7 = loadTexture(path + "fish", 7);

		piranha0 = loadTexture(path + "piranha", 0);
		piranha1 = loadTexture(path + "piranha", 1);
		piranha2 = loadTexture(path + "piranha", 2);
		piranha3 = loadTexture(path + "piranha", 3);
		piranha4 = loadTexture(path + "piranha", 4);
		piranha5 = loadTexture(path + "piranha", 5);
		piranha6 = loadTexture(path + "piranha", 6);
		piranha7 = loadTexture(path + "piranha", 7);

		piranhaFlipped0 = new TextureRegion(piranha0);
		piranhaFlipped0.flip(false, true);
		new TextureRegion(piranhaFlipped0).flip(false, true);
		piranhaFlipped1 = new TextureRegion(piranha1);
		piranhaFlipped1.flip(false, true);
		new TextureRegion(piranhaFlipped1).flip(false, true);
		piranhaFlipped2 = new TextureRegion(piranha2);
		piranhaFlipped2.flip(false, true);
		new TextureRegion(piranhaFlipped2).flip(false, true);
		piranhaFlipped3 = new TextureRegion(piranha3);
		piranhaFlipped3.flip(false, true);
		new TextureRegion(piranhaFlipped3).flip(false, true);
		piranhaFlipped4 = new TextureRegion(piranha4);
		piranhaFlipped4.flip(false, true);
		new TextureRegion(piranhaFlipped4).flip(false, true);
		piranhaFlipped5 = new TextureRegion(piranha5);
		piranhaFlipped5.flip(false, true);
		new TextureRegion(piranhaFlipped5).flip(false, true);
		piranhaFlipped6 = new TextureRegion(piranha6);
		piranhaFlipped6.flip(false, true);
		new TextureRegion(piranhaFlipped6).flip(false, true);
		piranhaFlipped7 = new TextureRegion(piranha7);
		piranhaFlipped7.flip(false, true);
		new TextureRegion(piranhaFlipped7).flip(false, true);

        redScreen = loadTexture(path + "red_border");
        lifeBarLeft = loadTexture(path + "life_bar_left");
        lifeBarMid = loadTexture(path + "life_bar_mid");
        lifeBarRight = loadTexture(path + "life_bar_right");
	}
	
	public static void changeTextures(){
		loadImageTypeDependentTextures();
	}
	
	public static void dispose() {
	    atlas.dispose();
	    swipeFlippedBackground.getTexture().dispose();
	    swipeSignInfoFlipped.getTexture().dispose();
	}
	
	private static TextureRegion loadTexture(String path){
		textureRegion = atlas.findRegion(path);
	    return textureRegion;
	}
	
	private static TextureRegion loadTexture(String path, int index){
		textureRegion = atlas.findRegion(path, index);
		return textureRegion;
	}
	
	private static void createAtlas(){
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		if(width < height){
			int temp = width;
			width = height;
			height = temp;
		}

		System.out.println("width" + width);
		System.out.println("height" + height);
		if(width < 1280 && height < 720){
			atlas = new TextureAtlas(Gdx.files.internal("data/atlas_small/atlas_small.atlas"));
		}
		else{
			atlas = new TextureAtlas(Gdx.files.internal("data/atlas_large/atlas_large.atlas"));	
		}
		for(AtlasRegion region : atlas.getRegions())
			region.flip(false, true);
        fontAtlas = new TextureAtlas(Gdx.files.internal("data/atlas_fonts/atlas_fonts.atlas"));
	}

    public static TextureRegion loadSplahRegion(){
        TextureRegion textureRegion = new TextureRegion(new Texture("data/splash.png"));
        textureRegion.flip(false, true);
        textureRegion.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        return textureRegion;
    }
}
