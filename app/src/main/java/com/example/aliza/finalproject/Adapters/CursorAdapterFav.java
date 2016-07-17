package com.example.aliza.finalproject.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aliza.finalproject.DB.AssingmentsDBHelper;
import com.example.aliza.finalproject.Classes.Constant;
import com.example.aliza.finalproject.R;

public class CursorAdapterFav extends CursorAdapter {

    private LayoutInflater inflater;
    int idShow;

    AssingmentsDBHelper dbHelper;
    SQLiteDatabase db;
   // CursorAdapterFav cursorAdapterFavorite;

    public CursorAdapterFav(Context context, Cursor c){
        super(context,c,true);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.activity_favorite_row,parent,false);
    }
   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) convertView.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_favorite_row, null);
        RatingBar favIcon = (RatingBar)convertView.findViewById(R.id.favoriteIcon);
        TextView favName = (TextView) convertView.findViewById(R.id.seriesName);
        //  TextView idHidden =(TextView) view.findViewById(R.id.tvHidden);

        //


        return convertView;
    }*/

    @Override
    public void bindView( View view, final Context context, Cursor cursor) {
        if (inflater == null)
            inflater = (LayoutInflater) view.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.activity_favorite_row,null,false);

        RatingBar favIcon = (RatingBar)view.findViewById(R.id.favoriteIcon);
        TextView favName = (TextView) view.findViewById(R.id.seriesName);

        favName.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_NAME_FAV)));
        idShow=Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_ID_FAV)));
        favIcon.setTag(idShow);
        favIcon.setRating(1);

        favIcon.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 if (rating==0)
                     onFavoriteClick(context,ratingBar.getTag()+"","",false);

             }
         }
        );

    }

    public void onFavoriteClick(Context context,String show_id, String show_name, boolean isFavorite)
    {
        //insert the show_id, show_title to the table
        if(isFavorite)
            AssingmentsDBHelper.InsertFavorite(context,show_id,show_name);

            //remove the show_id from the table
        else
            AssingmentsDBHelper.DeleteFavorite(context,show_id);

        //update cursor
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        db = dbHelper.getReadableDatabase();
        Cursor newcFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        this.changeCursor(newcFavorite);
        this.notifyDataSetChanged();
    }
}
