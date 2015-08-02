package com.jookershop.crazysticker.ad;

import android.content.Context;
import android.util.Log;

import com.jookershop.crazysticker.Constants;

import net.slidingmenu.tools.os.EarnPointsOrderList;
import net.slidingmenu.tools.os.PointsEarnNotify;

//import net.youmi.android.offers.EarnPointsOrderList;
//import net.youmi.android.offers.PointsEarnNotify;

/**
 * Created by Penny_Weng on 2015/6/17.
 */
public class YoumiPointEarn implements PointsEarnNotify {
    @Override
    public void onPointEarn(Context context, EarnPointsOrderList list) {
        int earnPoint =0;
        for(int index = 0; index < list.size(); index ++) {
            earnPoint = earnPoint + list.get(index).getPoints();
        }
        Log.d(Constants.TAG, "YoumiPointsReceiver :" + earnPoint);
        PointUtil.earn(context, earnPoint, PointUtil.POINT_FROM_YUMI);
    }
}
