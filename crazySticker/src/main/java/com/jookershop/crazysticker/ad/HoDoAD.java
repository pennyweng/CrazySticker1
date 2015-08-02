package com.jookershop.crazysticker.ad;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;

import com.hodo.HodoADView;
import com.hodo.listener.HodoADListener;
import com.jookershop.crazysticker.Constants;

/**
 * Created by Penny_Weng on 2015/6/25.
 */
public class HoDoAD {

    public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
        HodoADView hodoAd=new HodoADView(context);

        hodoAd.setListener(new HodoADListener() {
            @Override
            public void onGetBanner() {
                //成功取得banner
            }
            @Override
            public void onFailed(String msg) {
                //失敗取得banner
            }
            @Override
            public void onBannerChange() {
                //banner 切換
            }
        });


        adBannerLayout.addView(hodoAd);
        hodoAd.setBannerPositionType(HodoADView.BANNER_BOTTOM);
        hodoAd.requestAD("558a7331e66");
    }

    public static boolean isShow(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
        return sp.getBoolean("ad_hodo_enable", false);
    }
}
