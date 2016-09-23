package com.stefanosiano.wavyfish.utilities;

import com.stefanosiano.common.tochange.DataSaver;
import com.stefanosiano.wavyfish.utilities.Enums.Difficulty;
import com.stefanosiano.wavyfish.utilities.Enums.GameControl;
import com.stefanosiano.wavyfish.utilities.Enums.GameMode;
import com.stefanosiano.wavyfish.utilities.Enums.GameObstacle;
import com.stefanosiano.wavyfish.utilities.Enums.ImageType;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;

public class Settings {
	public static boolean STRETCH = false;
	public static boolean SOUND_ENABLED = true;
	public static boolean MUSIC_ENABLED = true;
	public static boolean TUT_MENU1 = true;
	public static boolean TUT_MENU2 = true;
	public static boolean NEW_TUT_CLASSIC = true;
	public static boolean NEW_TUT_FLAPPY = true;
	public static boolean NEW_TUT_WAVY = true;
	public static boolean NEW_TUT_BOUNCING = true;
	public static int screenshotCounter = 0;
	public static ImageType imageType = ImageType.original;
	public static ImageType[] imageTypes = {ImageType.original, ImageType.horror, ImageType.tema, ImageType.space};
	public static float timeForAds = 0;
	public static float totalPlayTime = 0;
	private static int imageTypeIndex = 0;
	public static Difficulty difficulty = Difficulty.easy;
	public static GameControl gameControl = GameControl.classic;
	public static GameMode gameMode = GameMode.blinking;
	public static GameObstacle gameObstacle = GameObstacle.wall;
	public static boolean gameEndless = false;

	/**
	 * used to know if the user got his life after sharing the game on facebook, g+, or twitter
	 */
    public static boolean FIRST_SHARE = false;
    public static int REWARDEDVIDEOSWATCHED = 0;
    public static int rewardedVideosToWatch = 1;
	
	
	public static void stretch(){
		STRETCH = !STRETCH;
		DataSaver.save(SavedItems.stretch + "", STRETCH+"");
	}
	
	public static void enableSound(){
		SOUND_ENABLED = !SOUND_ENABLED;
		DataSaver.save(SavedItems.soundEnabled + "", SOUND_ENABLED+"");
	}
	
	public static void enableMusic(){
		MUSIC_ENABLED = !MUSIC_ENABLED;
		DataSaver.save(SavedItems.musicEnabled + "", MUSIC_ENABLED+"");
	}
	
	public static void setScreenshotCounter(int counter){
		screenshotCounter = counter;
		DataSaver.save(SavedItems.screenshotCounter + "", counter+"");
	}
	
	public static void load(){
		STRETCH = DataSaver.load(SavedItems.stretch + "").equalsIgnoreCase("true");
		SOUND_ENABLED = DataSaver.load(SavedItems.soundEnabled + "").equalsIgnoreCase("true");
		MUSIC_ENABLED = DataSaver.load(SavedItems.musicEnabled + "").equalsIgnoreCase("true");

		TUT_MENU1 = DataSaver.load(SavedItems.showTutMenu1 + "").equalsIgnoreCase("true");
		TUT_MENU2 = DataSaver.load(SavedItems.showTutMenu2 + "").equalsIgnoreCase("true");
		
		NEW_TUT_CLASSIC = DataSaver.load(SavedItems.showNewTutClassic + "").equalsIgnoreCase("true");
		NEW_TUT_FLAPPY = DataSaver.load(SavedItems.showNewTutFlappy + "").equalsIgnoreCase("true");
		NEW_TUT_WAVY = DataSaver.load(SavedItems.showNewTutWavy + "").equalsIgnoreCase("true");
		NEW_TUT_BOUNCING = DataSaver.load(SavedItems.showNewTutBouncing + "").equalsIgnoreCase("true");

		/*
		TUT_MENU1 = true;
		TUT_MENU2 = true;
		NEW_TUT_CLASSIC = true;
		NEW_TUT_FLAPPY = true;
		NEW_TUT_WAVY = true;
		NEW_TUT_BOUNCING = true;*/

		try{imageTypeIndex = Integer.parseInt(DataSaver.load(SavedItems.imageTypeIndex + ""));}catch(Exception e){imageTypeIndex = Integer.parseInt(SavedItems.imageTypeIndex.getDefault());}
		try{screenshotCounter = Integer.parseInt(DataSaver.load(SavedItems.screenshotCounter + ""));}catch(Exception e){screenshotCounter = Integer.parseInt(SavedItems.screenshotCounter.getDefault());}
		FIRST_SHARE = DataSaver.load(SavedItems.firstShare + "").equalsIgnoreCase("true");

		totalPlayTime = 0;
		timeForAds = 0;
		imageType = imageTypes[imageTypeIndex];
	}
	
	public static void changeImages(boolean next){
		if(next)
			imageTypeIndex++;
		else
			imageTypeIndex--;
		imageTypeIndex += imageTypes.length;
		imageTypeIndex = imageTypeIndex % imageTypes.length;
		
		imageType = imageTypes[imageTypeIndex];
		DataSaver.save(SavedItems.imageTypeIndex + "", imageTypeIndex + "");
	}
	
	public static void tutorialShown(SavedItems savedItem){
		switch(savedItem){
			case showTutMenu1:
				Settings.TUT_MENU1 = false;
				break;
			case showTutMenu2:
				Settings.TUT_MENU2 = false;
				break;
			case showNewTutClassic:
				Settings.NEW_TUT_CLASSIC = false;
				break;
			case showNewTutFlappy:
				Settings.NEW_TUT_FLAPPY = false;
				break;
			case showNewTutWavy:
				Settings.NEW_TUT_WAVY = false;
				break;
			case showNewTutBouncing:
				Settings.NEW_TUT_BOUNCING = false;
				break;
			default:
				break;
		}
		DataSaver.save(savedItem + "", "false");
	}
	
	public static void sharedFirstTime(){
		FIRST_SHARE = true;
		DataSaver.save(SavedItems.firstShare + "", "true");
	}
}
