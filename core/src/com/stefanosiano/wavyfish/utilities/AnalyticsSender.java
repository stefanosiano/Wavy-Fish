package com.stefanosiano.wavyfish.utilities;

import com.stefanosiano.wavyfish.game.AnalyticsEnums.Category;
import com.stefanosiano.wavyfish.game.AnalyticsEnums.ShareType;
import com.stefanosiano.wavyfish.game.ShareCallback;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.utilities.Enums.Difficulty;
import com.stefanosiano.wavyfish.utilities.Enums.GameControl;
import com.stefanosiano.wavyfish.utilities.Enums.GameMode;

public class AnalyticsSender {
	public static void sendPlayGameWithOptions(WavyFishGame game, Difficulty difficulty, GameMode mode, GameControl control, float time){
		sendEvent(game, "GameScreenUpdater", Category.game, "Play game with time", difficulty + " " + mode + " " + control, (long)time);
	}

	public static void sendPlayGame(WavyFishGame game){
		sendEvent(game, "GameScreenUpdater", Category.navigation, "Play game", "", 1);
	}

	public static void sendOpenInfo(WavyFishGame game){
		sendEvent(game, "AnalyticsSender", Category.navigation, "Open info", "", 1);
	}

	public static void sendShare(WavyFishGame game, ShareType type, ShareCallback callback){
		sendEvent(game, "Menu1", Category.share, type.getTypeString(), "", 1);
	}

	public static void sendShareClicked(WavyFishGame game, ShareType type, String appPackage){
		sendEvent(game, "Menu1", Category.share, "Clicked " + type.getTypeString(), appPackage, 1);
	}

	public static void sendGeneralEvent(WavyFishGame game, String action, String label, long value){
		sendEvent(game, "AnalyticsSender", Category.general, action, label, value);
	}

	public static void sendTotalPlayTime(WavyFishGame game, long value){
		if(value <= 0)
			return;
		sendEvent(game, "WavyFishGame", Category.game, "Total play time", "Sent everytime", value);
		if(value > 20){
			sendEvent(game, "WavyFishGame", Category.game, "Total play time > 20", "Only if user played for more than 20 seconds", value);
		}
	}

	public static void sendMeanScore(WavyFishGame game, long value){
		if(value <= 0)
			return;
		sendEvent(game, "GameScreenCommonUpdater", Category.game, "Mean high score", "Sent on all games", value);
		if(value > 5000){
			sendEvent(game, "GameScreenCommonUpdater", Category.game, "Mean high score > 5000", "Sent on games with more than 5000 points", value);
		}
	}

	public static void sendHighScore(WavyFishGame game, long value, long value2){
		if(value <= 0)
			return;
		sendEvent(game, "GameScreenCommonUpdater", Category.game, "High score", "High score of game", value);
		sendEvent(game, "GameScreenCommonUpdater", Category.game, "High score cumulative", "High score - previous one", value2);
	}

	
	private static void sendEvent(WavyFishGame game, String screen, Category category, String action, String label, long value){
		game.getCommonApiController().sendEvent(screen, category.getString(), action, label, value);
	}
	
}
