package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stefanosiano.common.Text;
import com.stefanosiano.wavyfish.utilities.Enums.Difficulty;
import com.stefanosiano.wavyfish.utilities.Enums.GameMode;
import com.stefanosiano.wavyfish.utilities.Enums.GameObstacle;
import com.stefanosiano.wavyfish.utilities.Settings;

public class Score{
	private String text;
	private int x, y, scoreValue, wallPassed;
	private float scalePointsX, scalePointsY, scaleMultiplierX, scaleMultiplierY;
	private float multiplier, originalMultiplier;
	private Difficulty difficulty;
	private BitmapFont fontPoints, fontMultiplier;
	
	private Text pointText, multiplierText;
	
	

	public Score(BitmapFont fontPoints, BitmapFont fontMultiplier, float scalePointsX, float scalePointsY, float scaleMultiplierX, float scaleMultiplierY,
			int x, int y, Difficulty difficulty){
		this.pointText = new Text(fontPoints, scalePointsX, scalePointsY, false);
		this.multiplierText = new Text(fontMultiplier, scaleMultiplierX, scaleMultiplierY, false);
		this.x = x;
		this.y = y;

		this.scalePointsX = scalePointsX;
		this.scalePointsY = scalePointsY;
		this.fontMultiplier = fontMultiplier;
		this.fontPoints = fontPoints;
		this.scoreValue = 0;
		this.wallPassed = 0;
		this.difficulty = difficulty;
		switch(difficulty){
			case easy:
				this.multiplier = 1;
				break;
			case medium:
				this.multiplier = 1.75f;
				break;
			case hard:
				this.multiplier = 2.75f;
				break;
			case crazy:
				this.multiplier = 4f;
				break;
			default:
				this.multiplier = 1;
				break;
		}
		if(Settings.gameMode.equals(GameMode.blinking))
			multiplier += 1f;
		if(Settings.gameObstacle.equals(GameObstacle.piranha))
			multiplier += 0.75f;
		this.originalMultiplier = this.multiplier;

		pointText.setCenteredToLeftOf("0", x, y);
		multiplierText.set("x" + multiplier, x + 20, y);
	}

	public int update(float scoreValue){
		this.scoreValue += scoreValue * multiplier;
		this.text = "" + this.scoreValue;
		this.wallPassed++;
		if(wallPassed % 5 == 0)
			updateMultiplier();

		pointText.setCenteredToLeftOf("" + (int)this.scoreValue, x, y);
		multiplierText.set("x" + multiplier, x + 20, y);
		return this.scoreValue;
	}
	
	private float updateMultiplier(){
		switch(difficulty){
			case easy:
				this.multiplier += 0.25;
				break;
			case medium:
				this.multiplier += 0.5;
				break;
			case hard:
				this.multiplier += 0.75;
				break;
			case crazy:
				this.multiplier += 1;
				break;
			default:
				this.multiplier += 0.25;
				break;
		}
		return this.multiplier;
	}
	
	public float resetMultiplier(){
		this.multiplier = originalMultiplier;
		this.wallPassed = 0;
		this.multiplierText.updateText("x" + multiplier);
		return this.multiplier;
	}
	
	public void reset(){
		this.multiplier = originalMultiplier;
		this.wallPassed = 0;
		this.multiplierText.updateText("x" + multiplier);
		this.pointText.updateTextCenteredToLeftOf("0");
	}
	
	public void setOriginalMultiplier(float multiplier){
		this.originalMultiplier = multiplier;
		this.multiplier = multiplier;
	}
	
	public void draw(SpriteBatch batcher, float delta){
		multiplierText.draw(batcher, delta);
		pointText.draw(batcher, delta);
	}
	
	public float getScalePointsX(){
		return scalePointsX;
	}
	
	public float getScalePointsY(){
		return scalePointsY;
	}
	
	public BitmapFont getFontPoints(){
		return fontPoints;
	}
	
	public int getScoreValue(){
		return scoreValue;
	}
	
	public float getMultiplier(){
		return multiplier;
	}
}
