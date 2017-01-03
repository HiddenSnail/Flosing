package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.our.flosing.R;

/**
 * Created by huangrui on 2016/12/29.
 */

public class MainActivity extends AppCompatActivity {
    LostCardFragment lostCardFragment;
    FoundCardFragment foundCardFragment;

    private Button lost_list;
    private Button find_list;
    private Button search;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        lost_list = (Button) findViewById(R.id.lost_list);
        find_list = (Button) findViewById(R.id.find_list);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

//        默认fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        lostCardFragment = new LostCardFragment();
        fragmentTransaction.replace(R.id.content,lostCardFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();


        //TODO:改为隐藏
        lost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lostCardFragment == null) {
                    lostCardFragment = new LostCardFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content,lostCardFragment);
                fragmentTransaction.commit();
            }
        });

        find_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foundCardFragment == null) {
                    foundCardFragment = new FoundCardFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, foundCardFragment);
                fragmentTransaction.commit();
            }
        });

//        lostCardFragment = (LostCardFragment) getSupportFragmentManager().findFragmentById(R.id.content);
//        listView = lostCardFragment.getPullToRefreshListView();
//
//        listView = (PullToRefreshListView) findViewById(R.id.listview_lostcards);

        //TODO:方便使用，到时候去掉
        Button logout = (Button) findViewById(R.id.person);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AVUser.getCurrentUser().logOut();
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                MainActivity.this.finish();
            }
        });

        //Lost:寻物启事
        final Button lostPublish = (Button) findViewById(R.id.lost_publish);
        lostPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LostPublishActivity.class));
            }
        });

        //Found:失物招领
        final Button foundPublish = (Button) findViewById(R.id.found_publish);
        foundPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoundPublishActivity.class));
            }
        });
    }

}