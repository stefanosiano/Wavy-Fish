package com.stefanosiano.wavyfish.android;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.InterstitialAd;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.stefanosiano.wavyfish.Keys;

import java.util.Set;

public class AdsUtil {
	private InterstitialAd adMobInterstitialAd;
	private MoPubInterstitial mInterstitial;
    private MoPubRewardedVideoListener rewardedVideoListener;

	
	public AdsUtil(Context context, MoPubInterstitial mInterstitial) {
		adMobInterstitialAd = new InterstitialAd(context);
		adMobInterstitialAd.setAdUnitId(Keys.INTERSTITIAL_AD_UNIT_ID);

		this.mInterstitial = mInterstitial;

        rewardedVideoListener = new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(String adUnitId) {
                // Called when the video for the given adUnitId has loaded. At this point you should be able to call MoPub.showRewardedVideo(String) to show the video.
            }
            @Override
            public void onRewardedVideoLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                // Called when a video fails to load for the given adUnitId. The provided error code will provide more insight into the reason for the failure to load.
            }

            @Override
            public void onRewardedVideoStarted(String adUnitId) {
                // Called when a rewarded video starts playing.
            }

            @Override
            public void onRewardedVideoPlaybackError(String adUnitId, MoPubErrorCode errorCode) {
                //  Called when there is an error during video playback.
            }

            @Override
            public void onRewardedVideoClosed(String adUnitId) {
                // Called when a rewarded video is closed. At this point your application should resume.
            }

            @Override
            public void onRewardedVideoCompleted(Set<String> adUnitIds, MoPubReward reward) {
                // Called when a rewarded video is completed and the user should be rewarded.
                // You can query the reward object with boolean isSuccessful(), String getLabel(), and int getAmount().
            }
        };

        MoPub.setRewardedVideoListener(rewardedVideoListener);
	}
	
  	public void loadInterstitialAd() {
        if(!mInterstitial.isReady())
            mInterstitial.load();
	}

    public void loadRewardedVideo() {
        MoPub.loadRewardedVideo(Keys.REWARDED_VIDEO_AD_UNIT_ID);
    }

    public void showRewardedVideo() {
        MoPub.showRewardedVideo(Keys.REWARDED_VIDEO_AD_UNIT_ID);
    }

    public boolean isRewardedVideoLoaded() {
        return MoPub.hasRewardedVideo(Keys.REWARDED_VIDEO_AD_UNIT_ID);
    }

	public void showInterstitialAd() {
		if (mInterstitial.isReady()) {
			mInterstitial.show();
		}
	}


	public void onInterstitialLoaded(MoPubInterstitial interstitial) {mInterstitial = interstitial;}
	public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
		Log.d("ASD", errorCode.toString());}
	public void onInterstitialShown(MoPubInterstitial interstitial) {}
	public void onInterstitialClicked(MoPubInterstitial interstitial) {}
	public void onInterstitialDismissed(MoPubInterstitial interstitial) {}
}
