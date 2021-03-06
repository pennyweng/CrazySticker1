package com.jookershop.crazysticker.topic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.AllGiftAdapter;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.GiftItem;
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
public class TopicFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private TextView title;
	private ArrayList<GiftItem> res;
//	private static ActiveProductFragment fragment;
	private SearchView sv;


	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static TopicFragment newInstance() {
		TopicFragment fragment = new TopicFragment();
		return fragment;
	}

	public TopicFragment() {
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
//		result = new ArrayList<GiftItem>();
		View rootView = inflater.inflate(R.layout.fragment_ativity_gift_search, container,
				false);

		title = (TextView) rootView.findViewById(R.id.textView1);
		
		
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new AllGiftAdapter(this.getActivity(),
				new ArrayList<GiftItem>(), options));
		gridView.setTextFilterEnabled(true);

		sv=(SearchView)rootView.findViewById(R.id.searchView);
		sv.setIconifiedByDefault(false);

		sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				restore(true, newText);
				return true;
			}
		});
		sv.setSubmitButtonEnabled(false);
		sv.setQueryHint("主題名稱關鍵字");
		int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		if(sv.findViewById(id) != null) {
			TextView textView = (TextView) sv.findViewById(id);
			textView.setTextColor(Color.parseColor("#9C9C9C"));
			textView.setHighlightColor(Color.parseColor("#9C9C9C"));
		}


		sv.postDelayed(new Runnable() {
			@Override
			public void run() {
				sv.clearFocus();
			}
		}, 500l);
		return rootView;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		loadItmes(true);
		loadTitleCount();
		if(sv != null) sv.setQuery("", false);
	}

	public void loadTitleCount() {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "gift/line_topic/next_time?uid=" + uid;
		Log.d(Constants.TAG, "current loadTitleCount url " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
				if (e != null) {
					e.printStackTrace();
					return;
				}

				if (TopicFragment.this != null && TopicFragment.this.getActivity() != null)
					TopicFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							String tt = "";
							int s = Integer.parseInt(result) / 60000;
							if (s > 0) {
								if (s >= 60) {
									int h = s / 60;
									int m = s % 60;
									tt = "下次參加" + h + "時" + m + "分鐘之後";

								} else {
									tt = "下次參加" + s + "分之後";
								}

								title.setText(tt);
								ViewGroup.LayoutParams ll = title.getLayoutParams();
								ll.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								title.setLayoutParams(ll);
							} else {
								title.setText("請挑選貼圖參加");
								ViewGroup.LayoutParams ll = title.getLayoutParams();
								ll.height = 0;
								title.setLayoutParams(ll);
							}
						}
					});
			}
		});		
	}




	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "gift/line_topic/list?uid=" + uid;
		Log.d(Constants.TAG, "current line gift url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
				if (TopicFragment.this != null && TopicFragment.this.getActivity() != null)
					TopicFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							progressBar1.setVisibility(View.INVISIBLE);
						}
					});

				if (e != null) {
					e.printStackTrace();
					return;
				}

//		        if(first && result.length() == 0) Message.ShowMsgDialog(ActiveGiftFragment.this.getActivity(), "目前沒有進行中的遊戲");
				res = new ArrayList<GiftItem>();
				for (int index = 0; index < result.length(); index++) {
					try {

						JSONObject jo = result.getJSONObject(index);
						res.add(GiftItem.genPostItem(jo));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				restore(false, null);
//				if (ActiveProductFragment.this != null && ActiveProductFragment.this.getActivity() != null)
//					ActiveProductFragment.this.getActivity().runOnUiThread(new Runnable() {
//						public void run() {
//							AllGiftAdapter ia = (AllGiftAdapter) gridView.getAdapter();
//							for (int index = 0; index < res.size(); index++)
//								ia.add(res.get(index));
//							ia.notifyDataSetChanged();
//						}
//					});
			}
		});		
	}


	private void restore(final boolean startFilter, final String newText) {
		if(TopicFragment.this != null && TopicFragment.this.getActivity()!= null)
			TopicFragment.this.getActivity().runOnUiThread(new Runnable() {
				public void run() {
					AllGiftAdapter ia = (AllGiftAdapter) gridView.getAdapter();
					ia.clear();
					if(res != null && res.size() > 0) {
						for (int index = 0; index < res.size(); index++)
							ia.add(res.get(index));
						ia.notifyDataSetChanged();

						if(startFilter) {
							if (newText != null && newText.equals("")) {
								gridView.clearTextFilter();
							} else {
								gridView.setFilterText(newText);
							}
						}
					}
				}
			});
	}
}
