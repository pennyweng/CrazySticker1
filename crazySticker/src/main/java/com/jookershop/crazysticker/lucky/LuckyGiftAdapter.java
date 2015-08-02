package com.jookershop.crazysticker.lucky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.GiftItem;
import com.jookershop.crazysticker.MainActivity;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.AdUtil;
import com.jookershop.crazysticker.util.LineUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.net.URLEncoder;
import java.util.ArrayList;

public class LuckyGiftAdapter extends ArrayAdapter<LuckyItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
    private String uid;
    private InterstitialAd mInterstitialAd;

	public LuckyGiftAdapter(Context context, ArrayList<LuckyItem> interestItems,
                            DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
        uid = URLEncoder.encode(AccountUtil.getUid(mContext));

        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-4380704232895794/6328217666");
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }



	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LuckyItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.gift_item4, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
            viewHolder.user = (ImageView) rowView
                    .findViewById(R.id.imageView);
            viewHolder.status = (TextView) rowView.findViewById(R.id.textView);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
//			viewHolder.progressBar1 = (ProgressBar) rowView.findViewById(R.id.progressBar1);
			viewHolder.attend = (Button) rowView
					.findViewById(R.id.button1);
//			viewHolder.consume = (Button) rowView
//					.findViewById(R.id.Button03);
//			viewHolder.gidTv = (TextView) rowView.findViewById(R.id.textView3);
			

			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.icon, options,
				new SimpleImageLoadingListener(), null);


        if(postItem.getWinnerId() != null) {
            MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + postItem.getWinnerId(), holder.user, options,
                    new SimpleImageLoadingListener(), null);
            holder.user.setVisibility(View.VISIBLE);
			AccountUtil.showPersonImage(mContext, holder.user, postItem.getWinnerId());
        } else {
            holder.user.setVisibility(View.INVISIBLE);
        }

        if(postItem.isFinish()) {
            holder.maxCount.setText(postItem.getJoinCount() + "人已參與。已結束");
            holder.status.setText("得獎感言：" + postItem.getWinnerMsg());
            holder.attend.setVisibility(View.INVISIBLE);
        }
        else {
            holder.maxCount.setText(postItem.getJoinCount() + "人已參與");
            holder.status.setText("正在進行中...");
            holder.attend.setVisibility(View.VISIBLE);
        }

		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LineUtil.launchWeb(mContext, postItem.getImgUrl());
			}
		});

		
		holder.attend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                mInterstitialAd.setAdListener(new AdListener() {

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        super.onAdFailedToLoad(errorCode);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        Log.d(Constants.TAG, "google onAdLeftApplication");
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.d(Constants.TAG, "google onAdLoaded");
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        Log.d(Constants.TAG, "google onAdOpened");
                    }

                    @Override
                    public void onAdClosed() {
                        Log.d(Constants.TAG, "google onAdClosed");
                        requestNewInterstitial();
                        checkTime(postItem);
                    }
                });

                if(mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }else {
                    checkTime(postItem);
                }
			}
		});

		return rowView;
	}


	
	static class ViewHolder {
		protected ImageView icon;
        protected ImageView user;
		protected TextView title;
		protected TextView maxCount;
        protected TextView status;
//		protected ProgressBar progressBar1;
		protected Button attend;	
		protected TextView gidTv;
		protected Button consume;			
	}

	private void checkTime(final LuckyItem gi) {

		String url = Constants.BASE_URL + "touch/next_time?uid=" + uid
                + "&tid=" + gi.getId();

		Log.d(Constants.TAG, "gift checkTime play url " + url);
		AsyncHttpGet get = new AsyncHttpGet(url);

		AsyncHttpClient.getDefaultInstance().executeString(get,
				new AsyncHttpClient.StringCallback() {

					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse source, final String result) {
						if (e != null) {
							Message.ShowMsgDialog(mContext,
									"Opps....發生錯誤, 請稍後再試！");
							e.printStackTrace();
							return;
						}
						((Activity) mContext).runOnUiThread(new Runnable() {
							public void run() {
								if (!result.equals("0") && !Constants.IS_SUPER) {
									int s = Integer.parseInt(result) / 60000;
									if (s >= 60) {
										int h = s / 60;
										int m = s % 60;
										Message.ShowMsgDialog(mContext, "需過"
												+ h + "小時又" + m + "分鐘才能再玩一次");

									} else {
										Message.ShowMsgDialog(mContext, "需過"
												+ s + "分鐘才能再玩一次");
									}
								} else {
									sp.edit().putInt("tt", 0).apply();
									Intent intent = new Intent();
						            intent.setClass(mContext, com.jookershop.crazysticker.lucky.ClickADActivity.class);
						            intent.putExtra("gi", gi);
						            mContext.startActivity(intent);									

								}

							}
						});
					}
				});
	}
}
