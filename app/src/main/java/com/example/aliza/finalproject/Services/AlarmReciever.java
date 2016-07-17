package com.example.aliza.finalproject.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aliza.finalproject.Classes.Constant;


public class AlarmReciever extends BroadcastReceiver {
    private int alarmId;
    private AlarmManager alarmManager;
    private SharedPreferences sharedPreferences;//replace to DB
    private SharedPreferences.Editor spEditor;

    @Override
    public void onReceive(Context context,Intent intent){
        Intent service=new Intent(context, MyIntentService.class);
        service.putExtra(Constant.NTF_TITLE_NAME,intent.getStringExtra(Constant.NTF_TITLE_NAME));
        service.putExtra(Constant.NTF_SCH,intent.getStringExtra(Constant.NTF_SCH));

        context.startService(service);
    }

    public AlarmReciever(){}

    public void setAlarm(Context context, Long timeInMills,String title, String schedule ){

        Intent i=new Intent(context,AlarmReciever.class);
        i.putExtra(Constant.NTF_TITLE_NAME, title);
        i.putExtra(Constant.NTF_SCH, schedule);
        alarmId= (int)(System.currentTimeMillis());
        PendingIntent pi=PendingIntent.getBroadcast(context,alarmId,i,0);
        alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                timeInMills,alarmManager.INTERVAL_DAY*7,pi);

    }

    public void cancelAlarm(Context context){
        Intent intent=new Intent(context,AlarmReciever.class);
        PendingIntent sender=PendingIntent.getBroadcast(context,alarmId,intent,0);
        alarmManager.cancel(sender);
    }
}
