package com.redfish.jellyjuggler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.redfish.jellyjuggler.JellyJuggler;
import com.redfish.jellyjuggler.adMob.AdsController;

// AndroidLauncher is responsible for launching the game from Android
public class AndroidLauncher extends AndroidApplication implements AdsController {
    // The Key to display the ADs
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-5519384153835422/2811367393";
    // The View to display the ADs
	private AdView bannerAd;
    // The View for the game
	private View gameView;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Configuration class
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        // Setting the gameView to JellyJuggler with the config class
		gameView=initializeForView(new JellyJuggler(this),config);
        // Using the ads method
		setupAds();
        // Creating a relative layout and adding the gameView to the relative layout
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
        // Setting the params for the adView
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // Adding the AdView to the relative layout
		layout.addView(bannerAd, params);
        // Setting the content to layout
		setContentView(layout);
	}
    // The setup for the ads
	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}
    // Running the Bannerad on the UI thread
	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});
	}
    // Hiding the banner by making it invisible
	@Override
	public void hideBannerAd() {

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}
    // Checking if wifi is connected on the android device
	@Override
	public boolean isWifiConnected() {
		ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return ( ni!=null&&ni.isConnected() );
	}
}
