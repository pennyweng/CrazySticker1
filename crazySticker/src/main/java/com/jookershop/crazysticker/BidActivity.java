package com.jookershop.crazysticker;

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
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
public class BidActivity extends Activity {
	private EditText point;
	private EditText nickname;

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Constants.TAG, "onCreate");
		
		setContentView(R.layout.bid_ads2);
		sp = this.getSharedPreferences(
				Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).apply();


		final BidItem item = (BidItem) getIntent().getSerializableExtra("bid");
		
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
				VponAD.showIter(BidActivity.this, adr7);
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
				BidActivity.this.onBackPressed();
			}
		});
		
		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sp.getInt("tt", 0) < 2) {
					Message.ShowMsgDialog(BidActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
				} else if(nickname.getText().toString().equals("") || point.getText().toString().equals(""))
					Message.ShowMsgDialog(BidActivity.this, "請輸入要下標的點數或者是暱稱。");
				else {
					save(item, point.getText().toString(), nickname.getText().toString(), false);
				}				
				
			}
		});

		Button quickBt = (Button) findViewById(R.id.Button04);
		quickBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(nickname.getText().toString().equals("") || point.getText().toString().equals(""))
					Message.ShowMsgDialog(BidActivity.this, "請輸入要下標的點數或者是暱稱。");
				else {
					save(item, point.getText().toString(), nickname.getText().toString(), true);
				}

			}
		});
	}

	public void save(BidItem item, String bidValue, String nickName, boolean quick) {
//		bid/play?bid=2b187f10-cae8-447d-9693-9936b63d6bb5&uid=29FF1053B1E30818A1131500269B50549B5C7285&bidValue=202&nickName=man
		String uid = URLEncoder.encode(AccountUtil.getUid(this));
		String url = Constants.BASE_URL + "bid/play?bid=" + item.getId() + "&uid=" + uid 
				+ "&bidValue=" + bidValue + "&nickName=" + URLEncoder.encode(nickName)
				+ "&quick=" + quick;
		Log.d(Constants.TAG, "save  url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    	
		    	if(result.equals("err:1")) {
		    		Message.ShowMsgDialog(BidActivity.this, "此貼圖已經結束下標");
		    	} else if(result.equals("err:2")) {
		    		Message.ShowMsgDialog(BidActivity.this, "您目前的點數不足，無法下標。");
		    	} else if(result.indexOf("err:3") != -1) {
		    		if(result.split(":").length ==3)
		    		Message.ShowMsgDialog(BidActivity.this, "最少下標的點數為" + result.split(":")[2]+ "，您下標的點數不足。");
		    		else Message.ShowMsgDialog(BidActivity.this, "您下標的點數不足。");
		    	} else if(result.indexOf("err:4") != -1) {
		    		Message.ShowMsgDialog(BidActivity.this, "此貼圖已經結束下標");
		    	}else if(result.indexOf("err:5") != -1) {
		    		String rr1 = result.split(":")[2]; 
		    		int s = Integer.parseInt(rr1) / 60000;
					if (s >= 60) {
						int h = s / 60;
						int m = s % 60;
						Message.ShowMsgDialog(BidActivity.this, "需過"
								+ h + "小時又" + m + "分鐘才能再下標");

					} else {
						Message.ShowMsgDialog(BidActivity.this, "需過"
								+ s + "分鐘才能再下標");
					}
		    	} else if(result.equals("ok")){
		    		BidActivity.this
					.runOnUiThread(new Runnable() {
						public void run() {
		    		Builder MyAlertDialog = new AlertDialog.Builder(BidActivity.this);
					MyAlertDialog.setMessage("已經成功下標了。");
					DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							BidActivity.this.onBackPressed();
						}
					};
					MyAlertDialog.setNeutralButton("確定", OkClick);
					MyAlertDialog.show();
						}});
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
