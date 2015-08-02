package com.jookershop.crazysticker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        System.out.println("MyReceiver receive intent");

        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_VIEW.equals(action)) {
            System.out.println("MyReceiver receive action view intent");
            Log.d(Constants.TAG, "MyReceiver receive action view intent");
        }
    }
}