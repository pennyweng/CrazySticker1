package com.jookershop.crazysticker.point;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.fnum.FnItem;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.AdUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PointHistoryFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private SharedPreferences sp;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PointHistoryFragment newInstance() {
		PointHistoryFragment fragment = new PointHistoryFragment();
		return fragment;
	}

	public PointHistoryFragment() {
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
		
		View rootView = inflater.inflate(R.layout.point_history, container,
				false);


		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new PointHistoryAdapter(this.getActivity(),
				new ArrayList<HistoryItem>(), options));
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		progressBar1.setVisibility(View.VISIBLE);

		return rootView;
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		loadItmes(true);
	}



	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "point/history?uid=" + uid;
		Log.d(Constants.TAG, "point history " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse response, final JSONArray result) {
				if (PointHistoryFragment.this != null && PointHistoryFragment.this.getActivity() != null)
					PointHistoryFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							progressBar1.setVisibility(View.INVISIBLE);
						}
					});

				if (e != null) {
					e.printStackTrace();
					return;
				}


				if (PointHistoryFragment.this != null && PointHistoryFragment.this.getActivity() != null)
					PointHistoryFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							try {
								PointHistoryAdapter ia = (PointHistoryAdapter) gridView.getAdapter();
								ia.clear();
								for (int index = 0; index < result.length(); index++) {
									JSONObject joo = result.getJSONObject(index);
									HistoryItem hi = new HistoryItem();
									hi.setPlayTime(joo.getLong("ts"));
									hi.setFrom(joo.getString("from"));
									hi.setPoint(joo.getInt("point"));
									if(joo.has("product"))
										hi.setProduct(joo.getString("product"));
									else hi.setProduct("");
									ia.add(hi);
								}
								ia.notifyDataSetChanged();
							} catch (Throwable e1) {
								e1.printStackTrace();
							}
						}
					});
			}
		});		
	}
}
