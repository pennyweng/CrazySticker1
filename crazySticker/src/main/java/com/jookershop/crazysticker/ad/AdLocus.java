package com.jookershop.crazysticker.ad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.adlocus.*;

import com.jookershop.crazysticker.Constants;

/**
 * Created by Penny_Weng on 2015/6/17.
 */
public class AdLocus {
    public static String adId = "a46d2bb016065f8253e21d42b772b82a275ccef7";


//    public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
//        AdLocusLayout alLayout = new AdLocusLayout((Activity)context,
//                AdLocusLayout.AD_SIZE_BANNER, adId,15
//        );
//
//        alLayout.setTransitionAnimation(AdLocusLayout.ANIMATION_RANDOM);
//        LinearLayout.LayoutParams alParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        alParams.gravity = Gravity.CENTER;
//
////        alLayout.setListener(new AdListener()
////        {
////            @Override
////            public void onReceiveAd(Ad adView) //取得廣告
////            {
////                AdLocusLayout layout = (AdLocusLayout)adView; //casting
////            }
////
////            @Override
////            public void onFailedToReceiveAd(Ad adView, ErrorCode errorCode)
////            {
////                AdLocusLayout layout = (AdLocusLayout)adView; //casting,
////
////                switch (errorCode)
////                {
////                    case NO_FILL:          //無廣告展示.
////                        break;
////                    case INVAILD_KEY:      //錯誤的APP KEY.
////                        break;
////                    case NETWORK_ERROR:    //網路連線問題
////                        break;
////                    case SERVICE_ERROR:    //伺服器忙碌或維護中
////                        break;
////                    default:
////                        break;
////                }
////            }
////        });
//
//        adBannerLayout.addView(alLayout, alParams);
//        adBannerLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//        adBannerLayout.invalidate();
//    }
//
//    public static boolean isShow(Context context) {
//        if(Constants.IS_SUPER) return true;
//        else {
//            SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
//            return sp.getBoolean("ad_locus_enable", false);
//        }
//    }
}
