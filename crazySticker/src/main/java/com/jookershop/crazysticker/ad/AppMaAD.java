package com.jookershop.crazysticker.ad;



import java.util.List;

import com.appma.ad.AppAdView;
import com.appma.ad.AppConnection;
import com.appma.ad.DisplayAdNotifier;
import com.appma.ad.model.AdData;
import com.jookershop.crazysticker.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class AppMaAD {
	private static AppAdView ad = null;

	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		
		if(ad != null) {
			  AppConnection.getInstance(context).onDestory();
		      ad.onDestory();
		      ad = null;
		}	
		
		AppConnection.getInstance(context,adBannerLayout);
		ad = new AppAdView(context,adBannerLayout);
		ad.DisplayAd();
	}
	
	public static void shutdown(Context context) {
	
	}


	public static boolean isShow(Context context) {
        if(Constants.IS_SUPER) return true;
        else {
            SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
            return sp.getBoolean("ad_appma_enable", false);
        }
    }

}
