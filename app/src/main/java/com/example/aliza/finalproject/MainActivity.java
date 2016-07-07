package com.example.aliza.finalproject;

import android.content.res.Configuration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyClickListenerFromListFragment {

    int orientation;
    Fragment fragment, frgSearch, frgResults,mCurrentFragment,mFragmentContainer,mStackFragments ;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //save the current orientation:
        orientation= getResources().getConfiguration().orientation;

        fm = getSupportFragmentManager();
        mFragmentContainer=fm.findFragmentById(R.id.fragmentContainer);

        frgSearch = new SearchFragment();
        frgResults = new ResultFragment();

        //mStackFragments = new stack();

        FragmentTransaction trans = fm.beginTransaction();
        trans.add(android.R.id.content,frgResults,"frgResults");
        trans.hide(frgResults);

        trans.add(android.R.id.content,frgSearch,"frgSearch");
        trans.commit();

        mCurrentFragment = frgSearch;

    }

    //btnFind of fragment Search
    //btnBack of fragment Results
    @Override
    public void onButtonClick() {
       // Toast.makeText(this,mCurrentFragment.toString(), Toast.LENGTH_LONG).show();
        if (mCurrentFragment==frgSearch)showFragment(frgResults);
        else if (mCurrentFragment==frgResults)showFragment(frgSearch);
    }

    private void showFragment (Fragment fragment)
    {
        /*if (fragment.isVisible()){
            return;
        }*/
        FragmentTransaction trans = fm.beginTransaction();
        fragment.getView().bringToFront();
        mCurrentFragment.getView().bringToFront();

        trans.hide(mCurrentFragment);
        trans.show(fragment);

      //  trans.addToBackStack(null);
       // mStackFragments.Push(mCurrentFragment);
        trans.commit();

        mCurrentFragment = fragment;
    }
}
