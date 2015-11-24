package com.stefanosiano.wavyfish.android;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdsUtil {
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-7415586219687762/7423646733";
	private InterstitialAd adMobInterstitialAd;
	private AdRequest adRequest;

	
	public AdsUtil(Context context) {
		adMobInterstitialAd = new InterstitialAd(context);
		adMobInterstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
		
		AdRequest.Builder builder = new AdRequest.Builder();
		builder = builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
		adRequest = builder.build();
	}
	
	
  	public void loadInterstitialAd() {
  		loadAdmob();
	}
	

  	public void showInterstitialAd() {
	    if (adMobInterstitialAd.isLoaded()) {
			Gdx.app.log("Android launcher", "showing admob ad!");
			adMobInterstitialAd.show();
	    }
	    else
			Gdx.app.log("Android launcher", "not yet loaded");
	}
  	
  	private void loadAdmob(){
		if(adMobInterstitialAd.isLoaded())
			return;
		else
			adMobInterstitialAd.loadAd(adRequest);
		/*
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdFailedToLoad(int errorCode) {
				super.onAdFailedToLoad(errorCode);
				Gdx.app.log("Android launcher", "failed to load");
			}
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Gdx.app.log("Android launcher", "loaded!");
			}
		});*/
  	}
}
