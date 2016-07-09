package com.example.aliza.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {
    MyClickListenerFromListFragment mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);

        //TODO: display list

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
