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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FnAdapter extends ArrayAdapter<FnHistory> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;


	public FnAdapter(Context context, ArrayList<FnHistory> interestItems,
					 DisplayImageOptions options) {
		super(context, R.layout.fn_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final FnHistory postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.fn_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);

			viewHolder.playDate = (TextView) rowView
					.findViewById(R.id.textView1);

			viewHolder.nickname = (TextView) rowView
					.findViewById(R.id.textView2e);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		if(postItem.getMsg() != null)
			holder.nickname.setText( "幸福宣言:" + postItem.getMsg());

		holder.playDate.setText(formatter.format(new Date(postItem.getTs())));
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
						+ postItem.getUid(), holder.icon, options,
				new SimpleImageLoadingListener(), null);
		AccountUtil.showPersonImage(mContext, holder.icon, postItem.getUid());
		return rowView;
	}

	
	static class ViewHolder {
		protected ImageView icon;
		protected TextView playDate;	
		protected TextView nickname;
	}


}
