package com.example.aliza.finalproject;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliza on 07/07/2016.
 */
public class JsonExtractor {

    private List<Show> shows;
    private List<Episode> episodes;
    private CustomListAdapter adapter;
    private CustomListAdapterEpisodes adapterEpisode;
    private ListView listViewShows;
    private ListView listViewEpisodes;

    private boolean responseReq;

    public JsonExtractor(ListView listView) {

        this.listViewEpisodes = listView;
        this.listViewShows = listView;
        this.responseReq = true;

    }

    // Getters & Setters **************************************************************
    public CustomListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomListAdapter adapter) {
        this.adapter = adapter;
    }

    public CustomListAdapterEpisodes getAdapterEpisode() {
        return adapterEpisode;
    }

    public void setAdapterEpisode(CustomListAdapterEpisodes adapterEpisode) {
        this.adapterEpisode = adapterEpisode;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public ListView getListViewShows() {
        return listViewShows;
    }

    public void setListViewShows() {
        this.listViewShows.setAdapter(adapter);
    }

    public ListView getListViewEpisodes() {
        return listViewEpisodes;
    }

    public void setListViewEpisodes() {

        this.listViewEpisodes.setAdapter(adapterEpisode);
    }

    // Shows ********************************************************************************
    public Boolean getJsonShows(final String tmpUrl, final Context context) {

        responseReq = true;


        //test:
        //tmpUrl= Constant.URL_SHOWS_GENERAL+69;

        JsonArrayRequest showReq = new JsonArrayRequest(tmpUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                        /*Toast.makeText(MainActivity.this,
                                "response: " + response.toString(),
                                Toast.LENGTH_LONG).show();*/

                if (response.length() == 0 || response == null) {
                    /*Toast.makeText(MainActivity.this,
                            Constant.NOT_FOUND_MSG,
                            Toast.LENGTH_LONG).show();*/
                    responseReq = false;
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
                                obj.getJSONObject("network").getString("name")
                        );                        show.setScheduleTime(obj.getJSONObject("schedule").getString("time"));
                        //schedule days:
                        ArrayList<String> tmpdays= new ArrayList<String>();
                        for (int j = 0; j < obj.getJSONObject("schedule").getJSONArray("days").length(); j++)
                            tmpdays.add(obj.getJSONObject("schedule").getJSONArray("days").getString(j));
                        show.setScheduleDays(tmpdays);
                        //genres:
                        ArrayList<String> tmpGnr= new ArrayList<String>();
                        for (int j = 0; j < obj.getJSONArray("genres").length(); j++)
                            tmpGnr.add(obj.getJSONArray("genres").getString(j));
                        show.setGenres(tmpGnr);

                        //need more general details:
                        //show = getJsonShowGeneralDetails(
                       // show = tmp(tmpUrl,show,context);
                            //    Constant.URL_SHOWS_GENERAL + show.getIdShow(),show,context);

                        // adding movie to movies array
                        shows.add(show);

                                /*Toast.makeText(MainActivity.this,
                                        show.toString(),
                                        Toast.LENGTH_LONG).show();*/

                        //   responseReq = false;//dialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                responseReq = false;//dialog.dismiss();
                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  VolleyLog.d(TAG, "Error: " + error.getMessage());
                responseReq = false;//dialog.dismiss();

            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(context).add(showReq);

        return responseReq;
    }


    // Episodes *****************************************************************************
    public void getJsonEpisodes(String tmpUrl, Context context) {

        responseReq = true;

        JsonArrayRequest showReq = new JsonArrayRequest(tmpUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /*Toast.makeText(MainActivity.this,
                                "response: " + response.toString(),
                                Toast.LENGTH_LONG).show();*/

                        if (response.length() == 0 || response == null) {
                            /*Toast.makeText(MainActivity.this,
                                    Constant.NOT_FOUND_EP_MSG,
                                    Toast.LENGTH_LONG).show();*/
                            responseReq = false;
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

                                episode.setEpisodeDetails("Season " + obj.getString(Constant.SEASON_COL) +
                                        ", Episode " + obj.getString(Constant.NUM_COL) +
                                        ", air time " + obj.getString(Constant.DATE_COL) +
                                        " " + obj.getString(Constant.TIME_COL));

                                // adding movie to movies array
                                episodes.add(episode);

                                /*Toast.makeText(MainActivity.this,
                                        show.toString(),
                                        Toast.LENGTH_LONG).show();*/

                                responseReq = false;//dialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        responseReq = false;//dialog.dismiss();
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapterEpisode.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  VolleyLog.d(TAG, "Error: " + error.getMessage());
                responseReq = false;//dialog.dismiss();

            }
        });
        // Adding request to request queue
        Volley.newRequestQueue(context).add(showReq);
    }




}