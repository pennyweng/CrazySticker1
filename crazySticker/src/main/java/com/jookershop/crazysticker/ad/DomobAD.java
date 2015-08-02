package com.jookershop.crazysticker.ad;

import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdManager.ErrorCode;
import cn.domob.android.ads.AdView;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

public class DomobAD {

//	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
//		AdView mAdview = new AdView((Activity) context, "56OJxX24uN76NWnO+/", "16TLe2TlAp8XONUdLFRGQyPi");
//		mAdview.setAdEventListener(new AdEventListener() {
//			@Override
//			public void onAdOverlayPresented(AdView adView) {
//				Log.i("DomobSDKDemo", "overlayPresented");
//				adr.setLoad(true);
//			}
//			@Override
//			public void onAdOverlayDismissed(AdView adView) {
//				Log.i("DomobSDKDemo", "Overrided be dismissed");
//			}
//			@Override
//			public void onAdClicked(AdView arg0) {
//				Log.i("DomobSDKDemo", "onDomobAdClicked");
//				adr.setClick(true);
//			}
//			@Override
//			public void onLeaveApplication(AdView arg0) {
//				Log.i("DomobSDKDemo", "onDomobLeaveApplication");
//			}
//
//
//			@Override
//			public void onEventAdReturned(AdView arg0) {
//				Log.i("DomobSDKDemo", "onDomobAdReturned");
//				adr.setLoad(true);
//			}
//			@Override
//			public void onAdFailed(AdView arg0, ErrorCode arg1) {
//				adr.setLoad(false);
//
//			}
//			@Override
//			public Context onAdRequiresCurrentContext() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
//		adBannerLayout.addView(mAdview);
//	}
}
