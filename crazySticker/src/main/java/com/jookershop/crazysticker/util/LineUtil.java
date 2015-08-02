package com.jookershop.crazysticker.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Penny_Weng on 2015/6/18.
 */
public class LineUtil {

    public static void launchWeb(Context mContext, String s) {
        //https://store.line.me/stickershop/product/1016478/zh-Hant
        //https://sdl-stickershop.line.naver.jp/products/0/0/1/1016478/LINEStorePC/thumbnail_shop.png
        try {
            String[] ii = s.substring(0, s.indexOf("LINEStorePC") - 1).split("/");
            String url = "https://store.line.me/stickershop/product/" + ii[ii.length - 1] + "/zh-Hant";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            mContext.startActivity(i);
        } catch (Throwable ee) {}
    }

    public static void launchTopicWeb(Context mContext, String s) {
        //https://store.line.me/themeshop/product/91411cfe-6171-4f6b-96e5-4d5f7382555b/zh-Hant
        //https://sdl-shop.line.naver.jp/themeshop/v1/products/91/41/1c/91411cfe-6171-4f6b-96e5-4d5f7382555b/11/WEBSTORE/icon_136x190.png
        try {
            String[] ii = s.split("/");
            if(ii.length > 9) {
                String url = "https://store.line.me/themeshop/product/" + ii[9] + "/zh-Hant";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        } catch (Throwable ee) {}
    }
}
