package com.example.aliza.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyClickListenerFromListFragment {

    int orientation;
    Fragment fragment, frgSearch, frgResults,mCurrentFragment,mFragmentContainer,mStackFragments ;
    FragmentManager fm;
    CustomListAdapterEpisodes adapterEpisodes;
    ProgressDialog dialog;

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
    public void onButtonClick(View view) {
       // Toast.makeText(this,mCurrentFragment.toString(), Toast.LENGTH_LONG).show();
        if (mCurrentFragment==frgSearch) {//Find Button

            //close keyboard after clicking button:
            InputMethodManager imm = (InputMethodManager)getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Global.SetVisibilityShowDetails(View.GONE);

            Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

            //shows adapter:
            Global.getJsonExtractor().getListViewShows().
                    setAdapter(Global.getJsonExtractor().getAdapter());


            showFragment(frgResults);
        }

        else if (mCurrentFragment==frgResults){
            //  Global.getTvTitleResults().setText("Episodes for Show:" + Global.getStrQuery());

            if (Global.isEpisodeList()){

                //preperation for list of show
                Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

                Global.SetVisibilityShowDetails(View.GONE);

                //shows adapter:
                Global.getJsonExtractor().getListViewShows().
                        setAdapter(Global.getJsonExtractor().getAdapter());

                Global.setEpisodeList(false);
                showFragment(frgResults);
            }
            else{
                //preperation for Search
                Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());
                Global.SetVisibilityShowDetails(View.GONE);
                Global.setEpisodeList(false);
                showFragment(frgSearch);
            }
        }

    }

    @Override
    public void onItemClick(View view,int position) {

       /* if (Global.isEpisodeList()) return; //nothing to do when clicking on item of episodes list
        Show show = Global.getJsonExtractor().getShows().get(position);//get the position in the list to take the Show id
        Global.getJsonExtractor().setEpisodes(new ArrayList<Episode>());
        adapterEpisodes = new CustomListAdapterEpisodes(MainActivity.this,Global.getJsonExtractor().getEpisodes());
        Global.getJsonExtractor().setAdapterEpisode(adapterEpisodes);

        try{
            dialog= ProgressDialog.show(MainActivity.this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            String tmpEpisodesUrl = Constant.URL_EPISODES.replace("#", show.getIdShow()) ;
            Global.setEpisodeList(true);
            Global.getJsonExtractor().getJsonEpisodes(tmpEpisodesUrl,this); // get Json for all episodes of the show chosen by the user
            (Global.getJsonExtractor().getAdapterEpisode()).notifyDataSetChanged();
            dialog.dismiss();
        }
        catch(Exception e){
            dialog.dismiss();
            Toast.makeText(MainActivity.this,
                    Constant.ERR_MSG + e.toString(),
                    Toast.LENGTH_LONG).show();
        }*/

        Global.SetVisibilityShowDetails(View.VISIBLE);
        Global.getTvTitleResults().setText("Episodes for Show:" + Global.getStrChosenShow());


        //Episodes adapter:
        Global.getJsonExtractor().getListViewEpisodes().
                setAdapter(Global.getJsonExtractor().getAdapterEpisode());

       // showFragment(frgSearch);

    }

    @Override
    public void onFavoriteClick(int show_id) {

    }

    @Override
    public void onScheduleClick(int show_id) {

    }

    @Override
    public void onMyZoneClick(View view) {
        Intent intent = new Intent (MainActivity.this,myZone.class);
        startActivity(intent);
    }


    //********************************************************************************

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
