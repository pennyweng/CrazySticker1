package com.jookershop.crazysticker.v2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.ClickADActivity;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.GiftItem;
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
import java.util.ArrayList;

public class ReturnGiftAdapter extends ArrayAdapter<GiftItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;

	public ReturnGiftAdapter(Context context, ArrayList<GiftItem> interestItems,
                             DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GiftItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.gift_item3, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.progressBar1 = (ProgressBar) rowView.findViewById(R.id.progressBar1);
			viewHolder.attend = (Button) rowView
					.findViewById(R.id.button1);

			

			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.icon, options,
                new SimpleImageLoadingListener(), null);
        holder.maxCount.setText("領圖資格：用" + postItem.getPoint() + "點來兌換");
        holder.progressBar1.setVisibility(View.INVISIBLE);



		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LineUtil.launchWeb(mContext, postItem.getImgUrl());
			}
		});
		
		holder.attend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new MaterialDialog.Builder(mContext)
						.title("系統提醒")
						.content("確定要兌換嗎？").callback(new MaterialDialog.ButtonCallback() {
					@Override
					public void onPositive(MaterialDialog dialog) {
						consumePoint(postItem);
					}

					@Override
					public void onNegative(MaterialDialog dialog) {
					}

					@Override

					public void onNeutral(MaterialDialog dialog) {
					}
				})
						.positiveText("確定")
						.negativeText("取消")
						.positiveColor(Color.parseColor("#636363"))
						.negativeColorAttr(Color.parseColor("#636363"))
						.show();
			}
		});

		return rowView;
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
		}else if(postItem.getType().equals(GiftItem.TYPE_LINE_TOPIC)) {
			url = Constants.BASE_URL + "point/line_topic/consume?uid=" + uid +"&lgid=" + postItem.getId();
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
		protected TextView maxCount;
		protected ProgressBar progressBar1;
		protected Button attend;	
		protected TextView gidTv;
		protected Button consume;			
	}
}
