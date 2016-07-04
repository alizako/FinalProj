package com.example.aliza.finalproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SearchFilterActivity extends Fragment {

   MyClickListenerFromListFragment mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_search_filter, container, false);

        final Button btnFind =(Button) view.findViewById(R.id.btnFind);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                // SEARCH!!
                mListener.onButtonClick();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (MyClickListenerFromListFragment) context;
        }
        catch(ClassCastException e){
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
        }
    }
}
