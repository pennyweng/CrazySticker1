package com.jookershop.crazysticker.ad;



//import net.youmi.android.banner.AdSize;
//import net.youmi.android.banner.AdView;
//import net.youmi.android.banner.AdViewListener;
//import net.youmi.android.offers.OffersManager;
//import net.youmi.android.offers.PointsManager;
//import net.youmi.android.banner.AdView;
//import net.youmi.android.banner.AdViewListener;
import net.slidingmenu.tools.br.AdSize;
import net.slidingmenu.tools.br.AdView;
import net.slidingmenu.tools.br.AdViewListener;
import net.slidingmenu.tools.os.OffersManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class YoumiAD {
	
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		AdView adView = new AdView(context, AdSize.FIT_SCREEN);
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				adr.setLoad(false);
				Log.d("YoumiAD", "onFailedToReceivedAd");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				adr.setLoad(true);
				Log.d("YoumiAD", "onReceivedAd");
				
			}

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.d("YoumiAD", "onSwitchedAd");
			}

		});		
		adBannerLayout.addView(adView);
		
	}
	
	public static void reportTotal(Context context) {
//		int totalPoint = PointsManager.getInstance(context).queryPoints();
//		if(totalPoint > 0)
//		PointUtil.report(context, totalPoint, PointUtil.POINT_FROM_YUMI);		
	}
	
	public static void showWall(Context context) {

		OffersManager.getInstance(context).showOffersWall();
	}
}
