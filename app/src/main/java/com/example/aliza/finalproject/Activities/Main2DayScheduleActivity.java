package com.example.aliza.finalproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aliza.finalproject.Classes.Constant;
import com.example.aliza.finalproject.Adapters.CustomListAdapter2Day;
import com.example.aliza.finalproject.R;
import com.example.aliza.finalproject.Classes.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2DayScheduleActivity extends AppCompatActivity
{

    ProgressDialog dialog;
    private List<Show> shows;
    private CustomListAdapter2Day adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2day_schedule);

        TextView tvDate = (TextView)findViewById(R.id.tvDate);

        shows = new ArrayList<Show>();
        adapter = new CustomListAdapter2Day(Main2DayScheduleActivity.this,shows);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //isEpisodeList=false;

        try{
            dialog= ProgressDialog.show(this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            //jsonExtractor.

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String tmp =dateFormat.format(today);

            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
            tvDate.setText(dateFormatter.format(today));

            getJson2DayShows(Constant.URL_2DAY_SHOWS+tmp);
        }
        catch(Exception e){
            dialog.dismiss();
            Toast.makeText(this,
                    Constant.ERR_MSG + e.toString(),
                    Toast.LENGTH_LONG).show();
        }


        listView. setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent
                        (Main2DayScheduleActivity.this,SelectedShowActivity.class);
                Show show = shows.get(position);

                intent.putExtra(Constant.INTENT_ID,show.getIdShow() );
                intent.putExtra(Constant.INTENT_TITLE,show.getTitle() );
                intent.putExtra(Constant.INTENT_YEAR, show.getYearPremiered().toString().substring(0,4));
                intent.putExtra(Constant.INTENT_NW, show.getNetwork());
                String tmp="";
                for (int j = 0; j < show.getGenres().size(); j++)
                    tmp+=show.getGenresByIndex(j)+" | ";
                intent.putExtra(Constant.INTENT_GNR, tmp);
                tmp="";
                for (int j = 0; j < show.getScheduleDays().size(); j++)
                    tmp+=show.getScheduleDaysByIndex(j)+" | ";
                intent.putExtra(Constant.INTENT_SCH, tmp+" "+ show.getScheduleTime());
                startActivity(intent);
            }
        });


        final SearchView searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String strQuery = searchView.getQuery().toString();

                if (!strQuery.isEmpty())
                {
                    Intent intent = new Intent
                            (Main2DayScheduleActivity.this,SearchResultsActivity.class);
                    intent.putExtra(Constant.INTENT_QUERY, strQuery);
                    startActivity(intent);
                }
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.i("well", " this worked");
                return false;
            }
        });


        TextView tvMyZone=(TextView)findViewById(R.id.tvMyZone);
        tvMyZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (Main2DayScheduleActivity.this,myZone.class);
            //    intent.putExtra("QUERY", strQuery);
                startActivity(intent);
            }
        });

    }


    public static String rightPadZeros(String str) {
        if(str.length()<2) str = "0" + str;
        return str;
    }

    //************************** Json Handling *************************************************

    private void getJson2DayShows(String url) {

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        if (response.length() == 0 || response == null) {
                            Toast.makeText(Main2DayScheduleActivity.this,
                            Constant.NOT_FOUND_MSG,
                            Toast.LENGTH_LONG).show();
                          //  listener2DayActivity.makeToast(Constant.NOT_FOUND_EP_MSG);
                            // responseReq = false;
                            dialog.dismiss();

                        }
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i).
                                        getJSONObject(Constant.SHOW_COL);

                                Show show = new Show();
                                show.setIdShow(obj.getString(Constant.ID_COL));
                                show.setTitle(obj.getString(Constant.NAME_COL));
                                show.setSummary(obj.getString(Constant.SUMMARY_COL));
                                //when image is NULL, the value is in STRING format
                                // otherwise, it's an object:
                                if (obj.getString(Constant.IMG_COL).equals(Constant.NULL))
                                    show.setImgUrl("");
                                else
                                    show.setImgUrl(obj.getJSONObject(Constant.IMG_COL).
                                            getString(Constant.ORIGINAL_COL));

                                //main info:
                                show.setYearPremiered(obj.getString(Constant.YEAR_COL));
                                show.setNetwork(
                                        obj.getJSONObject(Constant.NW_COL).getString(Constant.NAME_COL));
                                show.setScheduleTime(
                                        obj.getJSONObject(Constant.SCH_COL).getString(Constant.TIME_COL));
                                //schedule days:
                                ArrayList<String> tmpdays= new ArrayList<String>();
                                for (int j = 0; j < obj.getJSONObject(Constant.SCH_COL).getJSONArray(Constant.DAYS_COL).length(); j++)
                                    tmpdays.add(obj.getJSONObject(Constant.SCH_COL).getJSONArray(Constant.DAYS_COL).getString(j));
                                show.setScheduleDays(tmpdays);
                                //genres:
                                ArrayList<String> tmpGnr= new ArrayList<String>();
                                for (int j = 0; j < obj.getJSONArray(Constant.GNR_COL).length(); j++)
                                    tmpGnr.add(obj.getJSONArray(Constant.GNR_COL).getString(j));
                                show.setGenres(tmpGnr);

                                shows.add(show);

                            } catch (JSONException e) {
                               // e.printStackTrace();
                                Toast.makeText(Main2DayScheduleActivity.this,
                                        Constant.CNCT_ERR_MSG,
                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        //listener2DayActivity.onDialogResponse(false,"");
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(this).add(req);

        dialog.dismiss();
    }
}
