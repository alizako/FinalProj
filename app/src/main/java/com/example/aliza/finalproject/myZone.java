package com.example.aliza.finalproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class myZone extends AppCompatActivity implements MyClickListenerFromListFragment {

    //database
    AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(this);
    SQLiteDatabase db;
    ContentValues values;

    int orientation;
    Fragment frgFavorite, frgSchedule,mFragmentContainer,mStackFragments ;
    FragmentManager fm;


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

        //FragmentTransaction trans = fm.beginTransaction();
        //trans.add(android.R.id.content,frgResults,"frgResults");
        //trans.hide(frgResults);

        //trans.add(android.R.id.content,frgSearch,"frgSearch");
        //trans.commit();

    }




    @Override
    public void onButtonClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void onFavoriteClick(int show_id)
    {
        //remove the show_id from the table
        db = dbHelper.getWritableDatabase();
        dbHelper.DeleteFavorite(db,show_id);
        db.close();
    }
    public void onScheduleClick(int episode_id)
    {
        //remove the episode_id from the table
        db = dbHelper.getWritableDatabase();
        dbHelper.DeleteSchedule(db,episode_id);
        db.close();

    }

    @Override
    public void onMyZoneClick(View view) {

    }
}
