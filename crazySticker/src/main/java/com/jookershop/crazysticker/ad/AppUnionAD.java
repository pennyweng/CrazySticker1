package com.jookershop.crazysticker.ad;

import com.baidu.ops.appunion.sdk.AppUnionSDK;
import com.baidu.ops.appunion.sdk.banner.BaiduBanner;

import android.content.Context;
import android.widget.RelativeLayout;

public class AppUnionAD {
	public static void init(Context context) {
		AppUnionSDK.getInstance(context).initSdk();
	}
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		BaiduBanner banner = new BaiduBanner (context); 
		adBannerLayout.addView(banner);
	}
	
	public static void stop (Context context) {
		AppUnionSDK.getInstance(context).quitSdk();
	}
}
