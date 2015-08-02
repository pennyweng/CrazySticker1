package com.jookershop.crazysticker.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jookershop.crazysticker.Constants;
import com.jookershop.crazysticker.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

//import me.drakeet.materialdialog.MaterialDialog;

public class Message {
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            // .showImageOnLoading(R.drawable.emptyhead)
            .showImageForEmptyUri(R.drawable.emptyhead)
            .showImageOnFail(R.drawable.emptyhead).cacheInMemory(true)
            .cacheOnDisk(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
            .build();

    private static SharedPreferences sp;

//	public void registerAlarm(Context context) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DAY_OF_YEAR, 1);
//		calendar.set(Calendar.HOUR_OF_DAY, 9);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//
//		Intent intent = new Intent(context, PlayReceiver.class);
//		intent.putExtra("msg", "play_hskay");
//
//		PendingIntent pi = PendingIntent.getService(context, 0, intent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		AlarmManager am = (AlarmManager) context
//				.getSystemService(Context.ALARM_SERVICE);
//		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//				AlarmManager.INTERVAL_DAY, pi);
//	}

    public static void ShowMsgDialog(final Context context, final String msg) {
        if (context != null)
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        new MaterialDialog.Builder(context)
                                .title("系統提醒")
                                .content(Html.fromHtml(msg)).positiveColor(Color.parseColor("#636363"))
                                .positiveText("確定")
                                .show();

//                        Builder MyAlertDialog = new AlertDialog.Builder(context);
//                        MyAlertDialog.setMessage(Msg);
//                        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                            }
//                        };
//                        MyAlertDialog.setNeutralButton("確定", OkClick);
//                        MyAlertDialog.show();
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                }
            });
    }

    public static boolean ShowReminderMsgDialog(final Context context, String title, final String msg, final String tp) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.KEY_SP, Context.MODE_PRIVATE);
        if(!sp.contains(tp)) {
            new MaterialDialog.Builder(context)
                    .title(title)
                    .content(msg).callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                    sp.edit().putBoolean(tp, true).commit();
                }

                @Override

                public void onNeutral(MaterialDialog dialog) {
                }
            })
                    .positiveText("確定")
                    .negativeText("我知道了，不用再提醒我")
                    .positiveColor(Color.parseColor("#636363"))
                    .negativeColorAttr(Color.parseColor("#636363"))
                    .show();
            return true;
        } else return false;
    }

    public static void ShowReminderMsgDialog1(final Context context, final String Msg) {
        if (context != null)
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        Builder MyAlertDialog = new AlertDialog.Builder(context);
                        MyAlertDialog.setMessage(Msg);
                        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        };
                        DialogInterface.OnClickListener OkClick1 = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        };
                        MyAlertDialog.setNeutralButton("確定", OkClick);
                        MyAlertDialog.setNegativeButton("我知道了，不用再提醒我", OkClick1);
                        MyAlertDialog.show();
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                }
            });
    }
}
