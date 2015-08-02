package com.jookershop.crazysticker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdView;
import com.jookershop.crazysticker.ad.DomobOffer;
import com.jookershop.crazysticker.ad.QumiAD;
import com.jookershop.crazysticker.ad.YoumiAD;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.AdUtil;
import com.jookershop.crazysticker.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.net.URLEncoder;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActiveMenuFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    DisplayImageOptions options;
    private ListView gridView;
    private ProgressBar progressBar1;
    private TextView title;
    private static ActiveMenuFragment fragment;
    private TextView nextT;

    public ActiveMenuFragment() {
        options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.emptyhead)
                .showImageForEmptyUri(R.drawable.logo2)
                .showImageOnFail(R.drawable.logo2).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true).build();
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static ActiveMenuFragment newInstance() {
//        if(fragment == null)
            ActiveMenuFragment fragment = new ActiveMenuFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences sp = this.getActivity().getSharedPreferences(
                Constants.KEY_SP, Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.task_menu, container, false);

        Button bt1 = (Button) rootView.findViewById(R.id.Button01);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YoumiAD.showWall(ActiveMenuFragment.this.getActivity());


            }
        });

        Button bt2 = (Button) rootView.findViewById(R.id.button1);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DomobOffer.show(ActiveMenuFragment.this.getActivity());

            }
        });

        Button bt3 = (Button) rootView.findViewById(R.id.Button02);
        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // OffersManager.getInstance(ActiveMenuFragment.this.getActivity()).showOffersWall();
                QumiAD.showTask(ActiveMenuFragment.this.getActivity());
            }
        });

        Button bt4 = (Button) rootView.findViewById(R.id.Button03);
        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sp.edit().putInt("tt", 0).apply();
                Intent intent = new Intent();
                intent.setClass(ActiveMenuFragment.this.getActivity(), DailyTaskActivity.class);
                ActiveMenuFragment.this.getActivity().startActivity(intent);
            }
        });

        Button shareFB = (Button) rootView.findViewById(R.id.Button04);
        shareFB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                FacebookSdk.setIsDebugEnabled(true);
                if (sp.contains("fb_share")) {
                    Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "之前已經分享過了，謝謝支持。");
                } else {
                    try {
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent content = new ShareLinkContent.Builder()
                                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.jookershop.crazysticker"))
                                    .setImageUrl(Uri.parse("http://www.jookershop.com:9001/linefriend/account/image?uid=fbb"))
                                    .setContentTitle("瘋貼圖")
                                    .setContentDescription("最簡易獲得貼圖的軟體。300+種以上的貼圖免費獲得/免費抽獎/免費下標。")
                                    .build();
//                            ShareDialog shareDialog = new ShareDialog(ActiveMenuFragment.this.getActivity());
                            MainActivity.shareDialog.registerCallback(MainActivity.callbackManager, new FacebookCallback<Sharer.Result>() {
                                @Override
                                public void onSuccess(Sharer.Result result) {
                                    if (sp.contains("fb_share")) return;

                                    String uid = URLEncoder.encode(AccountUtil.getUid(ActiveMenuFragment.this.getActivity()));
                                    String url = Constants.BASE_URL + "point/promote/fb?uid=" + uid;
                                    Log.d(Constants.TAG, "current point/promote/fb  url " + url);
                                    AsyncHttpGet ahg = new AsyncHttpGet(url);
                                    AsyncHttpClient.getDefaultInstance().executeString(ahg,
                                            new AsyncHttpClient.StringCallback() {
                                                @Override
                                                public void onCompleted(Exception e,
                                                                        AsyncHttpResponse response, final String result) {
                                                    if (e != null) {
                                                        Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "發生錯誤，請稍候再試。");
                                                        e.printStackTrace();
                                                        return;
                                                    }

                                                    if (ActiveMenuFragment.this != null
                                                            && ActiveMenuFragment.this.getActivity() != null)
                                                        if (result.indexOf("err") == -1 && result.indexOf("#") != -1) {
                                                            sp.edit().putBoolean("fb_share", true).commit();
                                                            Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "分享成功，點數增加至" + result.split("#")[0] + "點");
                                                        } else if (result.equals("err:1")) {
                                                            Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "之前已經分享過了，點數不會再增加");
                                                        } else {
                                                            Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "發生錯誤，請稍候再試。");
                                                        }
                                                }
                                            });
                                }

                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onError(FacebookException e) {
                                    Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "發生錯誤，請稍候再試。");
                                }
                            });
                            MainActivity.shareDialog.show(content);
                        } else Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "發生錯誤，請稍候再試。");
                    } catch (Throwable e) {
                        Message.ShowMsgDialog(ActiveMenuFragment.this.getActivity(), "發生錯誤，請稍候再試。");
                    }
                }
            }
        });


        title = (TextView) rootView.findViewById(R.id.textView1);
        nextT = (TextView) rootView.findViewById(R.id.textView8);

        reportPoints(this.getActivity());
//		loadPointCount();

        final AdView adView = (AdView) rootView.findViewById(R.id.adView);
        AdUtil.showAD(this.getActivity(), adView);

        return rootView;
    }

    public void reportPoints(Context ctx) {
        try {
            DomobOffer.reportTotal(ctx);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            YoumiAD.reportTotal(ctx);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void updateTime(final Context context) {
        String uid = URLEncoder.encode(AccountUtil.getUid(context));
        String url = Constants.BASE_URL + "point/next_time?uid=" + uid;

        Log.d(Constants.TAG, "fn updateTime play url " + url);
        AsyncHttpGet get = new AsyncHttpGet(url);

        AsyncHttpClient.getDefaultInstance().executeString(get,
                new AsyncHttpClient.StringCallback() {

                    @Override
                    public void onCompleted(Exception e,
                                            AsyncHttpResponse source, final String result) {
                        if (e != null) {
                            return;
                        }
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {
                                if (!result.equals("0") && !Constants.IS_SUPER) {
                                    int s = Integer.parseInt(result) / 60000;
                                    if (s >= 60) {
                                        int h = s / 60;
                                        int m = s % 60;
                                        nextT.setText(h + "時" + m + "分後才能拿驚喜");

                                    } else {
                                        nextT.setText(s + "分後才能拿驚喜");
                                    }
                                } else {
                                    nextT.setText("快點來取得今日驚喜");
                                }
                            }
                        });
                    }
                });
    }
    public void loadPointCount() {
        String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
        String url = Constants.BASE_URL + "point?uid=" + uid;
        Log.d(Constants.TAG, "current loadPointCount url " + url);
        AsyncHttpGet ahg = new AsyncHttpGet(url);
        AsyncHttpClient.getDefaultInstance().executeString(ahg,
                new AsyncHttpClient.StringCallback() {
                    @Override
                    public void onCompleted(Exception e,
                                            AsyncHttpResponse response, final String result) {
                        if (e != null) {
                            e.printStackTrace();
                            return;
                        }

                        if (ActiveMenuFragment.this != null
                                && ActiveMenuFragment.this.getActivity() != null)
                            ActiveMenuFragment.this.getActivity()
                                    .runOnUiThread(new Runnable() {
                                        public void run() {
                                            if (result.indexOf("err") == -1) {
                                                title.setText("目前點數:" + result + "點");
                                            }
                                        }
                                    });
                    }
                });
    }

    @Override
    public void onResume() {
        loadPointCount();
        super.onResume();
        updateTime(ActiveMenuFragment.this.getActivity());
    }


}
