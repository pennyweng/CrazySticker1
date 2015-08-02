package com.jookershop.crazysticker.happytime;

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
import com.jookershop.crazysticker.util.AccountUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FnWinnerAdapter extends ArrayAdapter<FnItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;
	private SimpleDateFormat formatter1;

	public FnWinnerAdapter(Context context, ArrayList<FnItem> interestItems,
						   DisplayImageOptions options) {
		super(context, R.layout.happy_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatter1 = new SimpleDateFormat("HH:mm");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final FnItem postItem = getItem(position);
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
		if(postItem.getWinnerMsg() != null) {
			holder.nickname.setText("幸福時光:" + formatter1.format(new Date(postItem.getStart())) + " - "
					+ formatter1.format(new Date(postItem.getEnd())) + "\n得獎感言: " + postItem.getWinnerMsg());
		} else {
			holder.nickname.setText("幸福時光:" + formatter1.format(new Date(postItem.getStart())) + " - "
					+ formatter1.format(new Date(postItem.getEnd())));
		}


		holder.playDate.setText(formatter.format(new Date(postItem.getWinnerTs())));
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
						+ postItem.getWinnerId(), holder.icon, options,
				new SimpleImageLoadingListener(), null);

		MainActivity.imageLoader.displayImage(postItem.getImg(), holder.line, options,
				new SimpleImageLoadingListener(), null);

		AccountUtil.showPersonImage(mContext, holder.icon, postItem.getWinnerId());
		return rowView;
	}

	
	static class ViewHolder {
		protected ImageView icon;
		protected TextView playDate;	
		protected TextView nickname;
		protected ImageView line;
	}


}
