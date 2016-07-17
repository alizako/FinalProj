package com.example.aliza.finalproject.Classes;

import android.provider.BaseColumns;

public final class Constant {

    public static final String URL_2DAY_SHOWS="http://api.tvmaze.com/schedule?country=US&date="; //format: 2014-12-01
    public static final String URL_ALL_SHOWS= "http://api.tvmaze.com/shows/";
    public static final String URL_SHOWS = "http://api.tvmaze.com/search/shows?q=";
    public static final String URL_EPISODES = "http://api.tvmaze.com/shows/#/episodes";

    //messages
    public static final String LOAD_MSG = "Loading";
    public static final String WAIT_MSG = "Please wait for the results..";
    public static final String ERR_MSG = "ERROR: ";
    public static final String NOT_FOUND_MSG = "Couldn't find Show";
    public static final String NOT_FOUND_EP_MSG = "No episodes to display";
    public static final String CNCT_ERR_MSG = "Connection Error";
    public static final String NTF_MSG = "Schedule Set";
    public static final String NTF_NO_SCH="No Schedule to set";


    public static final String SEASON= "Season";
    public static final String EPISODE= "Episode";
    public static final String AIR_TIME= "air time";

    //intent
    public static final String INTENT_ID="id";
    public static final String INTENT_YEAR="year";
    public static final String INTENT_TITLE="title";
    public static final String INTENT_NW="ne";
    public static final String INTENT_GNR="genre";
    public static final String INTENT_SCH="schedule";
    public static final String INTENT_QUERY="QUERY";

    //days
    public static final String SUN ="Sunday";
    public static final String MON ="Monday";
    public static final String TUE ="Tuesday";
    public static final String WED ="Wednesday";
    public static final String THU ="Thursday";
    public static final String FRI ="Friday";
    public static final String SAT ="Saturday";


    //columns
    public static final String SHOW_COL="show";
    public static final String ID_COL="id";
    public static final String NAME_COL="name";
    public static final String SUMMARY_COL="summary";
    public static final String IMG_COL="image";
    public static final String ORIGINAL_COL="original";
    public static final String SEASON_COL="season";
    public static final String NUM_COL="number";
    public static final String DATE_COL="airdate";
    public static final String AIRTIME_COL="airtime";
    public static final String YEAR_COL="premiered";
    public static final String NW_COL="network";
    public static final String SCH_COL="schedule";
    public static final String TIME_COL="time";
    public static final String DAYS_COL="days";
    public static final String GNR_COL="genres";

    //Notification
    public static final String NTF_TITLE="Show Time";
    public static final String NTF_TITLE_NAME="titleAlarm";
    public static final String NTF_SCH="schAlarm";

    public static final String NULL = "null";

    //DB
    public  static abstract class Shows implements BaseColumns{
        public static final String TABLE_FAVORITE="Favorite_Shows";
        public static final String SHOW_ID_FAV="show_id";
        public static final String SHOW_NAME_FAV = "show_name";
        public static final String TABLE_SCHEDULE ="Schedule_Shows";
        public static final String SHOW_ID_SCHDL="show_id";
        public static final String SHOW_NAME_SCHDL="show_name";
        public static final String SHOW_TIME_SCHDL="show_time";
    }

    //constructor
    private Constant(){
        throw new AssertionError("Can't create constant class");
    }



}
