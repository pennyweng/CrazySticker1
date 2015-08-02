package com.jookershop.crazysticker.lucky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.MainActivity;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.LineUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LuckyWinnerGiftAdapter extends ArrayAdapter<LuckyItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
    private String uid;
    private InterstitialAd mInterstitialAd;
	private SimpleDateFormat formatter;

	public LuckyWinnerGiftAdapter(Context context, ArrayList<LuckyItem> interestItems,
								  DisplayImageOptions options) {
		super(context, R.layout.lucky_winner_item4, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
        uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-4380704232895794/6328217666");
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LuckyItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.fn_winner_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();

			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);

			viewHolder.playDate = (TextView) rowView
					.findViewById(R.id.textView1);

			viewHolder.nickname = (TextView) rowView
					.findViewById(R.id.textView2e);
			viewHolder.line = (ImageView) rowView
					.findViewById(R.id.imageView3);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
//		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.line, options,
				new SimpleImageLoadingListener(), null);

		holder.playDate.setText(formatter.format(new Date(postItem.getWinnerTs())));

        if(postItem.getWinnerId() != null) {
            MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + postItem.getWinnerId(), holder.icon, options,
					new SimpleImageLoadingListener(), null);
			AccountUtil.showPersonImage(mContext, holder.icon, postItem.getWinnerId());
        }

		if(postItem.getWinnerMsg() != null) {
			holder.nickname.setText(postItem.getJoinCount() + "人已參與。\n得獎感言：" + postItem.getWinnerMsg());
		} else {
			holder.nickname.setText(postItem.getJoinCount() + "人已參與。");
		}

		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LineUtil.launchWeb(mContext, postItem.getImgUrl());
			}
		});




		return rowView;
	}



	static class ViewHolder {
		protected ImageView icon;
		protected TextView playDate;
		protected TextView nickname;
		protected ImageView line;
	}


}
