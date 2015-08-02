package com.jookershop.crazysticker.ad;

import java.net.URLEncoder;

import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class PointUtil {
	public static int POINT_FROM_DOMO = 1;
	public static int POINT_FROM_YUMI = 2;
	public static int POINT_FROM_CHMI = 3;
	public static int POINT_FROM_EVERY_DATE = 4;
	public static int POINT_FROM_ADPLAY = 5;
	
	public static void earn(final Context context, final int point, int from) {
		String uid = URLEncoder.encode(AccountUtil.getUid(context));
//		uid :String, point : Long, from : String)
		String url = Constants.BASE_URL + "point/earn?uid=" + uid + "&point=" + point + "&from=" + from ;
		Log.d(Constants.TAG, "current earn url ");
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }

//				try {
//					if (context != null)
//						((Activity) context).runOnUiThread(new Runnable() {
//							public void run() {
//								if (result.indexOf("err") == -1) {
//									String[] rr = result.split("#");
//									Message.ShowMsgDialog(context, "恭喜您得到點數" + point + "點，目前共有" + rr[0] + "點");
//								}
//							}
//						});
//				} catch(Throwable ee) {
//				}
		    }
		});		
	}
	
	public static void earnDaily(final Context context, final boolean goback) {
		String uid = URLEncoder.encode(AccountUtil.getUid(context));
//		uid :String, point : Long, from : String)
		String url = Constants.BASE_URL + "point/earn/daily?uid=" + uid ;
//		Log.d(Constants.TAG, "current earn url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    	
				if(context!= null)
					((Activity) context).runOnUiThread(new Runnable() {
					public void run() {
						if(result.indexOf("err") == -1) {
							String [] rr = result.split("#");
							
							Builder MyAlertDialog = new AlertDialog.Builder(context);
							MyAlertDialog.setMessage("今日簽到所得到的點數為"+ rr[1] + "點，目前共有" +rr[0] +"點");
							DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if(goback) {
										((Activity)context).onBackPressed();
									}
								}
							};
							MyAlertDialog.setNeutralButton("確定", OkClick);
							MyAlertDialog.show();							
						} else if(result.equals("err:1")) {
							Builder MyAlertDialog = new AlertDialog.Builder(context);
							MyAlertDialog.setMessage("今日已經有簽到過了。");
							DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if(goback) {
										((Activity)context).onBackPressed();
									}
								}
							};
							MyAlertDialog.setNeutralButton("確定", OkClick);
							MyAlertDialog.show();							
						}
					}
				});
		    }
		});		
	}
	
	

	public static void report(final Context context, final int totalPoint, int from) {
		String uid = URLEncoder.encode(AccountUtil.getUid(context));
		String url = Constants.BASE_URL + "point/earn/report?uid=" + uid + "&totalPoint=" + totalPoint + "&from=" + from;
		Log.d(Constants.TAG, "current report url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    }
		});		
	}
}
