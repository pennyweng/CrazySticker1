package com.jookershop.crazysticker.ad;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.Constants;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RelativeLayout;

public class AdmobAD {
	public static String unitId = "ca-app-pub-4380704232895794/6822774865";
	public static String unitId1 = "ca-app-pub-4380704232895794/3083038461";

	public static void show(Context context, final ADResult adr,
			RelativeLayout adBannerLayout) {
		showInternal(context, adr, adBannerLayout, unitId);
	}

	public static void show1(Context context, final ADResult adr,
			RelativeLayout adBannerLayout) {
		showInternal(context, adr, adBannerLayout, unitId1);
	}

	public static void showInternal(final Context context, final ADResult adr,
			RelativeLayout adBannerLayout, String unit) {
		try {

			final AdView adView = new AdView(context);
			adView.setAdUnitId(unit);
			adView.setAdSize(AdSize.SMART_BANNER);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					super.onAdClosed();
					Log.d(Constants.TAG, "google ad onAdClosed");
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					// TODO Auto-generated method stub
					super.onAdFailedToLoad(errorCode);
					adr.setLoad(false);
				}

				@Override
				public void onAdLeftApplication() {
					// TODO Auto-generated method stub
					super.onAdLeftApplication();
					Log.d(Constants.TAG, "google ad onAdLeftApplication");
					adr.setClick(true);
				}

				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
					Log.d(Constants.TAG, "google ad load");
					adr.setLoad(true);

				}

				@Override
				public void onAdOpened() {
					super.onAdOpened();
					Log.d(Constants.TAG, "google ad onAdOpened");
					adr.setClick(true);
					setClick(context);
				}

			});

			adBannerLayout.addView(adView);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static boolean isShow(Context context) {
		SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		long ll = sp.getLong("admob_show", 0l);
		return System.currentTimeMillis() - ll > sp.getLong("admob_period", 86400000);
	}

	public static void setClick(Context context) {
		SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putLong("admob_show", System.currentTimeMillis()).commit();
	}
}
