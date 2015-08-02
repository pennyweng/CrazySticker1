package com.jookershop.crazysticker.point;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.MainActivity;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.fnum.FnItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PointHistoryAdapter extends ArrayAdapter<HistoryItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;

	public PointHistoryAdapter(Context context, ArrayList<HistoryItem> interestItems,
							   DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HistoryItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.point_history_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();

			viewHolder.playDate = (TextView) rowView
					.findViewById(R.id.textView1);

			viewHolder.nickname = (TextView) rowView
					.findViewById(R.id.textView2e);

			viewHolder.point = (TextView) rowView
					.findViewById(R.id.textView9);

			viewHolder.product = (TextView) rowView
					.findViewById(R.id.textView10);

			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();

		if(postItem.getFrom().equals("1"))
			holder.nickname.setText("多盟任務");
		else if(postItem.getFrom().equals("2"))
			holder.nickname.setText("有米任務");
		else if(postItem.getFrom().equals("3"))
			holder.nickname.setText("趣米任務");
		else if(postItem.getFrom().equals("4"))
			holder.nickname.setText("每日驚喜");
		else if(postItem.getFrom().equals("5"))
			holder.nickname.setText("Facebook分享");
		else if(postItem.getFrom().equals("6"))
			holder.nickname.setText("傑思愛德威任務");
		else holder.nickname.setText("");

		holder.product.setText(postItem.getProduct());
		holder.playDate.setText(formatter.format(new Date(postItem.getPlayTime())));
		holder.point.setText(postItem.getPoint() + "點");
		
		return rowView;
	}

	
	static class ViewHolder {
		protected TextView playDate;	
		protected TextView nickname;
		protected TextView point;
		protected TextView product;
	}


}
