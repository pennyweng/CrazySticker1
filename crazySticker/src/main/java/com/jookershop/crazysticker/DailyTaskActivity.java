package com.jookershop.crazysticker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jookershop.crazysticker.ad.ADResult;
import com.jookershop.crazysticker.ad.AdmobAD;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.HoDoAD;
import com.jookershop.crazysticker.ad.Mobile159DAD;
import com.jookershop.crazysticker.ad.PointUtil;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.ad.YoumiAD;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.util.Message;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;

/**
 * A placeholder fragment containing a simple view.
 */
public class DailyTaskActivity extends Activity {
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.all_ads2);
		sp = this.getSharedPreferences(
				Constants.KEY_SP, Context.MODE_PRIVATE);

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
				VponAD.showIter(DailyTaskActivity.this, adr7);
			}
		});
		
		RelativeLayout adRelativeLayout6 = (RelativeLayout) findViewById(R.id.relativeLayout6);
		AppMaAD.show(DailyTaskActivity.this, adr6, adRelativeLayout6);
		
		RelativeLayout adRelativeLayout5 = (RelativeLayout) findViewById(R.id.relativeLayout5);		
		VponAD.show(DailyTaskActivity.this, adr5, adRelativeLayout5);

		if(HoDoAD.isShow(DailyTaskActivity.this)) {
			RelativeLayout adRelativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
			HoDoAD.show(DailyTaskActivity.this, adr4, adRelativeLayout4);
		}

		if(AdmobAD.isShow(DailyTaskActivity.this)) {
			final RelativeLayout adRelativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
			AdmobAD.show(DailyTaskActivity.this, adr3, adRelativeLayout3);
		}

		if(Mobile159DAD.isShow(DailyTaskActivity.this)) {
			final RelativeLayout adRelativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
			Mobile159DAD.show(DailyTaskActivity.this, adr2, adRelativeLayout2);
		}

		Button cancel = (Button) findViewById(R.id.Button03);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sp.edit().putInt("tt", 0).apply();
				DailyTaskActivity.this.onBackPressed();
			}
		});
		
		Button save = (Button) findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sp.getInt("tt", 0) < 2) {
					Message.ShowMsgDialog(DailyTaskActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
				} else {
					PointUtil.earnDaily(DailyTaskActivity.this, true);;
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
