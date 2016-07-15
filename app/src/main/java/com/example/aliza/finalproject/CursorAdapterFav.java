package com.example.aliza.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    String idShow;

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

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        RatingBar favIcon = (RatingBar)view.findViewById(R.id.favoriteIcon);
        TextView favName = (TextView) view.findViewById(R.id.seriesName);
      //  TextView idHidden =(TextView) view.findViewById(R.id.tvHidden);

        favIcon.setRating(1);
        favName.setText(cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_NAME_FAV)));

        idShow=cursor.getString(cursor.getColumnIndex(Constant.Shows.SHOW_ID_FAV));
      //  idHidden.setText(idShow);

        favIcon.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 if (rating==0)
                     onFavoriteClick(context,
                             getIdShow(),"",false);

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
        dbHelper = new AssingmentsDBHelper(context);
        db = dbHelper.getReadableDatabase();
        Cursor newcFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        changeCursor(newcFavorite);
        notifyDataSetChanged();
    }

    public String getIdShow() {
        return idShow;
    }
}
