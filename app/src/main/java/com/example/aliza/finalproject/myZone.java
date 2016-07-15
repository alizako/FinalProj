package com.example.aliza.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;

public class myZone extends AppCompatActivity  {

    MyClickListenerFromListFragment mListener;
    int orientation;
    Fragment frgFavorite, frgSchedule,mFragmentContainer,mStackFragments ;
    FragmentManager fm;

    AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(this);
    SQLiteDatabase db;
    ListView favoriteList;
    ListView scheduleList;
    CursorAdapterFav cursorAdapterFavorite;
    CursorAdapterSchdl cursorAdapterSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zone);

        //save the current orientation:
        orientation= getResources().getConfiguration().orientation;

        fm = getSupportFragmentManager();
        mFragmentContainer=fm.findFragmentById(R.id.fragmentContainer);

        frgFavorite = new FavoriteFragment();
        frgSchedule = new ScheduleFragment();

       // favoriteList = (ListView)findViewById(R.id.listViewFav);
        scheduleList = (ListView)findViewById(R.id.listViewSchdl);

        //listViewFavorite adapter
        db = dbHelper.getReadableDatabase();
       /* Cursor cFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite = new CursorAdapterFav(this,cFavorite);
        favoriteList.setAdapter(cursorAdapterFavorite);*/

        //list view listener
        //TODO: set on rating change listener

        //listViewSchedule adapter
        //db = dbHelper.getReadableDatabase();
        Cursor cSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule = new CursorAdapterSchdl(this,cSchedule);
        scheduleList.setAdapter(cursorAdapterSchedule);


        //FragmentTransaction trans = fm.beginTransaction();
        //trans.add(android.R.id.content,frgResults,"frgResults");
        //trans.hide(frgResults);

        //trans.add(android.R.id.content,frgSearch,"frgSearch");
        //trans.commit();

    /*    Global.getRtb().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 if (rating==1)
                     mListener.onFavoriteClick(myZone.this,Global.getIdChosenShow(),Global.getStrChosenShow(),true);
                 else
                     mListener.onFavoriteClick(myZone.this,Global.getIdChosenShow(),"",false);

             }
         }
        );

        Global.getSwtFav().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mListener.onScheduleClick(myZone.this,Global.getIdChosenShow(),Global.getStrChosenShow(),Global.getTimeChosenShow(),true);
                else
                    mListener.onScheduleClick(myZone.this,Global.getIdChosenShow(),"","",false);
            }
        });*/

    }





   /* public void onFavoriteClick(Context context,String show_id, String show_name, boolean isFavorite)
    {
        //insert the show_id, show_title to the table
        if(isFavorite)
            AssingmentsDBHelper.InsertFavorite(context,show_id,show_name);

        //remove the show_id from the table
        else
            AssingmentsDBHelper.DeleteFavorite(context,show_id);

        //update cursor
        db = dbHelper.getReadableDatabase();
        Cursor newcFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite.changeCursor(newcFavorite);
        cursorAdapterFavorite.notifyDataSetChanged();
    }*/

    /*public void onScheduleClick(Context context, String show_id, String show_name, String show_time, boolean isScheduled)
    {
        //insert the show_id, show_title, show_time to the table
        if(isScheduled)
            AssingmentsDBHelper.InsertSchedule(context,show_id,show_name,show_time);

        //remove the show_id from the table
        else
            AssingmentsDBHelper.DeleteSchedule(context,show_id);

        //update cursor
        db = dbHelper.getReadableDatabase();
        Cursor newcSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule.changeCursor(newcSchedule);
        cursorAdapterSchedule.notifyDataSetChanged();
    }*/



}
