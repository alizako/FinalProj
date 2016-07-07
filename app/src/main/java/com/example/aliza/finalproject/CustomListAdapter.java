package com.example.aliza.finalproject;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aliza on 18/05/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Show> showItems;
    ImageLoader imageLoader ;


   public CustomListAdapter(Activity activity, List<Show> showItems) {
        this.activity = activity;
        this.showItems = showItems;
    }

    @Override
    public int getCount() {
        return showItems.size();
    }

    @Override
    public Object getItem(int location) {
        return showItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

   @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_row, null);

       // getting show data for the row
       Show show = showItems.get(position);
       TextView title = (TextView) convertView.findViewById(R.id.title);
       TextView summary = (TextView) convertView.findViewById(R.id.summary);
       ImageView imageView = (ImageView)convertView.findViewById(R.id.imgVw);
       TextView episodeDtl = (TextView) convertView.findViewById(R.id.episode);


       // image-  Picasso does Automatic memory and disk caching:
       if (show.getImgUrl().equals("")) {
           Picasso.with(convertView.getContext()).
                   load(R.drawable.img_not_fnd).into(imageView);
       }
       else{
           Picasso.with(convertView.getContext()).
                 load(show.getImgUrl()).into(imageView);
       }


        // title
       title.setText(show.getTitle());

       // summary- fixing HTML tags display
       //  summary.setText(show.getSummary());
       String formattedText = show.getSummary();
       Spanned result = Html.fromHtml(formattedText);
       summary.setText(result);

       //episode date+time
       episodeDtl.setText(show.getEpisodeDetails());

       return convertView;
    }
}
