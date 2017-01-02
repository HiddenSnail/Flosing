package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.our.flosing.R;

/**
 * Created by RunNishino on 2017/1/2.
 */

public class SearchResultActivity extends AppCompatActivity {

    private String searchName = "";
    private String searchType = "";
    private String searchDate = "";

    LostSearchSearchResultFragment lostSearchResultFragment;

    private Button lost_list;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_result);

        lost_list = (Button) findViewById(R.id.search_result_lost);

        Intent intent = getIntent();

        searchName = intent.getStringExtra("searchName");
        searchType = intent.getStringExtra("searchType");
        searchDate = intent.getStringExtra("searchDate");


        lostSearchResultFragment = new LostSearchSearchResultFragment();


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.search_content,lostSearchResultFragment);
        fragmentTransaction.commit();
    }

    public String getSearchName(){
        return this.searchName;
    }

    public String getSearchType(){
        return this.searchType;
    }

    public String getSearchDate(){
        return this.searchDate;
    }
}
