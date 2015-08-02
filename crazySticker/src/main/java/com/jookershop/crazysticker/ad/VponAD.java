package com.jookershop.crazysticker.ad;

import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.util.Message;
import com.vpadn.ads.VpadnAd;
import com.vpadn.ads.VpadnAdListener;
import com.vpadn.ads.VpadnAdRequest;
import com.vpadn.ads.VpadnAdRequest.VpadnErrorCode;
import com.vpadn.ads.VpadnAdSize;
import com.vpadn.ads.VpadnBanner;
import com.vpadn.ads.VpadnInterstitialAd;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

public class VponAD {
	private static VpadnBanner vpadnBanner;
	public static String id = "8a8081824c228f08014c347ef56014a3";

	private static String interstitialBannerId = "8a8081824c228f08014c5992dd9138b4";
	private static VpadnInterstitialAd interstitialAd;
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		vpadnBanner = new VpadnBanner((Activity) context, id, VpadnAdSize.SMART_BANNER,
				"TW");

		VpadnAdRequest adRequest = new VpadnAdRequest();
		adRequest.setEnableAutoRefresh(true);
		vpadnBanner.loadAd(adRequest);
		vpadnBanner.setAdListener(new VpadnAdListener() {
			@Override
			public void onVpadnDismissScreen(VpadnAd arg0) {
				Log.d("vpon", "onVpadnDismissScreen");
			}
 
			@Override
			public void onVpadnFailedToReceiveAd(VpadnAd arg0, VpadnAdRequest.VpadnErrorCode arg1) {
				Log.d("vpon", "onVpadnFailedToReceiveAd:" + arg1);
				adr.setLoad(false);
			}
 
			@Override
			public void onVpadnLeaveApplication(VpadnAd arg0) {
				Log.d("vpon", "onVpadnLeaveApplication");
				adr.setClick(true);
			}
 
			@Override
			public void onVpadnPresentScreen(VpadnAd arg0) {
				adr.setClick(true);
				Log.d("vpon", "onVpadnPresentScreen");
			}
 
			@Override
			public void onVpadnReceiveAd(VpadnAd arg0) {
				Log.d("vpon", "onVpadnReceiveAd");
				adr.setLoad(true);
			}
 
		});		


		adBannerLayout.addView(vpadnBanner);		
	}
	
	public static void shutdown() {
		if(vpadnBanner != null) {
			vpadnBanner.destroy();
			vpadnBanner = null;
		}
		if(interstitialAd != null) {
			interstitialAd.destroy();
			interstitialAd = null;
		}
	}
	
	public static void showIter(final Context context, final ADResult adr) {
		interstitialAd = new VpadnInterstitialAd((Activity) context, interstitialBannerId, "TW");
		//加入listener
        interstitialAd.setAdListener(new VpadnAdListener(){

			@Override
			public void onVpadnDismissScreen(VpadnAd arg0) {
				Log.d(Constants.TAG, "onVpadnDismissScreen");
			}

			@Override
			public void onVpadnFailedToReceiveAd(VpadnAd arg0,
					VpadnErrorCode arg1) {
				Log.d(Constants.TAG, "onVpadnFailedToReceiveAd");
				adr.setLoad(false);
				Message.ShowMsgDialog(context, "目前沒有其他廣告可以讓您收看");
			}

			@Override
			public void onVpadnLeaveApplication(VpadnAd arg0) {
				Log.d(Constants.TAG, "onVpadnLeaveApplication");
				adr.setClick(true);

			}

			@Override
			public void onVpadnPresentScreen(VpadnAd arg0) {
				Log.d(Constants.TAG, "onVpadnPresentScreen");
//				adr.setClick(true);
				
			}

		  @Override
			public void onVpadnReceiveAd(VpadnAd ad) {
//				if (ad == interstitialAd) {
//					adr.setLoad(true);
					interstitialAd.show();
//				}
			}
        });
        
        VpadnAdRequest request = new VpadnAdRequest();
		interstitialAd.loadAd(request);		
	}
	
}
