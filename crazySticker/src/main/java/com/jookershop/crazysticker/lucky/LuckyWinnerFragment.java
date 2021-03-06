package com.jookershop.crazysticker.lucky;

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
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.R;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.AdUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class LuckyWinnerFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private TextView title;
	private static LuckyWinnerFragment fragment;


	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static LuckyWinnerFragment newInstance() {
//		if(fragment == null)
		LuckyWinnerFragment fragment = new LuckyWinnerFragment();
		return fragment;
	}

	public LuckyWinnerFragment() {
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
		final SharedPreferences sp = this.getActivity().getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
		
		View rootView = inflater.inflate(R.layout.fragment_ativity_gift, container,
				false);

		title = (TextView) rootView.findViewById(R.id.textView1);
        title.setText("");
		
		
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new LuckyWinnerGiftAdapter(this.getActivity(),
				new ArrayList<LuckyItem>(), options));

		return rootView;
	}


	@Override
	public void onResume() {
		super.onResume();
		loadItmes(true);
	}


	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "touch/winlist?uid=" + uid;
		Log.d(Constants.TAG, "current touch url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
				if(LuckyWinnerFragment.this != null && LuckyWinnerFragment.this.getActivity()!= null)
					LuckyWinnerFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});

		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }

//		        if(first && result.length() == 0) Message.ShowMsgDialog(ActiveGiftFragment.this.getActivity(), "目前沒有進行中的遊戲");
				final ArrayList<LuckyItem> res = new ArrayList<LuckyItem>();
				for (int index = 0; index < result.length(); index++) {
					try {

						JSONObject jo = result.getJSONObject(index);
						res.add(LuckyItem.parse(jo));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if(LuckyWinnerFragment.this != null && LuckyWinnerFragment.this.getActivity()!= null)
					LuckyWinnerFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						LuckyWinnerGiftAdapter ia = (LuckyWinnerGiftAdapter) gridView.getAdapter();
                        ia.clear();
						for(int index = 0; index < res.size(); index ++)
						ia.add(res.get(index));
						ia.notifyDataSetChanged();
					}
				});
		    }
		});		
	}
}
