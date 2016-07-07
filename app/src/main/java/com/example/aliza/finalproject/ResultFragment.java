package com.example.aliza.finalproject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ResultFragment extends Fragment {

    MyClickListenerFromListFragment mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_result, container, false);

        final Button btnFind =(Button) view.findViewById(R.id.btnBack);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick();
            }
        });

        //TODO:
        //displayList


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
