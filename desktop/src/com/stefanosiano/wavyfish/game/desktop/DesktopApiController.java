package com.stefanosiano.wavyfish.game.desktop;

import com.stefanosiano.common.utils.SimpleCallback;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.game.ShareCallback;
import com.stefanosiano.wavyfish.utilities.Enums.EnumPermissions;

public class DesktopApiController implements CommonApiController {
	@Override
	public void showInterstitialAd() {
		System.out.println("interstitial gneeee");
	}

	@Override
	public void loadInterstitialAd() {
		System.out.println("load interstitial gneeee");
	}

	@Override
	public void showShareProgressBar() {
		System.out.println("show share progress bar gneeee");
	}

	@Override
	public void hideShareProgressBar() {
		System.out.println("hide share progress bar gneeee");
	}

	@Override
	public void shareImage(String text, String title, String path, final ShareCallback callback) {
		System.out.println("share image gneeee");
	}

	@Override
	public void shareText(String text, String title, final ShareCallback callback) {
		System.out.println("share text gneeee");
	}

	@Override
	public void sendEvent(String screenName, String category, String action, String label, long value) {
		System.out.println("send event");
	}

	@Override
	public void sendScreen(String screenName) {
		System.out.println("send screen gneeee");
	}

	@Override
	public void sendTiming(String screenName, String category, String variable, String label, long value) {
		System.out.println("send timing gneeee");
	}

	@Override
	public void askPermission(EnumPermissions permission, SimpleCallback callback) {
		System.out.println("ask permission gneeee");
	}

	@Override
	public void showOnCheat() {
		System.out.println("on cheat gneeee");
	}
}
