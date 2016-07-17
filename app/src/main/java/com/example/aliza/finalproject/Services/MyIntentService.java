package com.example.aliza.finalproject.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;

import com.example.aliza.finalproject.Classes.Constant;
import com.example.aliza.finalproject.R;


public class MyIntentService extends IntentService {



    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //send notification
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(Constant.NTF_TITLE+": "+
                intent.getStringExtra(Constant.NTF_TITLE_NAME));
        //
        builder.setContentText(intent.getStringExtra(Constant.NTF_SCH));
        int id=(int)(System.currentTimeMillis());
        nm.notify(id, builder.build());
    }
}