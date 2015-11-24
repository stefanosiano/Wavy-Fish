package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.stefanosiano.common.SimpleGameObject;
import com.stefanosiano.common.Text;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.wavyfish.utilities.Settings;

public class HighScoreContainer extends SimpleGameObject{
	private TextureRegion star, blackStar;
	private Text scoreValueText, scoreWordText, bestScoreValueText, bestScoreWordText, levelText, experienceText, continueText, unlockedText;
	private float scoreValueX, scoreValueY, wordsScaleX, wordsScaleY, levelWordX, levelWordY, experienceWordX, experienceWordY, bestScoreValueX, bestScoreValueY;
	private float starX, starY, starWidth, starHeight;
	private int starNumber, activeStars;
	private int level, experience, newLevel, newExperience, newMaxExperience;
	private float starDistance;
	private ExperienceBar experienceBar;
	private boolean showContinueText;
	private int numCoinPlay;

	public HighScoreContainer(TextureRegion texture, TextureRegion star, TextureRegion blackStar, float x, float y, float width, float height, float scoreValueLeftX, float scoreValueY, 
			float bestScoreValueLeftX, float bestScoreValueY, float levelWordX, float levelWordY, float experienceWordX, float experienceWordY, 
			float continueTextX, float continueTextY, float unlockedTextX, float unlockedTextY, BitmapFont wordsFont, float wordsScaleX, float wordsScaleY, 
			int starNumber, ExperienceBar experienceBar) {
		super(texture, x, y, width, height, 0, 0);
		this.scoreValueX = scoreValueLeftX;
		this.scoreValueY = scoreValueY;
		this.bestScoreValueX = bestScoreValueLeftX;
		this.bestScoreValueY = bestScoreValueY;
		this.wordsScaleX = wordsScaleX;
		this.wordsScaleY = wordsScaleY;
		this.experienceWordX = experienceWordX;
		this.experienceWordY = experienceWordY;
		this.levelWordX = levelWordX;
		this.levelWordY = levelWordY;
		this.star = star;
		this.blackStar = blackStar;
		this.starNumber = starNumber;
		this.starDistance = 25;
		this.experienceBar = experienceBar;
		
		bestScoreWordText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		scoreWordText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		levelText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		experienceText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		continueText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		continueText.setCenteredHorizzontally("Tap to continue", continueTextX, continueTextY);
		this.unlockedText = new Text(wordsFont, wordsScaleX, wordsScaleY, false);
		this.unlockedText.setCenteredHorizzontally("", unlockedTextX, unlockedTextY);
		showContinueText = false;
		
		//the value of width is based on the number of stars and the space between each star
		starWidth = (width - ((starNumber - 1f) * starDistance))/(starNumber + 1f);
		starHeight = width/(starNumber + 1f);
		starY = y - (starHeight/4f);
		starX = x + (starWidth/2f);
	}
	
	public void initialize(Score score, int level, int experience, int maxExperience, int previousMaxExperience, int bestScore){
		this.level = level;
		this.experience = experience;
		int scoreValue = score.getScoreValue();
		this.numCoinPlay = 0;
		BitmapFont pointFont = score.getFontPoints();
		
		pointFont.getData().setScale(score.getScalePointsX(), score.getScalePointsY());
		GlyphLayout scoreLayout = new GlyphLayout(pointFont, scoreValue + "");
		GlyphLayout bestLayout = new GlyphLayout(pointFont, bestScore + "");
		
		scoreValueText = new Text(pointFont, score.getScalePointsX(), score.getScalePointsY(), false);
		bestScoreValueText = new Text(pointFont, score.getScalePointsX(), score.getScalePointsY(), false);

		scoreValueText.setCenteredToLeftOf(scoreValue + "", scoreValueX, scoreValueY);
		bestScoreValueText.setCenteredToLeftOf(bestScore + "", bestScoreValueX, bestScoreValueY);
		
		scoreWordText.setCenteredToLeftOf("Score:", scoreValueX - (scoreLayout.width + 30), scoreValueY);
		bestScoreWordText.setCenteredToLeftOf("Best:", bestScoreValueX - (bestLayout.width + 30), bestScoreValueY);
		levelText.setToLeftOf("LVL: " + level, levelWordX, levelWordY);
		experienceText.set("Experience: " + experience, experienceWordX, experienceWordY);

		
		if(scoreValue < 5000) activeStars = 0;
		if(scoreValue >= 5000 && scoreValue < 15000) activeStars = 1;
		if(scoreValue >= 15000 && scoreValue < 40000) activeStars = 2;
		if(scoreValue >= 40000 && scoreValue < 75000) activeStars = 3;
		if(scoreValue >= 75000 && scoreValue < 110000) activeStars = 4;
		if(scoreValue >= 110000) activeStars = 5;

		experienceBar.setValue(experience);
		experienceBar.setRenderingValue(experience - previousMaxExperience);
		experienceBar.setOffset(previousMaxExperience);
		experienceBar.setMaxValue(maxExperience - previousMaxExperience);
		experienceBar.setValueWidth((experienceBar.getRenderingValue())/ (float)(experienceBar.getMaxValue()) * experienceBar.getWidth());
		showContinueText = false;
	}
	
	public void setNewValues(int newExperience){
		this.newExperience = newExperience;
		experienceBar.modifyLife(experience - this.newExperience, 1.3f);
	}

	@Override
	public void draw(SpriteBatch batcher, float delta){
		super.draw(batcher);
		scoreValueText.draw(batcher, delta);
		scoreWordText.draw(batcher, delta);
		bestScoreValueText.draw(batcher, delta);
		bestScoreWordText.draw(batcher, delta);
		levelText.draw(batcher, delta);
		experienceText.draw(batcher, delta);

		starX = position.x + (starWidth/2f);
		
		for(int i = 0; i < activeStars; i++){
			batcher.draw(star, starX, starY, starWidth, starHeight);
			starX += starDistance + starWidth;
		}
		
		for(int i = activeStars; i < starNumber; i++){
			batcher.draw(blackStar, starX, starY, starWidth, starHeight);
			starX += starDistance + starWidth;
		}
		
		experienceBar.draw(batcher);
		unlockedText.draw(batcher, delta);
		if(showContinueText)
			continueText.draw(batcher, delta);
	}

	public void setShowContinueText(boolean show){
		this.showContinueText = show;
	}

	public boolean getShowContinueText(){
		return showContinueText;
	}
	
	public Text getContinueText(){
		return continueText;
	}
	
	private void onLevelPassed(){
		experienceBar.setOffset(experienceBar.getOffset() + experienceBar.getMaxValue());
		newMaxExperience = Experience.getNextLevelExperience((int)experienceBar.getRealCurrentValue());
		experienceBar.setRenderingValue(experienceBar.getRenderingValue() - experienceBar.getMaxValue());
		experienceBar.setMaxValue(newMaxExperience - experienceBar.getMaxValue());
		
		if(level < Experience.getMaxLevel())
			level++;
		levelText.updateTextToLeftOf("LVL: " + level);
		

		switch (level) {
			case 2:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Medium Difficulty!");
				break;
			case 3:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Horror Fish!");
				break;
			case 4:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Extra Life!");
				break;
			case 5:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Hard Difficulty!");
				break;
			case 6:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Third Fish!");
				break;
			case 7:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Extra Life!");
				break;
			case 8:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Crazy Difficulty!");
				break;
			case 9:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Fourth Fish!");
				break;
			case 10:
				unlockedText.updateTextCenteredHorizzontally("You unlocked: Extra Life!");
				break;
			default:
				break;
		}
	}
	
	@Override
	public void update(float delta){
		experienceBar.update(delta);
		numCoinPlay++;
		if(Settings.SOUND_ENABLED && (numCoinPlay % 4 == 0) && experienceBar.getRenderingValue() != experienceBar.getValue()-experienceBar.getOffset()){
			SoundLoader.collect_coin.play();
			numCoinPlay = 0;
		}
		
		experienceText.updateText("Experience: " + (int)experienceBar.getRealCurrentValue());

		if(experienceBar.getRenderingValue() >= experienceBar.getMaxValue()){
			onLevelPassed();
		}
	}
}
