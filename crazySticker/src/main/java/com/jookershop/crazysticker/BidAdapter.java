package com.jookershop.crazysticker;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class BidAdapter extends ArrayAdapter<BidHistory> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;

	public BidAdapter(Context context, ArrayList<BidHistory> interestItems,
			DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BidHistory postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.bid_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView3);
			viewHolder.playDate = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.nickname = (TextView) rowView
					.findViewById(R.id.textView2);			

			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.nickname.setText(postItem.getNickName());
		holder.playDate.setText(formatter.format(new Date(postItem.getTs())));
		holder.title.setText(postItem.getBaseNumber() + "點");
		
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
						+ postItem.getUid(), holder.icon, options,
				new SimpleImageLoadingListener(), null);
		AccountUtil.showPersonImage(mContext, holder.icon, postItem.getUid());
		return rowView;
	}

	public void consumePoint(GiftItem postItem) {
		String url = "";
		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		
		if(postItem.getType().equals(GiftItem.TYPE_LINE)) {
			url = Constants.BASE_URL + "point/line/consume?uid=" + uid +"&lgid=" + postItem.getId();
		} else if(postItem.getType().equals(GiftItem.TYPE_MONEY)) {
			url = Constants.BASE_URL + "point/money/consume?uid=" + uid +"&lgid=" + postItem.getId();
		} else if(postItem.getType().equals(GiftItem.TYPE_BAG)) {
			url = Constants.BASE_URL + "point/bag/consume?uid=" + uid +"&lgid=" + postItem.getId();
		} else if(postItem.getType().equals(GiftItem.TYPE_SE)) {
			url = Constants.BASE_URL + "point/se/consume?uid=" + uid +"&lgid=" + postItem.getId();
		}
			
		Log.d(Constants.TAG, "current consumePoint url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    	
				if(result.equals("err:2")) {
					Message.ShowMsgDialog(mContext, "目前點數不足，無法兌換。");
				} else if(result.indexOf("err") == -1) {
					Message.ShowMsgDialog(mContext, "兌換成功。請到記得到領取的獎勵區申請領獎。");
				}
		    }
		});		
	}
	
	

	
	static class ViewHolder {
		protected ImageView icon;
		protected TextView title;
		protected TextView playDate;	
		protected TextView nickname;		
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
