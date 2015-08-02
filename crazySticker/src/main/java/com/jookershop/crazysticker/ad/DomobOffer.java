package com.jookershop.crazysticker.ad;

import com.baidu.android.common.logging.Log;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.util.AccountUtil;

import cn.aow.android.DAOW;
import cn.aow.android.DListener;
import android.content.Context;

public class DomobOffer {
	public static String PUBLISHID = "96ZJ2idgzeUWnwTCfE";

	public static void init(Context ctx) {
		DAOW.getInstance(ctx).init(PUBLISHID, AccountUtil.getUid(ctx));
	}

	public static void spend(Context ctx, int point) {
		
		DAOW.getInstance(ctx).consumePoints(point, new DListener() {
			@Override
			public void onError(String error) {
			}

			@Override
			public void onResponse(Object... point) { // 积分消费的状态
				int status = (Integer) point[0];
				

				Integer totalPoint = (Integer) point[2]; // 用户总的积分数
				Integer consumPoint = (Integer) point[1];// 用户的已消费积分数
				int lastPoint = totalPoint - consumPoint;// 用户的剩余积分数
				
				switch (status) {
				case 1: // 消费成功
					break;
				case 2: // 积分不变
					break;
				case 3: // 积分不变
					break;
				}
			}
		});
	}
	
	public static void reportTotal(final Context ctx) {
		DAOW.getInstance(ctx).checkPoints(new DListener() {
			@Override
			public void onError(String error) {
			}

			@Override
			public void onResponse(Object... point) { // 用户总的积分数
				Integer totalPoint = (Integer) point[1]; // 用户的已消费积分数
				Log.d(Constants.TAG, "DomobOffer totalPoint:" + totalPoint);
				
				if(totalPoint > 0)
				PointUtil.report(ctx, totalPoint, PointUtil.POINT_FROM_DOMO);
//				Integer consumPoint = (Integer) point[0]; // 用户的剩余积分数
//				int lastPoint = totalPoint - consumPoint;

				// tv_point.setText("总积分:" + totalPoint + "\n已消费积分:"+
				// consumPoint + "\n剩余积分:" + lastPoint);
			}
		});		
	}

	public static void show(final Context ctx) {
		DAOW.getInstance(ctx).show(ctx);
	}
}
