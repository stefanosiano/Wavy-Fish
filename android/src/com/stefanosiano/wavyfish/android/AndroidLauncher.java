package com.stefanosiano.wavyfish.android;

import com.crashlytics.android.Crashlytics;
import com.dolby.dap.DolbyAudioProcessing;
import com.dolby.dap.OnDolbyAudioProcessingEventListener;

import com.google.android.gms.ads.MobileAds;
import com.jirbo.adcolony.AdColony;
import com.mopub.common.MoPub;
import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.privacy.ConsentDialogListener;
import com.mopub.common.privacy.PersonalInfoManager;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.stefanosiano.common.utils.SimpleCallback;
import com.stefanosiano.wavyfish.Keys;
import com.stefanosiano.wavyfish.android.AnalyticsHelper.TrackerName;
import com.stefanosiano.wavyfish.android.PermissionUtil.OnPermissionRequested;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.game.ShareCallback;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.utilities.Enums.EnumPermissions;

import io.fabric.sdk.android.Fabric;

public class AndroidLauncher extends AndroidApplication implements CommonApiController, OnDolbyAudioProcessingEventListener, MoPubInterstitial.InterstitialAdListener {
	public static boolean TESTMODE = false;
	
	private AdsUtil adsUtil;
	private MoPubInterstitial mInterstitial;
	private ItemSharer itemSharer;
	private AnalyticsHelper analyticsHelper;
	private SimpleCallback permissionCallback;

	public static final int requestCodeShareLink = 1;
	public static final int requestCodeShareImage = 2;
    private DolbyAudioProcessing dolbyAudioProcessing;
	private AlertDialog.Builder cheatDialog;
	private boolean isRunning = false;
    
    private final String TAG = "WavyFish Application";

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobileAds.initialize(this, Keys.ADMOB_APP_ID);
		MoPub.initializeSdk(this, new SdkConfiguration.Builder(Keys.INTERSTITIAL_AD_UNIT_ID).build(), null);
		
		if(!TESTMODE){
			Fabric.with(this, new Crashlytics());
            MoPub.onCreate(this);
		}
        else{
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

		mInterstitial = new MoPubInterstitial(this, Keys.INTERSTITIAL_AD_UNIT_ID);
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

        final PersonalInfoManager mPersonalInfoManager = MoPub.getPersonalInformationManager();
		if( mPersonalInfoManager.shouldShowConsentDialog() )
            mPersonalInfoManager.loadConsentDialog(new ConsentDialogListener() {
                @Override public void onConsentDialogLoaded() { if (mPersonalInfoManager != null) mPersonalInfoManager.showConsentDialog(); }
                @Override public void onConsentDialogLoadFailed(@NonNull MoPubErrorCode moPubErrorCode) {}
            });
		initialize(new WavyFishGame(this), config);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		if(!TESTMODE){
		    GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStop(this);
		}
        MoPub.onStop(this);
	}

	@Override
	public void onStart(){
		super.onStart();
		isRunning = true;
		if(!TESTMODE){
	        GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStart(this);
		}
        MoPub.onStart(this);
	}

    @Override
    public void onRestart() {
        super.onRestart();
        MoPub.onRestart(this);
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		isRunning = false;
        MoPub.onPause(this);
        AdColony.pause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
        MoPub.onResume(this);
        AdColony.resume(this);
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoPub.onBackPressed(this);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
        MoPub.onDestroy(this);
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
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
		PermissionUtil.permissionsRequested(this, EnumPermissions.shareScore, requestCode, permissions, grantResults, new OnPermissionRequested() {
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
		if(PermissionUtil.checkPermission(this, permission))
			callback.onActionCompleted(true);
		else{
			permissionCallback = callback;
			PermissionUtil.askPermission(this, permission);
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
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adsUtil.loadInterstitialAd();
                }
            });
    }

    @Override
    public void showInterstitialAd() {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
					if(isRunning)
                    	adsUtil.showInterstitialAd();
                }
            });
    }

    @Override
    public void loadRewardedVideoAd() {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adsUtil.loadRewardedVideo();
                }
            });
    }

    @Override
    public void showRewardedVideoAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
				if(isRunning)
                	adsUtil.showRewardedVideo();
            }
        });
    }

    @Override
    public boolean isRewardedVideoLoaded() {
        return adsUtil.isRewardedVideoLoaded();
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
				if(isRunning) {
					adsUtil.showInterstitialAd();
					cheatDialog.show();
				}
			}
		});
	}

	@Override
    public void onDolbyAudioProcessingEnabled(boolean on) {
        Log.i(TAG, "onDolbyAudioProcessingEnabled(" + on + ")");
    }
	@Override
    public void onDolbyAudioProcessingProfileSelected(DolbyAudioProcessing.PROFILE profile) {
        Log.i(TAG, "onDolbyAudioProcessingProfileSelected(" + profile + ")");
    }
	@Override
    public void onDolbyAudioProcessingClientConnected() {
        Log.i(TAG, "onDolbyAudioProcessingClientConnected()");
    }
	@Override
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
