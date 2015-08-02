package com.jookershop.crazysticker.fnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.MainActivity;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.util.AccountUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FnAdapter extends ArrayAdapter<FnHistory> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;


	public FnAdapter(Context context, ArrayList<FnHistory> interestItems,
					 DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
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
		holder.nickname.setText("我的終極密碼: " + postItem.getBaseNumber()  + "\n");
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
