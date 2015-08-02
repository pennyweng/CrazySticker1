package com.jookershop.crazysticker.glucky;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.BidItem;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.ad.ADResult;
import com.jookershop.crazysticker.ad.AdLocus;
import com.jookershop.crazysticker.ad.AdPlay;
import com.jookershop.crazysticker.ad.AdmobAD;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.HoDoAD;
import com.jookershop.crazysticker.ad.Mobile159DAD;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.net.URLEncoder;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;

/**
 * A placeholder fragment containing a simple view.
 */
public class GluckyActivity extends Activity {
	private EditText point;
	private EditText nickname;

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Constants.TAG, "onCreate");
		
		setContentView(R.layout.glucky_ads2);
		sp = this.getSharedPreferences(
				Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).apply();

		final FnItem item = (FnItem) getIntent().getSerializableExtra("bid");
		
		point = (EditText) this.findViewById(R.id.editText1);
		nickname = (EditText) this.findViewById(R.id.editText2);

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
				VponAD.showIter(GluckyActivity.this, adr7);
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
				sp.edit().putInt("tt", 0).commit();
				GluckyActivity.this.onBackPressed();
			}
		});
		
		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(nickname.getText().toString().equals("") || point.getText().toString().equals("")) {
					Message.ShowMsgDialog(GluckyActivity.this, "請輸入幸運星藏的數字或者是一些話。");
				} else {
					boolean isExist = false;
					boolean isInVaild = false;
					int s = Integer.parseInt(point.getText().toString());

					if (item.getFnHistory() != null) {
						FnHistory[] ii = item.getFnHistory();
						for (int index = 0; index < ii.length; index++) {
							if (ii[index].isSame(s)) isExist = true;
						}
					}

					if (s > item.getBig() || s < item.getSmall()) isInVaild = true;

					if (isExist) {
						Message.ShowMsgDialog(GluckyActivity.this, "這裡的幸運星別人找過了，換個數字找吧");
					} else if (isInVaild) {
						Message.ShowMsgDialog(GluckyActivity.this, "幸運星藏在" + item.getSmall() + "到" + item.getBig() + "之間");
					} else if (sp.getInt("tt", 0) < 2 && !Constants.IS_SUPER) {
						Message.ShowMsgDialog(GluckyActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
					} else {
						save(item, point.getText().toString(), nickname.getText().toString(), false);
					}
				}
				
			}
		});

		Button quickBt = (Button) findViewById(R.id.Button04);
		quickBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(nickname.getText().toString().equals("") || point.getText().toString().equals("")) {
					Message.ShowMsgDialog(GluckyActivity.this, "請輸入幸運星藏的數字或者是一些話。");
				} else {
					boolean isExist = false;
					boolean isInVaild = false;
					int s = Integer.parseInt(point.getText().toString());

					if (item.getFnHistory() != null) {
						FnHistory[] ii = item.getFnHistory();
						for (int index = 0; index < ii.length; index++) {
							if (ii[index].isSame(s)) isExist = true;
						}
					}

					if (s > item.getBig() || s < item.getSmall()) isInVaild = true;

					if (isExist) {
						Message.ShowMsgDialog(GluckyActivity.this, "這裡的幸運星別人找過了，換個數字找吧");
					} else if (isInVaild) {
						Message.ShowMsgDialog(GluckyActivity.this, "幸運星藏在" + item.getSmall() + "到" + item.getBig() + "之間");
					} else if (sp.getInt("tt", 0) < 2 && !Constants.IS_SUPER) {
						Message.ShowMsgDialog(GluckyActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
					} else {
						save(item, point.getText().toString(), nickname.getText().toString(), false);
					}
				}
			}
		});
	}


	public void save(final FnItem item, String bidValue, String nickName, boolean quick) {
//		boolean isExist = false;
//		boolean isInVaild = false;
//		int s = Integer.parseInt(bidValue);
//
//		if(item.getFnHistory() != null) {
//			FnHistory[] ii = item.getFnHistory();
//			for(int index =0 ;index <ii.length; index ++) {
//				if(ii[index].isSame(s)) isExist = true;
//			}
//		}
//
//		if(s > item.getBig() || s < item.getSmall() ) isInVaild = true;
//
//		if(isExist) {
//			Message.ShowMsgDialog(GluckyActivity.this, "這裡的幸運星別人找過了，換個數字找吧");
//		} else if(isInVaild) {
//			Message.ShowMsgDialog(GluckyActivity.this, "幸運星藏在" + item.getSmall()+"到" + item.getBig()+ "之間");
//		} else {
			String uid = URLEncoder.encode(AccountUtil.getUid(this));
			String url = Constants.BASE_URL + "glucky/play?tid=" + item.getId() + "&uid=" + uid
					+ "&mynumber=" + bidValue + "&msg=" + URLEncoder.encode(nickName)
					+ "&quick=" + quick;
			Log.d(Constants.TAG, "save  url " + url);
			AsyncHttpGet ahg = new AsyncHttpGet(url);
			AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
				@Override
				public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
					if (e != null) {
						e.printStackTrace();
						return;
					}

					GluckyActivity.this
							.runOnUiThread(new Runnable() {
								public void run() {
									if (result.indexOf("err:1") != -1)
										Message.ShowMsgDialog(
												GluckyActivity.this,
												"此幸運星已經不存在");
									else if (result.indexOf("err:3") != -1)
										Message.ShowMsgDialog(
												GluckyActivity.this,
												"此幸運星已經不存在");
									else if (result.indexOf("err:6") != -1)
										Message.ShowMsgDialog(
												GluckyActivity.this,
												"點數不足，無法參加");
									else if (result.indexOf("err:2") != -1)
										Message.ShowMsgDialog(
												GluckyActivity.this,
												"此幸運星已經被人找到了");
									else if (result
											.indexOf("err:5") != -1) {
										String[] r = result
												.split(":");
										if (r.length == 3) {
											int s = Integer
													.parseInt(r[2]) / 60000;
											Message.ShowMsgDialog(
													GluckyActivity.this,
													"需過"
															+ s
															+ "分鐘才能再玩一次");
										}
									} else if (result.equals("lucky")) {
										new MaterialDialog.Builder(GluckyActivity.this)
												.title("系統通知")
												.content("恭喜您找到了幸運星了。請儘速到[完成回報]區去申請此獎勵。也請填寫下面的幸運感言。")
												.positiveColor(Color.parseColor("#636363"))
												.inputMaxLength(15)
												.inputType(InputType.TYPE_CLASS_TEXT)
												.input("幸運感言", "", new MaterialDialog.InputCallback() {
													@Override
													public void onInput(MaterialDialog dialog, CharSequence input) {
														try {
															String uid = URLEncoder.encode(AccountUtil.getUid(GluckyActivity.this));
															String url = Constants.BASE_URL + "glucky/msg?uid=" + uid + "&tid=" + item.getId() + "&msg=" + URLEncoder.encode(input.toString());
															Log.d(Constants.TAG, "current update msg url " + url);
															AsyncHttpGet ahg = new AsyncHttpGet(url);
															AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
																@Override
																public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
																	GluckyActivity.this.onBackPressed();
																}
															});
														} catch (Throwable e) {
															GluckyActivity.this.onBackPressed();
														}

													}
												}).show();

									} else if (result.equals("no_lucky")) {
										new MaterialDialog.Builder(GluckyActivity.this)
												.title("系統提醒")
												.positiveColor(Color.parseColor("#636363"))
												.content("幸運星不在這裡喔。過一陣子歡迎繼續尋找它。").callback(new MaterialDialog.ButtonCallback() {
											@Override
											public void onPositive(MaterialDialog dialog) {
												GluckyActivity.this.onBackPressed();
											}
										})
												.positiveText("確定")
												.show();

									}
								}
							});
				}
			});
//		}
	}	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		VponAD.shutdown();
	}
	
	protected void onResume() {
		super.onResume();
		Log.d(Constants.TAG, "onResume");
	}
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(Constants.TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(Constants.TAG, "onRestart");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(Constants.TAG, "onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(Constants.TAG, "onStop");
		sp.edit().putInt("tt", sp.getInt("tt", 0) + 1).apply();
		Log.d(Constants.TAG, "tt:" + sp.getInt("tt", 0));		
	}
}
