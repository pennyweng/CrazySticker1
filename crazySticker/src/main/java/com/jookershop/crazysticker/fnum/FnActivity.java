package com.jookershop.crazysticker.fnum;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.BidItem;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.ad.ADResult;
import com.jookershop.crazysticker.ad.AdLocus;
import com.jookershop.crazysticker.ad.AdPlay;
import com.jookershop.crazysticker.ad.AdmobAD;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.AppUnionAD;
import com.jookershop.crazysticker.ad.DomobAD;
import com.jookershop.crazysticker.ad.HoDoAD;
import com.jookershop.crazysticker.ad.Mobile159DAD;
import com.jookershop.crazysticker.ad.PointUtil;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.ad.YoumiAD;
import com.jookershop.crazysticker.util.AccountUtil;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.util.Message;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class FnActivity extends Activity {
	private TextView nowNumber;
	private EditText guess;

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Constants.TAG, "onCreate");
		
		setContentView(R.layout.fin_ads2);
		sp = this.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).commit();


		final FnItem item = (FnItem) getIntent().getSerializableExtra("bid");
		
		nowNumber = (TextView) this.findViewById(R.id.editText1);
		guess = (EditText) this.findViewById(R.id.editText2);
		nowNumber.setText(item.getSmall() + "\n到\n" + item.getBig());

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
				VponAD.showIter(FnActivity.this, adr7);
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
				FnActivity.this.onBackPressed();
			}
		});
		
		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Constants.IS_SUPER && sp.getInt("tt", 0) < 2) {
					Message.ShowMsgDialog(FnActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
				} else if(guess.getText().toString().equals(""))
					Message.ShowMsgDialog(FnActivity.this, "請輸入您的終極密碼。");
				else {
					save(item, guess.getText().toString(), false);
				}				
				
			}
		});

		Button quickbt = (Button) findViewById(R.id.Button04);
		quickbt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(guess.getText().toString().equals(""))
					Message.ShowMsgDialog(FnActivity.this, "請輸入您的終極密碼。");
				else {
					save(item, guess.getText().toString(), true);
				}

			}
		});
	}

	public void save(final FnItem item, String bidValue, boolean quick) {
//		bid/play?bid=2b187f10-cae8-447d-9693-9936b63d6bb5&uid=29FF1053B1E30818A1131500269B50549B5C7285&bidValue=202&nickName=man
		String uid = URLEncoder.encode(AccountUtil.getUid(this));
		String url = Constants.BASE_URL + "fin/play?gid=" + item.getId() + "&uid=" + uid
				+ "&guess=" + bidValue + "&quick=" + quick;
		Log.d(Constants.TAG, "fin/play " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }

				FnActivity.this
						.runOnUiThread(new Runnable() {
							public void run() {
								if(result.equals("err:1")) {
									Message.ShowMsgDialog(FnActivity.this, "此遊戲已經結束");
								} else if(result.equals("err:2")) {
									Message.ShowMsgDialog(FnActivity.this, "您目前的點數不足，無法參加。");
								} else if(result.indexOf("err:3") != -1) {
									if(result.split(":").length ==3) {
										String s = result.split(":")[2];
										String small = s.split("##")[0];
										String big = s.split("##")[1];
										Message.ShowMsgDialog(FnActivity.this, "輸入的終極密碼錯誤。目前範圍是" + small + "到" + big + "之間");
									}
									else Message.ShowMsgDialog(FnActivity.this, "輸入的終極密碼錯誤。");
								} else if(result.indexOf("err:4") != -1) {
									Message.ShowMsgDialog(FnActivity.this, "此遊戲已經結束");
								}else if(result.indexOf("err:5") != -1) {
									String rr1 = result.split(":")[2];
									int s = Integer.parseInt(rr1) / 60000;
									if (s >= 60) {
										int h = s / 60;
										int m = s % 60;
										Message.ShowMsgDialog(FnActivity.this, "需過"
												+ h + "小時又" + m + "分鐘才能再玩");

									} else {
										Message.ShowMsgDialog(FnActivity.this, "需過"
												+ s + "分鐘才能再玩");
									}
								} else if(result.indexOf("winner") != -1){
									new MaterialDialog.Builder(FnActivity.this)
											.title("系統通知")
											.content("恭喜您猜中了。請儘速到[完成回報]區去申請此獎勵。也請填寫下面猜中感言。")
											.positiveColor(Color.parseColor("#636363"))
											.inputMaxLength(15)
											.inputType(InputType.TYPE_CLASS_TEXT)
											.input("中獎感言", "", new MaterialDialog.InputCallback() {
												@Override
												public void onInput(MaterialDialog dialog, CharSequence input) {
													try {
														String uid = URLEncoder.encode(AccountUtil.getUid(FnActivity.this));
														String url = Constants.BASE_URL + "fin/msg?uid=" + uid + "&gid=" + item.getId() + "&msg=" + URLEncoder.encode(input.toString());
														Log.d(Constants.TAG, "current update msg url " + url);
														AsyncHttpGet ahg = new AsyncHttpGet(url);
														AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
															@Override
															public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
																FnActivity.this.onBackPressed();
															}
														});
													}catch (Throwable e) {
														FnActivity.this.onBackPressed();
													}

												}
											}).show();
								} else if(result.indexOf("##") != -1){
									String small = result.split("##")[0];
									String big = result.split("##")[1];

									new MaterialDialog.Builder(FnActivity.this)
											.title("系統提醒")
											.positiveColor(Color.parseColor("#636363"))
											.content("還是差了一點點。目前範圍是" + small + "到" + big + "之間").callback(new MaterialDialog.ButtonCallback() {
										@Override
										public void onPositive(MaterialDialog dialog) {
											FnActivity.this.onBackPressed();
										}
									})
											.positiveText("確定")
											.show();
								}
							}
						});
			}

		});
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
		sp.edit().putInt("tt", sp.getInt("tt", 0) + 1).commit();
		Log.d(Constants.TAG, "tt:" + sp.getInt("tt", 0));		
	}
}
