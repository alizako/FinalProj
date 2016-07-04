package com.example.aliza.finalproject;

public final class Constant {

    public static final String URL_SHOWS = "http://api.tvmaze.com/search/shows?q=";
    public static final String URL_EPISODES = "http://api.tvmaze.com/shows/#/episodes";
    public static final String LOAD_MSG = "Loading";
    public static final String WAIT_MSG = "Please wait for the results..";
    public static final String ERR_MSG = "ERROR: ";
    public static final String NOT_FOUND_MSG = "Couldn't find Show";
    public static final String NOT_FOUND_EP_MSG = "No episodes to display";

    public static final String SHOW_COL="show";
    public static final String ID_COL="id";
    public static final String NAME_COL="name";
    public static final String SUMMARY_COL="summary";
    public static final String IMG_COL="image";
    public static final String ORIGINAL_COL="original";
    public static final String SEASON_COL="season";
    public static final String NUM_COL="number";
    public static final String DATE_COL="airdate";
    public static final String TIME_COL="airtime";

    public static final String NULL = "null";

    //constructor
    private Constant(){
        throw new AssertionError("Can't create constant class");
    }



}
