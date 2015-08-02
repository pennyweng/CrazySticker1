package com.jookershop.crazysticker.lucky;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.GiftItem;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.ad.ADResult;
import com.jookershop.crazysticker.ad.AdLocus;
import com.jookershop.crazysticker.ad.AdPlay;
import com.jookershop.crazysticker.ad.AdmobAD;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.HoDoAD;
import com.jookershop.crazysticker.ad.Mobile159DAD;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.ad.YoumiAD;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClickADActivity extends Activity {
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lucky_ads2);
		sp = this.getSharedPreferences(
				Constants.KEY_SP, Context.MODE_PRIVATE);
		sp.edit().putInt("tt", 0).apply();
		
		final LuckyItem gi= (LuckyItem) this.getIntent().getExtras().getSerializable("gi");

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
                if (sp.getInt("tt", 0) < 2 && !Constants.IS_SUPER) {
                    boolean show = Message.ShowReminderMsgDialog(ClickADActivity.this, "無法送出","參加資格\n1.確定畫面上有兩個以上您有興趣的廣告，若沒有請下次再來\n2.挑選其中一則有興趣的廣告，點開它。會有下列三種可能\n    a.引導您到Play Store：您可以選擇下載或直接返回瘋貼圖\n    b.出現影音廣告：耐心看完之後，再點選廣告本身，跳到廠商的網站，在返回瘋貼圖\n    c.自動開始下載：耐心等到安裝頁面出現，確實安裝之後在再返回。如果發現不愛，之後再自行移除\n3.返回瘋貼圖後，在完成另一則有興趣的廣告\n4.都完成後再點選送出", "instruction");
                    if(!show) {
                        Message.ShowMsgDialog(ClickADActivity.this, "目前能讓您有興趣的廣告不多或廣告數量不足，請稍候再嘗試。");
                    }
                } else send(gi, false);
			}
		});

        Button quickbt = (Button) findViewById(R.id.Button04);
        quickbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                send(gi, true);
            }
        });

	}

    private void send(final LuckyItem gi, boolean quick) {
            final String uid = URLEncoder.encode(AccountUtil.getUid(ClickADActivity.this));
            String url = Constants.BASE_URL + "touch/play?uid=" + uid
                    + "&tid=" + gi.getId()
                    + "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""))
                    + "&quick=" + quick;


            Log.d(Constants.TAG, "touch play url " + url);
            AsyncHttpGet get = new AsyncHttpGet(url);

            AsyncHttpClient.getDefaultInstance().executeString(get,
                    new AsyncHttpClient.StringCallback() {

                        @Override
                        public void onCompleted(Exception e,
                                                AsyncHttpResponse source,
                                                final String result) {

                            if (e != null) {
                                Message.ShowMsgDialog(ClickADActivity.this,
                                        "Opps....發生錯誤, 請稍後再試！");
                                if (e != null)
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
                            ClickADActivity.this
                                    .runOnUiThread(new Runnable() {
                                        public void run() {


                                            if (result.indexOf("err:1") != -1)
                                                Message.ShowMsgDialog(
                                                        ClickADActivity.this,
                                                        "此戳戳樂已經不存在");
                                            else if (result.indexOf("err:6") != -1)
                                                Message.ShowMsgDialog(
                                                        ClickADActivity.this,
                                                        "點數不足，無法參加");
                                            else if (result.indexOf("err:2") != -1)
                                                Message.ShowMsgDialog(
                                                        ClickADActivity.this,
                                                        "此戳戳樂已經被人戳中了");

                                            else if (result
                                                    .indexOf("err:5") != -1) {
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
                                            } else if (result.equals("lucky")) {
                                                new MaterialDialog.Builder(ClickADActivity.this)
                                                        .title("系統通知")
                                                        .content("恭喜您戳中了。請儘速到[完成回報]區去申請此獎勵。也請填寫下面的戳中感言。")
                                                        .positiveColor(Color.parseColor("#636363"))
                                                        .inputMaxLength(15)
                                                        .inputType(InputType.TYPE_CLASS_TEXT)
                                                        .input("戳中感言", "", new MaterialDialog.InputCallback() {
                                                            @Override
                                                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                                                try {
                                                                    String uid = URLEncoder.encode(AccountUtil.getUid(ClickADActivity.this));
                                                                    String url = Constants.BASE_URL + "touch/msg?uid=" + uid + "&tid=" + gi.getId() + "&msg=" + URLEncoder.encode(input.toString());
                                                                    Log.d(Constants.TAG, "current update msg url " + url);
                                                                    AsyncHttpGet ahg = new AsyncHttpGet(url);
                                                                    AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
                                                                        @Override
                                                                        public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
                                                                            ClickADActivity.this.onBackPressed();
                                                                        }
                                                                    });
                                                                } catch (Throwable e) {
                                                                    ClickADActivity.this.onBackPressed();
                                                                }

                                                            }
                                                        }).show();

                                            } else if (result.equals("no_lucky")) {
                                                new MaterialDialog.Builder(ClickADActivity.this)
                                                        .title("系統提醒")
                                                        .positiveColor(Color.parseColor("#636363"))
                                                        .content("您沒有戳中喔。過一陣子歡迎繼續戳它。").callback(new MaterialDialog.ButtonCallback() {
                                                    @Override
                                                    public void onPositive(MaterialDialog dialog) {
                                                        ClickADActivity.this.onBackPressed();
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
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(Constants.TAG, "onStop");

//        if(isScreenOn(ClickADActivity.this)) {
            sp.edit().putInt("tt", sp.getInt("tt", 0) + 1).commit();
//        }

		Log.d(Constants.TAG, "tt:" + sp.getInt("tt", 0));
	}


    public boolean isScreenOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(Constants.TAG, "onTouchEvent");
//
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d(Constants.TAG, "MotionEvent.ACTION_DOWN");
//        }
//
//
//        return super.onTouchEvent(event);
//    }
//
//    public void updateMsg(String tid, String msg, Context context) {
//
//    }
}
