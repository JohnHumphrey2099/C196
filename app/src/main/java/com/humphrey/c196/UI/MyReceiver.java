package com.humphrey.c196.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.humphrey.c196.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "test";
    static int notificationID;
    @Override
    public void onReceive(Context context, Intent intent) {

    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
    }
}