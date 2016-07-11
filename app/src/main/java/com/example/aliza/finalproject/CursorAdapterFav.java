package com.example.aliza.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by amalia on 10/07/2016.
 */
public class CursorAdapterFav extends CursorAdapter {

    LayoutInflater inflater;

    public CursorAdapterFav(Context context, Cursor c){
        super(context,c,true);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.activity_favorite_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        RatingBar favIcon = (RatingBar)view.findViewById(R.id.favoriteIcon);
        TextView favName = (TextView) view.findViewById(R.id.seriesName);

        favIcon.setRating(1);
        favName.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_NAME_FAV)));

    }
}
