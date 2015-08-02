package com.jookershop.crazysticker;

import java.net.URLEncoder;
import java.util.Random;









import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;



import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.ad.ADResult;
import com.jookershop.crazysticker.ad.AdBuddizAD;
import com.jookershop.crazysticker.ad.AdColonyAD;
import com.jookershop.crazysticker.ad.AdLocus;
import com.jookershop.crazysticker.ad.AdPlay;
import com.jookershop.crazysticker.ad.AdmobAD;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.AppUnionAD;
import com.jookershop.crazysticker.ad.AppnextAD;
import com.jookershop.crazysticker.ad.AxonixAD;
import com.jookershop.crazysticker.ad.CocoAD;
import com.jookershop.crazysticker.ad.DomobAD;
import com.jookershop.crazysticker.ad.DomobOffer;
import com.jookershop.crazysticker.ad.FlurryAD;
import com.jookershop.crazysticker.ad.HoDoAD;
import com.jookershop.crazysticker.ad.Leadbolt;
import com.jookershop.crazysticker.ad.MillennialAD;
import com.jookershop.crazysticker.ad.Mobile159DAD;
import com.jookershop.crazysticker.ad.QumiAD;
import com.jookershop.crazysticker.ad.StartAppAD;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.ad.VungleAD;
import com.jookershop.crazysticker.ad.YoumiAD;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import net.slidingmenu.tools.st.SpotManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClickADActivity extends Activity {
	private SharedPreferences sp;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//		KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//		lock.disableKeyguard();

//		SpotManager.getInstance(this).loadSpotAds();
//		SpotManager.getInstance(this).setSpotOrientation(
//				SpotManager.ORIENTATION_PORTRAIT);
//		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);

		super.onCreate(savedInstanceState);
//		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
//		super.onAttachedToWindow();

		setContentView(R.layout.all_ads2);

//		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//		KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//		lock.disableKeyguard();

		sp = this.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).apply();
		
		final GiftItem gi= (GiftItem) this.getIntent().getExtras().getSerializable("gi");

		final ADResult adr0 = new ADResult();
		final ADResult adr1 = new ADResult();
		final ADResult adr2 = new ADResult();
		final ADResult adr3 = new ADResult();
		final ADResult adr4 = new ADResult();
		final ADResult adr5 = new ADResult();
		final ADResult adr6 = new ADResult();
		final ADResult adr7 = new ADResult();
		
		Button v1 = (Button) this.findViewById(R.id.button1);
		v1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				VponAD.showIter(ClickADActivity.this, adr7);
			}
		});

		if(AdPlay.isShowVideo(this)) {
			RelativeLayout adRelativeLayout6 = (RelativeLayout) findViewById(R.id.relativeLayout6);
			AdPlay.showVideo(this, adr6, adRelativeLayout6);
		}

		if(HoDoAD.isShow(this)) {
			RelativeLayout adRelativeLayout5 = (RelativeLayout) findViewById(R.id.relativeLayout5);
			HoDoAD.show(this, adr5, adRelativeLayout5);
		}

		if(AdPlay.isShow(this)) {
			final RelativeLayout adRelativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
			AdPlay.show(this, adr4, adRelativeLayout4);
		}

		RelativeLayout adRelativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
		VponAD.show(this, adr3, adRelativeLayout3);

		if(AdmobAD.isShow(this)) {
			final RelativeLayout adRelativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
			AdmobAD.show(this, adr2, adRelativeLayout2);
		}

		if(Mobile159DAD.isShow(this)) {
			final RelativeLayout adRelativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
			Mobile159DAD.show(this, adr1, adRelativeLayout1);
		}

		if(AppMaAD.isShow(this)) {
			final RelativeLayout adRelativeLayout0 = (RelativeLayout) findViewById(R.id.relativeLayout0);
			AppMaAD.show(this, adr0, adRelativeLayout0);
		}

		Button cancel = (Button) findViewById(R.id.Button03);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sp.edit().putInt("tt", 0).apply();
				ClickADActivity.this.onBackPressed();
			}
		});
		
		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sp.getInt("tt", 0) < 2) {
                    boolean show = Message.ShowReminderMsgDialog(ClickADActivity.this, "無法送出","參加資格\n1.確定畫面上有兩個以上您有興趣的廣告，若沒有請下次再來\n2.挑選其中一則有興趣的廣告，點開它。會有下列三種可能\n    a.引導您到Play Store：您可以選擇下載或直接返回瘋貼圖\n    b.出現影音廣告：耐心看完之後，再點選廣告本身，跳到廠商的網站，在返回瘋貼圖\n    c.出現遊戲試玩廣告：：請點選開始試玩，之後再到Play Store下載頁面。您可以選擇下載或直接返回瘋貼圖\n3.返回瘋貼圖後，在完成另一則有興趣的廣告\n4.都完成後再點選送出", "instruction");
                    if(!show) {
					    Message.ShowMsgDialog(ClickADActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
                    }
				} else {
					final String uid = URLEncoder.encode(AccountUtil.getUid(ClickADActivity.this));
					String url = "";
					
					if(gi.getType().equals(GiftItem.TYPE_LINE))
						url = Constants.BASE_URL + "gift/line/nplay?uid=" + uid 
								+ "&lgid=" + gi.getId() 
								+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else if(gi.getType().equals(GiftItem.TYPE_MONEY))
						url = Constants.BASE_URL + "gift/money/nplay?uid=" + uid 
						+ "&lgid=" + gi.getId() 
						+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else if(gi.getType().equals(GiftItem.TYPE_BAG))
						url = Constants.BASE_URL + "gift/bag/nplay?uid=" + uid 
						+ "&lgid=" + gi.getId() 
						+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else  if(gi.getType().equals(GiftItem.TYPE_SE))
						url = Constants.BASE_URL + "gift/se/nplay?uid=" + uid 
						+ "&lgid=" + gi.getId() 
						+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else  if(gi.getType().equals(GiftItem.TYPE_LINE_TOPIC))
						url = Constants.BASE_URL + "gift/line_topic/play?uid=" + uid
								+ "&lgid=" + gi.getId()
								+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else return;
					
					Log.d(Constants.TAG, "gift play url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeString(get,
							new AsyncHttpClient.StringCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source,
										final String result) {
									
									if (e != null ) {
										Message.ShowMsgDialog(ClickADActivity.this,
												"Opps....發生錯誤, 請稍後再試！");
										if(e != null)
										e.printStackTrace();
										return;
									}
//									if(source != null && source.getHeaders() != null
//											&& source.getHeaders().getHeaders() != null
//											&& source.getHeaders().getHeaders().getResponseCode() != 200) 	{
//										Message.ShowMsgDialog(ClickADActivity.this,
//												"Opps....發生錯誤, 請稍後再試！");
//										return;
//									}
									((Activity) ClickADActivity.this)
											.runOnUiThread(new Runnable() {
												public void run() {
													if (result.indexOf("err:4") != -1)
														Message.ShowMsgDialog(
																ClickADActivity.this,
																"此個人獎勵已經不存在");
													else if (result
															.indexOf("err:3") != -1) {
														String[] r = result
																.split(":");
														if (r.length == 3) {
															int s = Integer
																	.parseInt(r[2]) / 60000;
															Message.ShowMsgDialog(
																	ClickADActivity.this,
																	"需過"
																			+ s
																			+ "分鐘才能再玩一次");
														}
													} else if(result.indexOf("achived") != -1) {
														Builder MyAlertDialog = new AlertDialog.Builder(
																ClickADActivity.this);
														
														String uid = AccountUtil.getUid(ClickADActivity.this);
														String rec = gi.getId().substring(0, 3) + "_" + uid.substring(0,3);
														if(gi.getCode() != null)
															rec = gi.getCode();
														
														MyAlertDialog
																.setMessage("恭喜您完成了此個人獎勵。請儘速到完成獎勵區兌換。");
														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																ClickADActivity.this.onBackPressed();
															}
														};
														MyAlertDialog
																.setNeutralButton(
																		"確認",
																		OkClick);
														MyAlertDialog.show();														
													} else {
														
														Builder MyAlertDialog = new AlertDialog.Builder(
																ClickADActivity.this);
														MyAlertDialog
																.setMessage("目前您已經參與了"+ result +"次。過一陣子歡迎繼續參與此個人獎勵。" );
														
														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																ClickADActivity.this.onBackPressed();
															}
														};
														MyAlertDialog
																.setNeutralButton(
																		"確認",
																		OkClick);
														MyAlertDialog.show();
													}
												}
											});
								}
							});
				}

			}
		});

	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
		VponAD.shutdown();
	}

	protected void onResume() {
		super.onResume();


	}


//	@Override
//	public void onAttachedToWindow()
//	{
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//		super.onAttachedToWindow();
//	}

	@Override
	protected void onStop() {
		SpotManager.getInstance(this).onStop();

		super.onStop();
		Log.d(Constants.TAG, "onStop");

//        if(isScreenOn(ClickADActivity.this)) {
            sp.edit().putInt("tt", sp.getInt("tt", 0) + 1).commit();
//        }

		Log.d(Constants.TAG, "tt:" + sp.getInt("tt", 0));		
	}


    public boolean isScreenOn(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            boolean screenOn = false;
            for (Display display : dm.getDisplays()) {
                if (display.getState() != Display.STATE_OFF) {
                    screenOn = true;
                }
            }
            return screenOn;
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //noinspection deprecation
            return pm.isScreenOn();
        }
    }

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		Log.d(Constants.TAG, "ClickADActivity show onUserLeaveHint!!");
	}


//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		//  KeyEvent.KEYCODE_HOME  KeyEvent.KEYCODE_APP_SWITCH
//		if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
//			Log.d(Constants.TAG, "ClickADActivity press home/back");
//
//			return true;
//		} else
//			return super.onKeyDown(keyCode, event);
//	}

}
