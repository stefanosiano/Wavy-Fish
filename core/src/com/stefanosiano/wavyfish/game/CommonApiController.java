package com.stefanosiano.wavyfish.game;

import com.stefanosiano.common.utils.SimpleCallback;
import com.stefanosiano.wavyfish.utilities.Enums.EnumPermissions;

public interface CommonApiController {
	public void loadInterstitialAd ();
	public void showInterstitialAd ();

	public void showShareProgressBar ();
	public void hideShareProgressBar ();
	public void shareImage (String text, String title, String path, ShareCallback callback);
	public void shareText (String text, String title, ShareCallback callback);
	
	public void sendEvent(String screenName, String category, String action, String label, long value);
	public void sendScreen(String screenName);
	public void sendTiming(String screenName, String category, String variable, String label, long value);
	public void askPermission(EnumPermissions permission, SimpleCallback callback);

	public void showOnCheat ();
}
