package com.example.aliza.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    MyClickListenerFromListFragment mListener;
    ProgressDialog dialog;
    JsonExtractor jsonExtractor;
    CustomListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        jsonExtractor= new JsonExtractor();

        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.networks_spinner);
        final ArrayAdapter<CharSequence> spnrAdapter = ArrayAdapter.createFromResource(view.getContext(),
        R.array.networks_array, android.R.layout.simple_spinner_item);
        spnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spnrAdapter);

        //components
        final EditText etQuery = (EditText) view.findViewById(R.id.etQuery);
        final Button btnFind =(Button) view.findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonExtractor.setShows(new ArrayList<Show>());
                adapter = new CustomListAdapter(getActivity(),jsonExtractor.getShows());
                jsonExtractor.setAdapter(adapter);
                jsonExtractor.setListViewShows();
               // isEpisodeList=false;

                try{
                    dialog= ProgressDialog.show(getActivity(),
                            Constant.LOAD_MSG,
                            Constant.WAIT_MSG,
                            true);
                    String tmpUrl = Constant.URL_SHOWS + etQuery.getText().toString();

                    // get Json for show inserted by the user
                    jsonExtractor.getJsonShows(tmpUrl,getContext());
                }
                catch(Exception e){
                    dialog.dismiss();
                    Toast.makeText(getActivity(),
                            Constant.ERR_MSG + e.toString(),
                            Toast.LENGTH_LONG).show();
                }

             //   mListener.onButtonClick();
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
