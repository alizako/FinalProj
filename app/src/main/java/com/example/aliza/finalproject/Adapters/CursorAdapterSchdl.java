package com.example.aliza.finalproject.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aliza.finalproject.DB.AssingmentsDBHelper;
import com.example.aliza.finalproject.Classes.Constant;
import com.example.aliza.finalproject.R;


public class CursorAdapterSchdl extends CursorAdapter {

    LayoutInflater inflater;
    AssingmentsDBHelper dbHelper;
    SQLiteDatabase db;
    int idShow;

    public CursorAdapterSchdl(Context context, Cursor c){
        super(context,c,true);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.activity_schedule_row,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        if (inflater == null)
            inflater = (LayoutInflater) view.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.activity_favorite_row,null,false);

        Switch schdlIcon = (Switch)view.findViewById(R.id.switchSchdl);
        TextView schdlName = (TextView) view.findViewById(R.id.seriesNameSchdl);
        TextView schdlTime = (TextView) view.findViewById(R.id.scheduleTime);

        schdlIcon.setChecked(true);
        schdlName.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_NAME_SCHDL)));
        schdlTime.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_TIME_SCHDL)));

        idShow=Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_ID_SCHDL)));
        schdlIcon.setTag(idShow);
        schdlIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    onScheduleClick(context,buttonView.getTag()+"","","",false);
            }
        });
    }


    public void onScheduleClick(Context context, String show_id, String show_name, String show_time, boolean isScheduled)
    {
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);

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
        changeCursor(newcSchedule);
        notifyDataSetChanged();
    }
}
