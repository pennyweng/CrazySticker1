package com.jookershop.crazysticker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Locale;

//import net.youmi.android.AdManager;
//import net.youmi.android.offers.OffersManager;
//import net.youmi.android.offers.PointsManager;

import com.Mobile159.Mobile159adContainer;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.jookershop.crazysticker.ad.AppMaAD;
import com.jookershop.crazysticker.ad.AppUnionAD;
import com.jookershop.crazysticker.ad.DomobOffer;
import com.jookershop.crazysticker.ad.QumiAD;
import com.jookershop.crazysticker.ad.VponAD;
import com.jookershop.crazysticker.ad.YoumiPointEarn;
import com.jookershop.crazysticker.big.BigFragment;
import com.jookershop.crazysticker.fnum.FnMainFragment;
import com.jookershop.crazysticker.glucky.GluckyMainFragment;
import com.jookershop.crazysticker.happytime.HappyTimeMainFragment;
import com.jookershop.crazysticker.lucky.LuckyMainFragment;
import com.jookershop.crazysticker.lucky.LuckyProductFragment;
import com.jookershop.crazysticker.point.PointMainFragment;
import com.jookershop.crazysticker.topic.TopicFragment;
import com.jookershop.crazysticker.util.AccountUtil;
import com.jookershop.crazysticker.util.Message;
import com.jookershop.crazysticker.v2.ReturnProductFragment;
import com.jookershop.crazysticker.v2.ReturnTopicFragment;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.FileBody;
import com.koushikdutta.async.http.body.StreamBody;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

import net.slidingmenu.tools.AdManager;
import net.slidingmenu.tools.os.OffersBrowserConfig;
import net.slidingmenu.tools.os.OffersManager;
import net.slidingmenu.tools.os.PointsManager;

import org.json.JSONObject;
import net.adways.appdriver.sdk.ADAService;

public class MainActivity extends MaterialNavigationDrawer {
    public static ImageLoader imageLoader;
    public static final String TEMP_PHOTO_FILE_NAME = "linefriend_photo.jpg";
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private File mFileTemp;
    public static CallbackManager callbackManager;
    public static ShareDialog shareDialog;
//    private YoumiPointEarn ype;
    private SharedPreferences sp;

    @Override
    public void init(Bundle savedInstanceState) {


        sp = getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
        String menuColor = "#f08c0a";
        this.setDrawerHeaderImage(R.drawable.banner);

        this.addSubheader("賺參加/點數");
        this.addSection(newSection("貼圖區", R.drawable.join, ActiveProductFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("任務區", R.drawable.checklist, PointMainFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("主題區", R.drawable.bulb, TopicFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));

        this.addSubheader("一同享樂");
        this.addSection(newSection("競標", R.drawable.race, BidFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("戳戳樂", R.drawable.touch, LuckyMainFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("終極密碼", R.drawable.nn2, FnMainFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("比大小", R.drawable.big, BigFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("尋找幸運星", R.drawable.start, GluckyMainFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("幸福時光", R.drawable.clock, HappyTimeMainFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));


        this.addSubheader("換取獎勵");
        this.addSection(newSection("貼圖兌換", R.drawable.change, ReturnProductFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("主題兌換", R.drawable.handshake, ReturnTopicFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));
        this.addSection(newSection("完成回報", R.drawable.dd, FinishGiftFragment.newInstance()).setSectionColor(Color.parseColor(menuColor)));

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }

        this.addBottomSection(newSection("上傳大頭貼", R.drawable.addressbook, new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection materialSection) {
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("照片選取")
                                .items(new String[] { "用相機拍新照片", "從相簿取得照片" })
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int item, CharSequence text) {
                                        if (item == 0) {
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                            try {
//                                                Uri mImageCaptureUri = null;
//                                                String state = Environment.getExternalStorageState();
//                                                if (Environment.MEDIA_MOUNTED.equals(state)) {
//                                                    mImageCaptureUri = Uri.fromFile(mFileTemp);
//                                                }
//                                                intent.putExtra(
//                                                        android.provider.MediaStore.EXTRA_OUTPUT,
//                                                        mImageCaptureUri);
//                                                intent.putExtra("return-data", true);
                                                startActivityForResult(intent, PICK_FROM_CAMERA);
                                            } catch (ActivityNotFoundException e) {

                                                e.printStackTrace();
                                            }
                                        } else { // pick from file
                                            Intent intent = new Intent();

                                            intent.setType("image/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);

                                            startActivityForResult(Intent.createChooser(intent,
                                                    "Complete action using"), PICK_FROM_FILE);
                                        }
                                    }
                                })
                                .show();


                    }
                }
        ));

        this.addBottomSection(newSection("常見問題", R.drawable.faq, new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection materialSection) {
                        Message.ShowMsgDialog(MainActivity.this, "<strong>Q1. 參加各項活動最重要的規則是什麼？</strong><br />" +
                                "<font color=\"#878787\">Ans. [瘋貼圖]保留對程式內各活動變更、修改、停止之權利，如遇意外或其他原因導致獎品發放困難，[瘋貼圖]有權隨時取消、暫停或終止活動，參加者絕無異議。一切未盡事宜，悉依[瘋貼圖]之說明或解釋為準。參加者於參加活動之同時，即視為同意接受[瘋貼圖]之各項規範，如有違反，[瘋貼圖]有權取消其領獎資格。\n" +
                                "※如不同意上述規則，請立刻按返回鍵離開程式。※</font><br /><br />" +
                                "<strong>Q2. 要如何參加各種活動及遊戲？</strong><br />" +
                                "<font color=\"#878787\">Ans.1.確定畫面上有兩個以上您有興趣的廣告，若沒有請下次再來。" +
                                "2.挑選其中一則有興趣的廣告，點開它。會有下列三種可能：" +
                                "    a.引導您到Play Store：您可以選擇下載或直接返回瘋貼圖<br />" +
                                "    b.出現影音廣告：耐心看完之後，再點選廣告本身，跳到廠商的網站，在返回瘋貼圖<br />" +
                                "    c.出現遊戲試玩廣告：：請點選開始試玩，之後再到Play Store下載頁面。您可以選擇下載或直接返回瘋貼圖<br />" +
                                "3.返回瘋貼圖後，在完成另一則有興趣的廣告\n" +
                                "4.都完成後再點選送出</font><br /><br />" +

                                "<strong>Q3. 貼圖要怎麼申請？什麼時候會領到？怎麼領？有期限嗎？</strong><br />" +
                                "<font color=\"#878787\">Ans.在『完成回報』頁面選擇要申請的項目按下申請即可 (資料請務必填寫正確，仔細檢查確認無誤後再送出)，不用再另外截圖或傳訊息給版主，只要資料填寫無誤，申請後5個工作天會完成發送。會直接發送到申請時填寫的LINE帳號。(PS.申請後到領到貼圖的這幾天請暫時不要更改暱稱，以免影響貼圖發送)\n" +
                                "請務必於完成後2週內(14天)提出申請，逾期視同放棄，系統會自動刪除該項目。</font><br /><br />" +
                                "<strong>Q4. 為什麼我在LINE傳訊息給版主，版主都不馬上回？</strong><br />" +
                                "<font color=\"#878787\">Ans.很抱歉版主和小幫手沒辦法24小時守在LINE旁邊，但會盡量在當天處理完各個平台的訊息，就算當天沒有處理到，最晚也會在隔天處理完，還請各位體諒人手有限，稍安勿躁。另外，例假日是版主的休息時間，所以暫停一切作業，請見諒。</font><br /><br />" +
                                "<strong>Q5. 活動中的貼圖我已經有了還可以玩嗎？</strong><br />" +
                                "<font color=\"#878787\">Ans.不可以。基於LINE一個帳號內的貼圖不能重覆 (那當然，你是要2個一樣的貼圖幹嘛啦？=.=!!!)，所以請務必選擇您的LINE帳號沒有的貼圖來進行，以免完成後卻無法領取。\n" +
                                "為免系統錯亂不小心造成更多錯誤，恕不提供已完成項目的貼圖換發申請，故請不要選擇您已經有的貼圖項目來參加，貼圖不提供換發服務，因為既然你沒辦法領，就請不要參加；不過，若您沒辦法領取還是要參加 (志在參加不要獎品的那種)，我們是也沒意見啦。</font><br /><br />" +
                                "<strong>Q6. 申請時LINE帳號資料填錯了怎麼辦？</strong><br />" +
                                "<font color=\"#878787\">Ans.基本上申請資料一經送出恕不更改，請務必在送出前仔細檢查 (只有ID和暱稱2個欄位而已，並沒有很難，請睜大您雪亮的眼睛多看二下，PLS~)。這就像你參加百貨公司的滿額抽獎，卻把抽獎券上的手機號碼填錯了，人家就連絡不到你，你自然就喪失了領獎的機會了嘛！哦？" +
                                "不過，如果真的就手眼不協調，眼睛看到填錯了但手還是給他按送出了，結果出現了ID少輸入一碼或是p變成了隔壁的o這種的，我們還是會願意特別幫你服務讓你更正啦！但如果手抖的太厲害差很多那種的，就很抱歉了哦！這種的要說不小心輸入錯誤也太牽強。" +
                                "更正資料以一次為限！請於程式中該筆申請項目留言後面雙引號內文字 ”更正資料：ID「ooo」、暱稱「xxx」”(ooo和xxx是你的正確資料，千萬不要這樣直接copy留言，小幫手會翻白眼翻到中風，然後麻煩直接留正確的就好，拜託！而且ID和暱稱都麻煩再給一次，不管你是只更正ID還是暱稱，都請照上面的格式二樣都再留一次)。</font>");


                    }
                }
        ));
        this.addBottomSection(newSection("前往粉絲頁", R.drawable.fan, new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection materialSection) {
                        try {
                            String url = "https://www.facebook.com/morefriendapp";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            MainActivity.this.startActivity(i);
                        } catch (Throwable ee) {}
                    }
                }
        ));
		File cacheDir;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"crazysticker");
		else
			cacheDir = this.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.threadPoolSize(4)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(13)
				// default
				.diskCache(
						new com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache(
								cacheDir))
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.imageDownloader(new BaseImageDownloader(this)) // default
				.writeDebugLogs().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
        FacebookSdk.setApplicationId("452744348218927");
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

		AdManager.getInstance(this).init("8a17e3a8588ae241", "09972f48c8f2da0b", false);
        OffersManager.getInstance(this).onAppLaunch();
        OffersManager.getInstance(this).setCustomUserId(AccountUtil.getUid(this));
        OffersManager.getInstance(this).setUsingServerCallBack(true);

//        PointsManager.getInstance(this).registerPointsEarnNotify(new YoumiPointEarn());
        OffersBrowserConfig.setPointsLayoutVisibility(false);
        OffersBrowserConfig.setBrowserTitleText("瘋貼圖免費點數");

		DomobOffer.init(this);
		QumiAD.init(this);

        Mobile159adContainer.steDemo(false);//是否為Demo環境

        ADAService.setup(this, 7060, "aab9894f8195884e810ba6c8cd3c8f8c");
        updateParam();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        PointsManager.getInstance(this).unRegisterPointsEarnNotify(new YoumiPointEarn());
        OffersManager.getInstance(this).onAppExit();
    }

    @Override
	  protected void onPause() {
	      super.onPause();
	      AppMaAD.shutdown(this);
		  VponAD.shutdown();
	  }

    private void updateParam() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String uid = URLEncoder.encode(AccountUtil.getUid(MainActivity.this));
                    String url = Constants.BASE_URL + "param?uid=" + uid;
                    Log.d(Constants.TAG, "updateParam " + url);
                    AsyncHttpGet ahg = new AsyncHttpGet(url);
                    AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg, new AsyncHttpClient.JSONObjectCallback() {
                        @Override
                        public void onCompleted(Exception e, AsyncHttpResponse response, JSONObject result) {
                            try {
                                if (e != null) {
                                    e.printStackTrace();
                                    return;
                                }

                                if(result.has("g_ad") && !result.isNull("g_ad")) {
                                    Log.d(Constants.TAG, "get admob_period " + result.getLong("g_ad"));
                                    sp.edit().putLong("admob_period", result.getLong("g_ad")).commit();
                                }

                                if(result.has("ad_hodo_enable") && !result.isNull("ad_hodo_enable")) {
                                    Log.d(Constants.TAG, "get ad_hodo_enable " + result.getBoolean("ad_hodo_enable"));
                                    sp.edit().putBoolean("ad_hodo_enable", result.getBoolean("ad_hodo_enable")).commit();
                                }

                                if(result.has("ad_mob_159_enable") && !result.isNull("ad_mob_159_enable")) {
                                    Log.d(Constants.TAG, "get ad_mob_159_enable " + result.getBoolean("ad_mob_159_enable"));
                                    sp.edit().putBoolean("ad_mob_159_enable", result.getBoolean("ad_mob_159_enable")).commit();
                                }

                                if(result.has("ad_locus_enable") && !result.isNull("ad_locus_enable")) {
                                    Log.d(Constants.TAG, "get ad_locus_enable " + result.getBoolean("ad_locus_enable"));
                                    sp.edit().putBoolean("ad_locus_enable", result.getBoolean("ad_locus_enable")).commit();
                                }

                                if(result.has("ad_play_enable") && !result.isNull("ad_play_enable")) {
                                    Log.d(Constants.TAG, "get ad_play_enable " + result.getBoolean("ad_play_enable"));
                                    sp.edit().putBoolean("ad_play_enable", result.getBoolean("ad_play_enable")).commit();
                                }

                                if(result.has("ad_play_key") && !result.isNull("ad_play_key")) {
                                    Log.d(Constants.TAG, "get ad_play_key " + result.getString("ad_play_key"));
                                    sp.edit().putString("ad_play_key", result.getString("ad_play_key")).commit();
                                }

                                if(result.has("ad_play_video_enable") && !result.isNull("ad_play_video_enable")) {
                                    Log.d(Constants.TAG, "get ad_play_video_enable " + result.getBoolean("ad_play_video_enable"));
                                    sp.edit().putBoolean("ad_play_video_enable", result.getBoolean("ad_play_video_enable")).commit();
                                }

                                if(result.has("ad_appma_enable") && !result.isNull("ad_appma_enable")) {
                                    Log.d(Constants.TAG, "get ad_appma_enable " + result.getBoolean("ad_appma_enable"));
                                    sp.edit().putBoolean("ad_appma_enable", result.getBoolean("ad_appma_enable")).commit();
                                }
                            }catch (Throwable aa) {
                            }
                        }
                    });
                } catch (Throwable e) {
                }
            }
        }).start();;
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("瘋貼圖")
                .positiveColor(Color.parseColor("#636363"))
                .content("確定要離開嗎？").callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                sp.edit().putInt("tt", 0).commit();
                MainActivity.this.finish();
            }
        })
                .positiveText("確定").negativeText("取消").negativeColor(Color.parseColor("#636363"))
                .show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                try {
                    Bundle extras = data.getExtras();

                    Bitmap bitmap1 = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 35, stream1);
                    byte[] byteArray1 = stream1.toByteArray();
                    upload(byteArray1);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            case PICK_FROM_FILE:
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(
                            data.getData());

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 35, stream);
                    byte[] byteArray = stream.toByteArray();
                    upload(byteArray);

                    inputStream.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void upload(byte [] data) {
        String uid = URLEncoder.encode(AccountUtil.getUid(MainActivity.this));
        String url =Constants.BASE_URL + "account/upload_img?uid=" + uid;

        AsyncHttpPost post = new AsyncHttpPost(url);
        AsyncHttpRequestBody body = new StreamBody(new ByteArrayInputStream(data), data.length);
        post.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {

            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                if (e != null) {
                    Message.ShowMsgDialog(MainActivity.this, "Opps....發生錯誤, 請稍後再試！");
                    e.printStackTrace();
                    return;
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("瘋貼圖")
                                .positiveColor(Color.parseColor("#636363"))
                                .content("上傳成功。").callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if(MainActivity.imageLoader != null) {
                                    MainActivity.imageLoader.clearMemoryCache();
                                    MainActivity.imageLoader.clearDiskCache();
                                }
                            }
                        })
                                .positiveText("確定")
                                .show();
                    }
                });

            }
        });
    }




}

