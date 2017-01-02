package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.our.flosing.R;

/**
 * Created by RunNishino on 2017/1/2.
 */

public class SearchResultActivity extends AppCompatActivity {

    private String searchName = "";
    private String searchType = "";
    private String searchDate = "";

    LostSearchResultFragment lostSearchResultFragment;
    FoundSearchResultFragment foundSearchResultFragment;

    private Button lost_list;
    private Button found_list;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_result);

        lost_list = (Button) findViewById(R.id.search_result_lost);
        found_list = (Button) findViewById(R.id.search_result_found);

        Intent intent = getIntent();

        searchName = intent.getStringExtra("searchName");
        searchType = intent.getStringExtra("searchType");
        searchDate = intent.getStringExtra("searchDate");


        lostSearchResultFragment = new LostSearchResultFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        lostSearchResultFragment = new LostSearchResultFragment();
        fragmentTransaction.replace(R.id.search_content,lostSearchResultFragment);
        fragmentTransaction.commit();


        fragmentTransaction = fragmentManager.beginTransaction();

        lost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lostSearchResultFragment == null) {
                    lostSearchResultFragment = new LostSearchResultFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.search_content,lostSearchResultFragment);
                fragmentTransaction.commit();
            }
        });

        found_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foundSearchResultFragment == null) {
                    foundSearchResultFragment = new FoundSearchResultFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.search_content,foundSearchResultFragment );
                fragmentTransaction.commit();
            }
        });

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
