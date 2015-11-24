package com.stefanosiano.wavyfish.game;

import com.stefanosiano.wavyfish.game.AnalyticsEnums.ShareType;

public interface ShareCallback {
	public void onShareCancelled();
	void onShareClicked(String appPackage, ShareType type);
}
