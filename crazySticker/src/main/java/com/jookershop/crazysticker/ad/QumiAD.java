package com.jookershop.crazysticker.ad;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.newqm.sdkoffer.AdView;
import com.newqm.sdkoffer.QuMiConnect;
import com.newqm.sdkoffer.QuMiNotifier;


public class QumiAD {
	public static void init(Context context) {
//		QuMiConnect.ConnectQuMi(context, "0a8bd13e4d758d51", "403ce56f15691253");
		QuMiConnect.ConnectQuMi(context, "2d7750cb798a3cbe", "0ae3df655a6b860e");
		QuMiConnect.getQumiConnectInstance(context).initOfferAd(context);
		
	}
	
//	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
////		AdView adView = new AdView(context);
////		adBannerLayout.addView(adView);
//		
//		AdView adView = new AdView(context);
//		FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, 
//				FrameLayout.LayoutParams.WRAP_CONTENT);
//		param.bottomMargin = 0;
//		param.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//		
//		adBannerLayout.addView(adView, param);		
//		
//	}
	
	public static void spend(Context context, int point) {
		QuMiConnect.getQumiConnectInstance().spendPoints(new QuMiNotifier() {

			@Override
			public void earnedPoints(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void getPoints(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void getPointsFailed(String arg0) {
				// TODO Auto-generated method stub
				
			}}, point);	
	}
	
	
	public static void showTask(final Context context) {
		QuMiConnect.getQumiConnectInstance().showOffers( new QuMiNotifier() {

			@Override
			public void earnedPoints(int pointTotal, int earnedpoint) {
				PointUtil.earn(context, earnedpoint, PointUtil.POINT_FROM_CHMI);
			}

			@Override
			public void getPoints(int pointTotal) {
				
			}

			@Override
			public void getPointsFailed(String error) {
				
			}
			
		});
	}
}
