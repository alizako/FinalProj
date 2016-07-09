package com.example.aliza.finalproject;


import android.view.View;

public interface MyClickListenerFromListFragment {
    public void onButtonClick(View view);
    public void onItemClick(View view,int position);
    public void onFavoriteClick(int show_id);
    public void onScheduleClick(int show_id);
    public void onMyZoneClick(View view);
}
