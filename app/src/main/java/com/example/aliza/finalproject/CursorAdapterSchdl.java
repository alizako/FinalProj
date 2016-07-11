package com.example.aliza.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by amalia on 10/07/2016.
 */
public class CursorAdapterSchdl extends CursorAdapter {

    LayoutInflater inflater;

    public CursorAdapterSchdl(Context context, Cursor c){
        super(context,c,true);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.activity_schedule_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Switch schdlIcon = (Switch)view.findViewById(R.id.switchSchdl);
        TextView schdlName = (TextView) view.findViewById(R.id.seriesNameSchdl);
        TextView schdlTime = (TextView) view.findViewById(R.id.scheduleTime);

        schdlIcon.setChecked(true);
        schdlName.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_NAME_SCHDL)));
        schdlTime.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_TIME_SCHDL)));
    }
}
