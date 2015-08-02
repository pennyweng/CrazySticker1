package com.jookershop.crazysticker.ad;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RelativeLayout;

import com.Mobile159.Mobile159adContainer;
import com.jookershop.crazysticker.Constants;

/**
 * Created by Penny_Weng on 2015/6/17.
 */
public class Mobile159DAD {
    public static String adId = "31378";
    private static Mobile159adContainer ad;


    public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {

        if (ad != null) {
            ad.destroy();
        }

        Log.d(Constants.TAG, "Mobile159DAD show");
        ad = new Mobile159adContainer(context, adId);//@param 應用程式ID
        adBannerLayout.addView(ad);
        ad.getBamnnreAd("1", Mobile159adContainer.setAnimation(6));

        ad.setOnMobile159adListener(new Mobile159adContainer.OnMobile159adListener() {

            @Override
            public void OnStop(int state) {
                switch (state) {
                    case Mobile159adContainer.PAGE_NAME_INVALID:
                        Log.i(Constants.TAG, "Mobile159DAD 應用程式填入之版位名稱(ID)錯誤，或是開發商將版位關閉");
                        break;
                    case Mobile159adContainer.SERVICE_STOP:
                        Log.i(Constants.TAG, "Mobile159DAD Server服務正常，目前沒有廣告做投放");
                        adr.setLoad(false);
                        break;
                    case Mobile159adContainer.DESTROY_159:
                        Log.i(Constants.TAG, "Mobile159DAD 廣告套建生命週期結束，destroy方法呼叫");
                        break;
                }
            }

            @Override
            public void OnChange(int state) {
                switch (state) {
                    case Mobile159adContainer.AD_ROTATE:
                        adr.setLoad(true);
                        break;
                    case Mobile159adContainer.NETWORK_CHECK:
                        break;
                    case Mobile159adContainer.AD_CLICK:
                        Log.i(Constants.TAG, "Mobile159DAD 廣告點擊行為");
                        adr.setClick(true);
                        break;
                }
            }

            @Override
            public void OnStart(int state) {
                if (state == Mobile159adContainer.CREATE_159) {
                    Log.i("", "Mobile159DAD create，一般監聽不到");
                }
            }
        });


    }

    public static boolean isShow(Context context) {
        if(Constants.IS_SUPER) return true;
        else {
            SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
            return sp.getBoolean("ad_mob_159_enable", false);
        }
    }
}
