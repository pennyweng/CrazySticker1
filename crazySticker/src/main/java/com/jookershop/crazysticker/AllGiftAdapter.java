package com.jookershop.crazysticker;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.LineUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AllGiftAdapter extends ArrayAdapter<GiftItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	public ArrayList<GiftItem> orig;

	public AllGiftAdapter(Context context, ArrayList<GiftItem> interestItems,
			DisplayImageOptions options) {
		super(context, R.layout.gift_item2, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {


			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<GiftItem> result = new ArrayList();
				final FilterResults oReturn = new FilterResults();
				for( int index =0; index < getCount(); index ++) {
					GiftItem giftItem = getItem(index);
					if(giftItem.getName().contains(constraint))
						result.add(giftItem);
				}
				oReturn.count = result.size();
				oReturn.values = result;
				return oReturn;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
//				if(results.count > 0) {
					clear();
					addAll((ArrayList<GiftItem>) results.values);
					notifyDataSetChanged();
//				}
			}
		};
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GiftItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.gift_item2, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.progressBar1 = (ProgressBar) rowView.findViewById(R.id.progressBar1);
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

		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(postItem.getType().equals(GiftItem.TYPE_LINE))
				LineUtil.launchWeb(mContext, postItem.getImgUrl());
				else if(postItem.getType().equals(GiftItem.TYPE_LINE_TOPIC))
					LineUtil.launchTopicWeb(mContext, postItem.getImgUrl());
			}
		});

//		holder.maxCount.setText("領獎資格：(二擇一)\n1.參加" + postItem.getnMaxCount() + "次，目前已參加" + postItem.getClickCount() + "次\n2.用" + postItem.getPoint() + "點來兌換");
        holder.maxCount.setText("領圖資格：參加" + postItem.getnMaxCount() + "次，目前已參加" + postItem.getClickCount() + "次");

        holder.progressBar1.setMax(postItem.getnMaxCount());
		holder.progressBar1.setProgress(postItem.getClickCount());
		
//		if(Constants.IS_SUPER) {
//			holder.gidTv.setText(postItem.getId());
//			holder.gidTv.setVisibility(View.VISIBLE);
//		} else holder.gidTv.setVisibility(View.INVISIBLE);
		
//		holder.consume.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				consumePoint(postItem);
//			}
//		});
		
		holder.attend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (!sp.contains(Constants.LINE_STORE_KEY)) {
//					Message.ShowMsgDialog(mContext, "請先填寫右上方的基本資料!");
//				} else {
					checkTime(postItem);
//				}
				
//				addTask(postItem);

			}
		});

		return rowView;
	}


	static class ViewHolder {
		protected ImageView icon;
		protected TextView title;
		protected TextView maxCount;
		protected ProgressBar progressBar1;
		protected Button attend;	
		protected TextView gidTv;
		protected Button consume;			
	}

	private void checkTime(final GiftItem gi) {
		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		final String id = gi.getId();
		final String giftType = gi.getType();
		
		String url = "";
		if(giftType.equals(GiftItem.TYPE_LINE)) {
			url = Constants.BASE_URL + "gift/line/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_MONEY)) {
			url = Constants.BASE_URL + "gift/money/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_BAG)) {
			url = Constants.BASE_URL + "gift/bag/next_time?uid=" + uid
					+ "&lgid=" + id;			
		}else if(giftType.equals(GiftItem.TYPE_SE)) {
			url = Constants.BASE_URL + "gift/se/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_LINE_TOPIC)) {
			url = Constants.BASE_URL + "gift/line_topic/next_time?uid=" + uid
					+ "&lgid=" + id;
		}

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
						            intent.setClass(mContext, ClickADActivity.class);
						            intent.putExtra("gi", gi);
						            mContext.startActivity(intent);									

								}

							}
						});
					}
				});
	}
}
