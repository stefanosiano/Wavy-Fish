package com.stefanosiano.wavyfish.android;

import com.crashlytics.android.Crashlytics;
import com.dolby.dap.DolbyAudioProcessing;
import com.dolby.dap.OnDolbyAudioProcessingEventListener;

import com.mopub.common.MoPub;
import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.startapp.android.publish.StartAppSDK;
import com.stefanosiano.common.utils.SimpleCallback;
import com.stefanosiano.wavyfish.android.AnalyticsHelper.TrackerName;
import com.stefanosiano.wavyfish.android.PermissionAskerHelper.OnPermissionRequested;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.game.ShareCallback;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.utilities.Enums.EnumPermissions;

import io.fabric.sdk.android.Fabric;

public class AndroidLauncher extends AndroidApplication implements CommonApiController, OnDolbyAudioProcessingEventListener, MoPubInterstitial.InterstitialAdListener {
	public static boolean TESTMODE = false;
	
	private AdsUtil adsUtil;
	private MoPubInterstitial mInterstitial;
	private String mopubID = "04142d059c024d68a2caef5692f616ae";
	private ItemSharer itemSharer;
	private AnalyticsHelper analyticsHelper;
	private SimpleCallback permissionCallback;

	public static final int requestCodeShareLink = 1;
	public static final int requestCodeShareImage = 2;
    private DolbyAudioProcessing dolbyAudioProcessing;
	private AlertDialog.Builder cheatDialog;
    
    private final String TAG = "WavyFish Application";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(!TESTMODE){
			Fabric.with(this, new Crashlytics());
		}
		
		dolbyAudioProcessing = null;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			dolbyAudioProcessing = DolbyAudioProcessing.getDolbyAudioProcessing(getApplicationContext(), DolbyAudioProcessing.PROFILE.GAME, this);
			if(dolbyAudioProcessing != null)
				dolbyAudioProcessing.setEnabled(true);
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = false;
		config.useAccelerometer = false;
		config.useCompass = false;

        StartAppSDK.init(this, "211059130", false);
		mInterstitial = new MoPubInterstitial(this, mopubID);
		mInterstitial.setInterstitialAdListener(this);
		adsUtil = new AdsUtil(this, mInterstitial);
		itemSharer = new ItemSharer(this);
		analyticsHelper = new AnalyticsHelper();
		permissionCallback = null;
		

		//AlertDialog to show when a cheat is recognized
		cheatDialog = new Builder(this);
		cheatDialog.setMessage(R.string.cheatDialogMessage);
		cheatDialog.setTitle(R.string.cheatDialogTitle);
		cheatDialog.setCancelable(false);
		
		cheatDialog.setNeutralButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Gdx.app.exit();
			}
		});
		
		initialize(new WavyFishGame(this), config);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		if(!TESTMODE){
		    GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStop(this);
		}
	}

	@Override
	public void onStart(){
		super.onStart();
		if(!TESTMODE){
	        GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStart(this);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mInterstitial != null) {
			mInterstitial.destroy();
			mInterstitial = null;
		}
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	        if(dolbyAudioProcessing != null)
	            dolbyAudioProcessing.release();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
		PermissionAskerHelper.permissionsRequested(this, EnumPermissions.shareScore, requestCode, permissions, grantResults, new OnPermissionRequested() {
			@Override
			public void onPermissionDenied() {
				if (permissionCallback != null) permissionCallback.onActionCompleted(false);
			}

			@Override
			public void onPermissionAccepted() {
				if (permissionCallback != null) permissionCallback.onActionCompleted(true);
			}
		});
	}

	@Override
	public void askPermission(EnumPermissions permission, SimpleCallback callback){
		if(PermissionAskerHelper.checkPermission(this, permission))
			callback.onActionCompleted(true);
		else{
			permissionCallback = callback;
			PermissionAskerHelper.askPermission(this, permission);
		}
	}

	@Override
	public void sendEvent(String screenName, String category, String action, String label, long value){
		if(!TESTMODE)
			analyticsHelper.sendEvent(TrackerName.APP_TRACKER, this, screenName, category, action, label, value);
	}

	@Override
	public void sendScreen(String screenName){
		if(!TESTMODE)
			analyticsHelper.sendScreen(TrackerName.APP_TRACKER, this, screenName);
	}

	@Override
	public void sendTiming(String screenName, String category, String variable, String label, long value){
		if(!TESTMODE)
			analyticsHelper.sendTiming(TrackerName.APP_TRACKER, this, screenName, category, variable, label, value);
	}
	
	@Override
  	public void loadInterstitialAd() {
		if(!TESTMODE)
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adsUtil.loadInterstitialAd();
				}
			});
	}
	
	@Override
  	public void showInterstitialAd() {
		if(!TESTMODE)
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adsUtil.showInterstitialAd();
				}
			});
	}

	@Override
	public void showShareProgressBar() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				itemSharer.showProgressBar();
			}
		});
	}

	@Override
	public void hideShareProgressBar() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				itemSharer.hideProgressBar();
			}
		});
	}

	@Override
	public void shareImage(final String text, final String title, final String path, final ShareCallback callback) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				itemSharer.shareImage(title, text, Uri.fromFile(new File(path)), callback);
			}
		});
	}

	@Override
	public void shareText(final String text, final String title, final ShareCallback callback) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				itemSharer.shareText(title, text, callback);
			}
		});
	}

	@Override
	public void showOnCheat() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adsUtil.showInterstitialAd();
				cheatDialog.show();
			}
		});
	}

    public void onDolbyAudioProcessingEnabled(boolean on) {
        Log.i(TAG, "onDolbyAudioProcessingEnabled(" + on + ")");
    }
    public void onDolbyAudioProcessingProfileSelected(DolbyAudioProcessing.PROFILE profile) {
        Log.i(TAG, "onDolbyAudioProcessingProfileSelected(" + profile + ")");
    }
    public void onDolbyAudioProcessingClientConnected() {
        Log.i(TAG, "onDolbyAudioProcessingClientConnected()");
    }
    public void onDolbyAudioProcessingClientDisconnected() {
        Log.w(TAG, "onDolbyAudioProcessingClientDisconnected()");
    }



	// InterstitialAdListener methods
	@Override
	public void onInterstitialLoaded(MoPubInterstitial interstitial) {
		adsUtil.onInterstitialLoaded(interstitial);
	}

	@Override
	public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {adsUtil.onInterstitialFailed(interstitial, errorCode);}
	@Override
	public void onInterstitialShown(MoPubInterstitial interstitial) {adsUtil.onInterstitialShown(interstitial);}
	@Override
	public void onInterstitialClicked(MoPubInterstitial interstitial) {adsUtil.onInterstitialClicked(interstitial);}
	@Override
	public void onInterstitialDismissed(MoPubInterstitial interstitial) {adsUtil.onInterstitialDismissed(interstitial);}
}
