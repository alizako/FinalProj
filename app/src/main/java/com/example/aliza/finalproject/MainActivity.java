package com.example.aliza.finalproject;

import android.content.res.Configuration;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MyClickListenerFromListFragment {

    int orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //save the current orientation:
        orientation= getResources().getConfiguration().orientation;

    }

    @Override
    public void onButtonClick() { //btnFind of fragment Search Filter

        if (orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            SearchResultActivity resultFragment = new SearchResultActivity();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace (R.id.searchFilter,resultFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
