package com.jookershop.crazysticker.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.MainActivity;
import com.jookershop.crazysticker.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AccountUtil {

	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.emptyhead)
	.showImageForEmptyUri(R.drawable.emptyhead)
	.showImageOnFail(R.drawable.emptyhead)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.resetViewBeforeLoading(true)
	.build();
	
	public static String getGamilID(Context context) {
		String result = "";
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				String possibleEmail = account.name;
				if (possibleEmail.indexOf("@gmail.com") != -1)
					result = possibleEmail.substring(0,
							possibleEmail.indexOf("@gmail.com"));
			}
		}

		return result;
	}

	public static String getUid(Context context) {
		SharedPreferences  sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
		if(sp.contains("uid")) {
			String uu = sp.getString("uid", Installation.id(context));
			if(!sp.contains("checkOne") && !Installation.isExistSD()) {
				Installation.writeSD(uu);
			}
			sp.edit().putBoolean("checkOne", true).apply();
			return uu;
		} else {
			String su = Installation.idFromSD(context);
			if(su == null || su == "") {
				String account = getGamilID(context);
				if (account != "") {
					String uu = Crytal.toHash(account);
					sp.edit().putString("uid", uu).apply();
					Installation.writeSD(uu);
					Log.d(Constants.TAG, "get uid from email:" + uu);
					return uu;
				} else {
					String uu = Installation.id(context);
					sp.edit().putString("uid", uu).apply();
					Installation.writeSD(uu);
					Log.d(Constants.TAG, "get uid from new app cache:" + uu);
					return uu;
				}
			} else {
				Log.d(Constants.TAG, "get uid from external sd:" + su);
				sp.edit().putString("uid", su).apply();
				return su;
			}
		}
	}

	public static void showPersonImage(final Context context, ImageView iv, final String id) {
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean wrapInScrollView = false;
				MaterialDialog dialog = new MaterialDialog.Builder(context)
						.title("大頭貼")
						.customView(R.layout.person, wrapInScrollView)
						.show();
				View view = dialog.getCustomView();
				ImageView iv = (ImageView)view.findViewById(R.id.imageView2);
				MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
								+ id, iv, options,
						new SimpleImageLoadingListener(), null);
			}
		});
	}
}
