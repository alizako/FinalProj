package com.example.aliza.finalproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aliza.finalproject.Services.AlarmReciever;
import com.example.aliza.finalproject.DB.AssingmentsDBHelper;
import com.example.aliza.finalproject.Classes.Constant;
import com.example.aliza.finalproject.Adapters.CursorAdapterFav;
import com.example.aliza.finalproject.Adapters.CursorAdapterSchdl;
import com.example.aliza.finalproject.Adapters.CustomListAdapterEpisodes;
import com.example.aliza.finalproject.Classes.Episode;
import com.example.aliza.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectedShowActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private List<Episode> episodes;
    private CustomListAdapterEpisodes adapter;
    private ListView listView;
    private String showID, title,schedule;

    private AlarmReciever alarmReciever;

    private boolean isSwitchChange;

    AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(this);
    SQLiteDatabase db;
    CursorAdapterFav cursorAdapterFavorite;
    CursorAdapterSchdl cursorAdapterSchedule;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_show);

        Intent intent = getIntent();

        //db initialization
        db = dbHelper.getReadableDatabase();
        Cursor cFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite = new CursorAdapterFav(this,cFavorite);
        Cursor cSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule = new CursorAdapterSchdl(this,cSchedule);

        //alarm:
        alarmReciever = new AlarmReciever();

        //main info of Show:
        showID=intent.getStringExtra(Constant.INTENT_ID);

        title=intent.getStringExtra(Constant.INTENT_TITLE);
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvYear = (TextView)findViewById(R.id.tvYearVal);
        tvYear.setText(intent.getStringExtra(Constant.INTENT_YEAR));

        TextView tvNw = (TextView)findViewById(R.id.tvNetVal);
        tvNw.setText(intent.getStringExtra(Constant.INTENT_NW));

        TextView tvGenre = (TextView)findViewById(R.id.tvGenreVal);
        tvGenre.setText(intent.getStringExtra(Constant.INTENT_GNR));

        schedule=intent.getStringExtra(Constant.INTENT_SCH);
        TextView tvSchd = (TextView)findViewById(R.id.tvScheduleVal);
        tvSchd.setText(schedule);



        episodes = new ArrayList<Episode>();
        adapter = new CustomListAdapterEpisodes(SelectedShowActivity.this,episodes);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        try{
            dialog= ProgressDialog.show(this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            //jsonExtractor.
            String tmpEpisodesUrl = Constant.URL_EPISODES.replace("#", showID) ;

            getJsonEpisodes(tmpEpisodesUrl);
        }
        catch(Exception e){
            dialog.dismiss();
            Toast.makeText(this,
                    Constant.ERR_MSG + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

       RatingBar rtb= (RatingBar) findViewById(R.id.ratingBar);
        if(dbHelper.isFavoriteShowExist(showID)){
            rtb.setRating(1);
        }
        rtb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 if (rating==1)
                     onFavoriteClick(SelectedShowActivity.this,
                             showID,title,true);
                 else
                     onFavoriteClick(SelectedShowActivity.this,
                             showID,"",false);

             }
         }
        );

        final Switch swtch = (Switch)findViewById(R.id.switch1);
        if(dbHelper.isScheduleShowExist(showID)){
            swtch.setChecked(true);
        }
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    onScheduleClick(SelectedShowActivity.this,
                            showID, title, schedule, true);
                    if(!isSwitchChange)
                        swtch.setChecked(isSwitchChange);
                }

                else
                    onScheduleClick(SelectedShowActivity.this,
                            showID,"","",false);
            }
        });

        TextView tvMyZone=(TextView)findViewById(R.id.tvMyZone);
        tvMyZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (SelectedShowActivity.this,myZone.class);
                //    intent.putExtra("QUERY", strQuery);
                startActivity(intent);
            }
        });

    }


    //*************************************************************************************//

    private int getIntDay(String day) {

        switch (day) {
            case Constant.SUN:
                return 1;
            case Constant.MON:
                return 2;
            case Constant.TUE:
                return 3;
            case Constant.WED:
                return 4;
            case Constant.THU:
                return 5;
            case Constant.FRI:
                return 6;
            case Constant.SAT:
                return 7;
            default:
                return 0;
        }
    }

    private void updateDB(){
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
    }

    //************************** favorite & schedule: DB *************************************************
    public void onFavoriteClick(Context context,String show_id, String show_name, boolean isFavorite)
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
    }

    public void onScheduleClick(Context context, String show_id, String show_name, String show_time, boolean isScheduled)
    {
        //insert the show_id, show_title, show_time to the table
        if(isScheduled) {
            isSwitchChange=true;
            //schedule day(s)
            if (show_time.equals("")) {
                Toast.makeText(SelectedShowActivity.this,
                        Constant.NTF_NO_SCH,
                        Toast.LENGTH_LONG).show();
                isSwitchChange=false;
            } else {
                show_time = show_time.replace(" ", "");
                show_time = show_time.replace("|", ",");
                String[] daysSchedule = show_time.split(",");

                if (daysSchedule[daysSchedule.length - 1].indexOf(":") < 0) {
                    Toast.makeText(SelectedShowActivity.this,
                            Constant.NTF_NO_SCH,
                            Toast.LENGTH_LONG).show();
                    isSwitchChange=false;
                } else {

                    String[] scheduleTime = daysSchedule[daysSchedule.length - 1].split(":"); //show_time.substring(indexTime).split(":") ;


                    int scheduleHour = Integer.parseInt(scheduleTime[0]);
                    int scheduleMinute = Integer.parseInt(scheduleTime[1]);

                    Calendar calendarSet = Calendar.getInstance();
                    calendarSet.setTimeInMillis(System.currentTimeMillis());
                    long milliesForTiming;

                    for (int i = 0; i < daysSchedule.length - 1; i++) //-1: the last is time
                    {
                        int scheduleDay = getIntDay(daysSchedule[i]);

                        calendarSet.set(Calendar.DAY_OF_WEEK, scheduleDay);

                        calendarSet.set(Calendar.HOUR_OF_DAY, scheduleHour);
                        calendarSet.set(Calendar.MINUTE, scheduleMinute);
                        calendarSet.set(Calendar.SECOND, 0);

                        if (scheduleHour >= 12)
                            calendarSet.set(Calendar.AM_PM, Calendar.PM);
                        else
                            calendarSet.set(Calendar.AM_PM, Calendar.AM);

                        //convert day to milliseconds
                        //day+nowUTC +hour => setAlarm()
                        milliesForTiming = calendarSet.getTimeInMillis();

                        alarmReciever.setAlarm(this, milliesForTiming, title, schedule);
                        AssingmentsDBHelper.InsertSchedule(context, show_id, show_name, show_time);
                        updateDB();
                    }

                    Toast.makeText(SelectedShowActivity.this,
                            Constant.NTF_MSG,
                            Toast.LENGTH_LONG).show();

                }
            }
        }
        else //remove the show_id from the table
        {
            AssingmentsDBHelper.DeleteSchedule(context, show_id);
            updateDB();
        }
    }



    //************************** Json Handling *************************************************
    public void getJsonEpisodes(String tmpUrl) {

        JsonArrayRequest showReq = new JsonArrayRequest(tmpUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /*Toast.makeText(MainActivity.this,
                                "response: " + response.toString(),
                                Toast.LENGTH_LONG).show();*/

                        if (response.length() == 0 || response == null) {
                            Toast.makeText(SelectedShowActivity.this,
                                    Constant.NOT_FOUND_EP_MSG,
                                    Toast.LENGTH_LONG).show();
                            //responseReq = false;
                        }
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                Episode episode = new Episode();
                                episode.setTitle(obj.getString(Constant.NAME_COL));
                                episode.setSummary(obj.getString(Constant.SUMMARY_COL));
                                episode.setEpisodeNum(Integer.parseInt(obj.getString(Constant.NUM_COL)));
                                episode.setSeason(Integer.parseInt(obj.getString(Constant.SEASON_COL)));
                                //when image is NULL, the value is in STRING format
                                // otherwise, it's an object:
                                if (obj.getString(Constant.IMG_COL).equals(Constant.NULL))
                                    episode.setImgUrl("");
                                else
                                    episode.setImgUrl(obj.getJSONObject(Constant.IMG_COL).
                                            getString(Constant.ORIGINAL_COL));

                                episode.setEpisodeDetails(
                                        Constant.SEASON+" " + obj.getString(Constant.SEASON_COL) +
                                        ", "+Constant.EPISODE+" " + obj.getString(Constant.NUM_COL) +
                                        ", "+Constant.AIR_TIME+" " + obj.getString(Constant.DATE_COL) +
                                        " " + obj.getString(Constant.AIRTIME_COL));

                                // adding movie to movies array
                                episodes.add(episode);

                                //responseReq = false;//dialog.dismiss();
                                //mListener.onDialogResponse(false,"");

                            } catch (JSONException e) {
                                Toast.makeText(SelectedShowActivity.this,
                                        Constant.CNCT_ERR_MSG,
                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

            }
        });
        // Adding request to request queue
        Volley.newRequestQueue(this).add(showReq);
    }




}
