package com.jookershop.crazysticker.big;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jookershop.crazysticker.*;
import com.jookershop.crazysticker.BidActivity;
import com.jookershop.crazysticker.BidAdapter;
import com.jookershop.crazysticker.BidHistory;
import com.jookershop.crazysticker.BidItem;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.AdUtil;
import com.jookershop.crazysticker.util.LineUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * A placeholder fragment containing a simple view.
 */
public class BigFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private TextView title;
	private TextView begin;
	private TextView endDate;	
	private ImageView img;
	private TextView finish;
	private TextView play;
	private TextView countT;
	private TextView nextT;

	private BigItem bidItem;
	private SharedPreferences sp;
	private InterstitialAd mInterstitialAd;
//	private static BigFragment fragment;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static BigFragment newInstance() {
//		if(fragment == null)
			BigFragment fragment = new BigFragment();
		return fragment;
	}

	public BigFragment() {
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.emptyhead)
		.showImageForEmptyUri(R.drawable.logo2)
		.showImageOnFail(R.drawable.logo2)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.resetViewBeforeLoading(true)
		.build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sp = this.getActivity().getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);

		View rootView = inflater.inflate(R.layout.big, container,
				false);

		countT = (TextView) rootView.findViewById(R.id.textView6);
		title = (TextView) rootView.findViewById(R.id.textView1);
		begin = (TextView) rootView.findViewById(R.id.textView2);
		img = (ImageView) rootView.findViewById(R.id.imageView1);

		endDate = (TextView) rootView.findViewById(R.id.TextView01);
		play = (TextView) rootView.findViewById(R.id.textView3);
		nextT = (TextView) rootView.findViewById(R.id.textView7);

		finish = (TextView) rootView.findViewById(R.id.textView5);
		finish.setVisibility(View.INVISIBLE);

		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);

		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new BigAdapter(this.getActivity(),
				new ArrayList<BigHistory>(), options));

		TextView createN = (TextView) rootView.findViewById(R.id.textView3);
		createN.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(bidItem != null) {
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
							checkTime(bidItem);
						}
					});

					if(mInterstitialAd.isLoaded()) {
						mInterstitialAd.show();
					}else {
						checkTime(bidItem);
					}
				}
			}
		});

		mInterstitialAd = new InterstitialAd(this.getActivity());
		mInterstitialAd.setAdUnitId("ca-app-pub-4380704232895794/6328217666");
		requestNewInterstitial();

		return rootView;
	}

	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mInterstitialAd.loadAd(adRequest);
	}


	@Override
	public void onResume() {
		super.onResume();
		loadItmes(true);
	}

	public void updateTime(BigItem bigItem) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "big/next?uid=" + uid +"&big=" + bigItem.getId();
		Log.d(Constants.TAG, "current BigFragment checkTime url " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
				if (e != null) {
					e.printStackTrace();
					return;
				}

				if (BigFragment.this != null && BigFragment.this.getActivity() != null)
					BigFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							if (!result.equals("0") && !Constants.IS_SUPER) {
								int s = Integer.parseInt(result) / 60000;
								if (s >= 60) {
									int h = s / 60;
									int m = s % 60;
									nextT.setText(h + "時" + m + "分後才能參與");
								} else {
									nextT.setText(s + "分後才能參與");
								}
							} else nextT.setText("");
						}

					});
			}
		});
	}


	public void checkTime(BigItem bigItem) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "big/next?uid=" + uid +"&big=" + bigItem.getId();
		Log.d(Constants.TAG, "current BigFragment checkTime url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }

				if(BigFragment.this != null && BigFragment.this.getActivity()!= null)
					BigFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						if (!result.equals("0") && !Constants.IS_SUPER) {
							int s = Integer.parseInt(result) / 60000;
							if (s >= 60) {
								int h = s / 60;
								int m = s % 60;
								Message.ShowMsgDialog(BigFragment.this.getActivity(), "需過"
										+ h + "小時又" + m + "分鐘才能再玩一次");

							} else {
								Message.ShowMsgDialog(BigFragment.this.getActivity(), "需過"
										+ s + "分鐘才能再玩一次");
							}
						} else {
							sp.edit().remove("tt").commit();
							Intent intent = new Intent();
							intent.putExtra("big", bidItem);
							intent.setClass(BigFragment.this.getActivity(), BigActivity.class);
							BigFragment.this.getActivity().startActivity(intent);
						}
					}
				});
		    }
		});
	}

	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "big?uid=" + uid;
		Log.d(Constants.TAG, "current line big url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg, new AsyncHttpClient.JSONObjectCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONObject result) {
				if(BigFragment.this != null && BigFragment.this.getActivity()!= null)
					BigFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});

		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    	bidItem = BigItem.parse(result);

				if(BigFragment.this != null && BigFragment.this.getActivity()!= null)
					BigFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						if(bidItem.isFinish()) {
							play.setVisibility(View.INVISIBLE);
							finish.setVisibility(View.VISIBLE);
						}
						else {
							play.setVisibility(View.VISIBLE);
							finish.setVisibility(View.INVISIBLE);
						}

						title.setText(bidItem.getName());
//						begin.setText("起標價：" + bidItem.getBaseCount() + "點");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						endDate.setText("結束日期：" + formatter.format(new Date(bidItem.getEnd())));
						MainActivity.imageLoader.displayImage(bidItem.getImg(), img, options,
								new SimpleImageLoadingListener(), null);

						img.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								LineUtil.launchWeb(BigFragment.this.getActivity(), bidItem.getImg());
							}
						});
						countT.setText(bidItem.getBigHistory().length + "人參賽");
						BigAdapter ia = (BigAdapter) gridView.getAdapter();
						ia.clear();
						for(int index = 0; index < bidItem.getBigHistory().length; index ++)
						ia.add(bidItem.getBigHistory()[index]);
						ia.notifyDataSetChanged();
						updateTime(bidItem);
					}
				});
		    }
		});		
	}
}
