package com.jookershop.crazysticker.ad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.adlocus.AdLocusLayout;
import com.jookershop.crazysticker.Constants;
import com.vm5.adn.api.AdRequest;
import com.vm5.adn.api.AdSize;
import com.vm5.adn.api.AdnView;

/**
 * Created by Penny_Weng on 2015/6/17.
 */
public class AdPlay {
    public static String testKey = "TEST-JSAdways";
    public static String releaseKey = "47cac0eff6a184a9b7b27a747c2a0816";
    public static String adId = releaseKey;



    public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
            AdnView mAdnView = new AdnView(context, adId, AdSize.BANNER);

            AdRequest req = new AdRequest.Builder(context)
                    .AdType(AdRequest.TYPE_BANNER)
                    .Format(AdRequest.REQ_TYPE_PLAYABLE)
                    .Orientation(AdRequest.ORIENTATION_PORTRAIT)
                    .build();
            adBannerLayout.addView(mAdnView);
            mAdnView.loadAd(req);
    }

    public static void showVideo(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
            AdnView mAdnView = new AdnView(context, adId, AdSize.BANNER);
            AdRequest req = new AdRequest.Builder(context)
                    .AdType(AdRequest.TYPE_BANNER)
                    .Format(AdRequest.REQ_TYPE_VIDEO)
                    .build();

            adBannerLayout.addView(mAdnView);
            mAdnView.loadAd(req);
    }

    public static boolean isShow(Context context) {
        if(Constants.IS_SUPER) return true;
        else {
            SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
//            adId = sp.getString("ad_play_key", releaseKey);
            return sp.getBoolean("ad_play_enable", false) && !adId.equals(testKey);
        }
    }

    public static boolean isShowVideo(Context context) {
        if(Constants.IS_SUPER) return true;
        else {
            SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
//            adId = sp.getString("ad_play_key", releaseKey);
            return sp.getBoolean("ad_play_video_enable", false) && !adId.equals(testKey);
        }
    }

}
