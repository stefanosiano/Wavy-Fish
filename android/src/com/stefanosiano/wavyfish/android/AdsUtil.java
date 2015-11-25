package com.stefanosiano.wavyfish.android;

import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

public class AdsUtil {
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-7415586219687762/7423646733";
	private InterstitialAd adMobInterstitialAd;
	private AdRequest adRequest;
	private MoPubInterstitial mInterstitial;

	
	public AdsUtil(Context context, MoPubInterstitial mInterstitial) {
		adMobInterstitialAd = new InterstitialAd(context);
		adMobInterstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
		
		AdRequest.Builder builder = new AdRequest.Builder();
		builder = builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
		adRequest = builder.build();
		this.mInterstitial = mInterstitial;
	}
	
	
  	public void loadInterstitialAd() {
		load();
	}


	public void showInterstitialAd() {
/*		if (adMobInterstitialAd.isLoaded()) {
			Gdx.app.log("Android launcher", "showing admob ad!");
			adMobInterstitialAd.show();
		}
		else
			Gdx.app.log("Android launcher", "not yet loaded");
*/
		if (mInterstitial.isReady()) {
			mInterstitial.show();
		}
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

	private void load(){
		mInterstitial.load();
	}


	public void onInterstitialLoaded(MoPubInterstitial interstitial) {mInterstitial = interstitial;}
	public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
		Log.d("ASD", errorCode.toString());}
	public void onInterstitialShown(MoPubInterstitial interstitial) {}
	public void onInterstitialClicked(MoPubInterstitial interstitial) {}
	public void onInterstitialDismissed(MoPubInterstitial interstitial) {}
}
