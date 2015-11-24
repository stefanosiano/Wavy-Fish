package com.stefanosiano.wavyfish.experience;

import com.stefanosiano.common.tochange.DataSaver;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;

public class Experience {
	private static String lives;
	private static String level;
	private static String experience;
	private static String maxLevel;
	
	public static void load(){
		maxLevel = "10";
		lives = DataSaver.load(SavedItems.lives + "");
		level = DataSaver.load(SavedItems.level + "");
		experience = DataSaver.load(SavedItems.experience + "");
		
		try{Integer.parseInt(lives);}catch(Exception e){lives = SavedItems.lives.getDefault();}
		try{Integer.parseInt(level);}catch(Exception e){level = SavedItems.level.getDefault();}
		try{Integer.parseInt(experience);}catch(Exception e){experience = SavedItems.experience.getDefault();}
		
		calculateNewValues(0);
	}
	
	public static int getNextLevelExperience(int experience){
		int levelStep = 0;
		for(int i = 1; i < Integer.parseInt(maxLevel) && experience >= levelStep; i++){
			levelStep += (3 * i + 1) * 10000;
		}
		return levelStep;
	}
	
	public static int getPreviousLevelExperience(int experience){
		int levelStep = 0;
		int i = 0;
		for(i = 1; i < Integer.parseInt(maxLevel) && experience >= levelStep; i++){
			levelStep += (3 * i + 1) * 10000;
		}
		i--;
		levelStep -= (3 * i + 1) * 10000;
		return levelStep;
	}
	
	public static void calculateNewValues(int score){
		/* 
		 * exp - level
		 * (2 * i + 1) * 10000
		 * 1 - 30000 - start
		 * 2 - 80000 - medium
		 * 3 - 150000 - fish
		 * 4 - 240000 - life
		 * 5 - 350000 - hard
		 * 6 - 480000 - fish
		 * 7 - 630000 - life
		 * 8 - 800000 - crazy
		 * 9 - 990000 - fish
		 * 10 - life
		 * 
		 * (3 * i + 1) * 10000
		 * 1 - 40000 - start
		 * 2 - 110000 - medium
		 * 3 - 210000 - fish
		 * 4 - 340000 - life
		 * 5 - 500000 - hard
		 * 6 - 690000 - fish
		 * 7 - 910000 - life
		 * 8 - 1160000 - crazy
		 * 9 - 1440000 - fish
		 * 10 - life
		 */
		int tmpLevel = 1;
		int levelStep = 0;
		
		levelStep = 0;
		int tmpExperience = Integer.parseInt(experience);
		tmpExperience += score;
		for(int i = 1; i < Integer.parseInt(maxLevel); i++){
			levelStep += (3 * i + 1) * 10000;
			if(tmpExperience >= levelStep)
				tmpLevel++;
			else break;
		}

		if(tmpExperience > levelStep)
			tmpExperience = levelStep;
		
		int temp = 0;
		switch(tmpLevel){
			case 1:
			case 2:
			case 3:
				temp = 1;
				break;
			case 4:
			case 5:
			case 6:
				temp = 2;
				break;
			case 7:
			case 8:
			case 9:
				temp = 3;
				break;
			case 10:
				temp = 4;
		}
		
		if(Settings.FIRST_SHARE)
			temp++;
		
		level = tmpLevel + "";
		lives = temp + "";
		experience = tmpExperience + "";
		DataSaver.save(SavedItems.lives + "", lives);
		DataSaver.save(SavedItems.level + "", level);
		DataSaver.save(SavedItems.experience + "", experience);
	}
	
	public static int getMaxLevel(){
		return Integer.parseInt(maxLevel);
	}
	
	public static int getLives(){
		return Integer.parseInt(lives);
	}
	
	public static int getExperience(){
		return Integer.parseInt(experience);
	}
	
	public static int getLevel(){
		return Integer.parseInt(level);
	}
}
