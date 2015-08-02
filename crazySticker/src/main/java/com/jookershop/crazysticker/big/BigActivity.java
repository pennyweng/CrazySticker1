package com.jookershop.crazysticker.big;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.*;
import com.jookershop.crazysticker.BidItem;
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
public class BigActivity extends Activity {
	//private EditText point;
	private EditText nickname;

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Constants.TAG, "onCreate");
		
		setContentView(R.layout.big_ads2);
		sp = this.getSharedPreferences(
				Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).apply();


		final BigItem item = (BigItem) getIntent().getSerializableExtra("big");

		//point = (EditText) this.findViewById(R.id.editText1);
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
				VponAD.showIter(BigActivity.this, adr7);
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
				BigActivity.this.onBackPressed();
			}
		});

		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sp.getInt("tt", 0) < 2 && !Constants.IS_SUPER) {
					boolean show = Message.ShowReminderMsgDialog(BigActivity.this, "無法送出","參加資格\n1.確定畫面上有兩個以上您有興趣的廣告，若沒有請下次再來\n2.挑選其中一則有興趣的廣告，點開它。會有下列三種可能\n    a.引導您到Play Store：您可以選擇下載或直接返回瘋貼圖\n    b.出現影音廣告：耐心看完之後，再點選廣告本身，跳到廠商的網站，在返回瘋貼圖\n    c.自動開始下載：耐心等到安裝頁面出現，確實安裝之後在再返回。如果發現不愛，之後再自行移除\n3.返回瘋貼圖後，在完成另一則有興趣的廣告\n4.都完成後再點選送出", "instruction");
					if(!show) {
						Message.ShowMsgDialog(BigActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
					}
					//Message.ShowMsgDialog(BigActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
				} else if(nickname.getText().toString().equals("") )
					Message.ShowMsgDialog(BigActivity.this, "請隨意說些什麼......");
				else {
					save(item, nickname.getText().toString(), false);
				}
			}
		});

		Button quickbt = (Button) findViewById(R.id.Button04);
		quickbt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(nickname.getText().toString().equals("") )
					Message.ShowMsgDialog(BigActivity.this, "請隨意說些什麼......");
				else {
					save(item, nickname.getText().toString(), true);
				}
			}
		});
	}

	public void save(BigItem item, String nickName, boolean quick) {
//		bid/play?bid=2b187f10-cae8-447d-9693-9936b63d6bb5&uid=29FF1053B1E30818A1131500269B50549B5C7285&bidValue=202&nickName=man
		String uid = URLEncoder.encode(AccountUtil.getUid(this));
		String url = Constants.BASE_URL + "big/play?big=" + item.getId() + "&uid=" + uid
				+ "&nickName=" + URLEncoder.encode(nickName) + "&quick=" + quick;
		Log.d(Constants.TAG, "save  url " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }

		    	if(result.equals("err:1")) {
		    		Message.ShowMsgDialog(BigActivity.this, "此貼圖已經結束");
		    	} else if(result.indexOf("err:2") != -1) {
					Message.ShowMsgDialog(BigActivity.this, "點數不足，無法參加。");
				} else if(result.indexOf("err:3") != -1) {
		    		Message.ShowMsgDialog(BigActivity.this, "系統有點問題，請稍候再嘗試。");
		    	} else if(result.indexOf("err:4") != -1) {
		    		Message.ShowMsgDialog(BigActivity.this, "此貼圖已經結束");
		    	}else if(result.indexOf("err:5") != -1) {
		    		String rr1 = result.split(":")[2];
		    		int s = Integer.parseInt(rr1) / 60000;
					if (s >= 60) {
						int h = s / 60;
						int m = s % 60;
						Message.ShowMsgDialog(BigActivity.this, "需過"
								+ h + "小時又" + m + "分鐘才能再玩比大小");

					} else {
						Message.ShowMsgDialog(BigActivity.this, "需過"
								+ s + "分鐘才能再玩比大小");
					}
		    	} else if(result.indexOf(":highest") != -1) {
					final String h = result.split(":")[0];
					BigActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							new MaterialDialog.Builder(BigActivity.this)
									.title("系統提醒")
									.positiveColor(Color.parseColor("#636363"))
									.content("您得到的數字為" + h + "，是目前的最大喔，恭喜您。").callback(new MaterialDialog.ButtonCallback() {
								@Override
								public void onPositive(MaterialDialog dialog) {
									BigActivity.this.onBackPressed();
								}
							})
									.positiveText("確定")
									.show();

						}
					});
				} else {
					BigActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							new MaterialDialog.Builder(BigActivity.this)
									.title("系統提醒")
									.positiveColor(Color.parseColor("#636363"))
									.content("您得到的數字為" + result + "，離最大的數字差一點點，請再接再厲吧。").callback(new MaterialDialog.ButtonCallback() {
								@Override
								public void onPositive(MaterialDialog dialog) {
									BigActivity.this.onBackPressed();
								}
							})
									.positiveText("確定")
									.show();

						}
					});
		    	}
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
		sp.edit().putInt("tt", sp.getInt("tt", 0) + 1).apply();
		Log.d(Constants.TAG, "tt:" + sp.getInt("tt", 0));		
	}
}
