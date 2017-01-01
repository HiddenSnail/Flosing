package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.our.flosing.R;

/**
 * Created by huangrui on 2016/12/29.
 */

public class MainActivity extends AppCompatActivity {
    LostCardFragment lostCardFragment;
    FindCardFragment findCardFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button lost_list = (Button) findViewById(R.id.lost_list);
        final Button find_list = (Button) findViewById(R.id.find_list);

//        默认fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        lostCardFragment = new LostCardFragment();
        fragmentTransaction.replace(R.id.content,lostCardFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();

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
                if (findCardFragment == null) {
                    findCardFragment = new FindCardFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content,findCardFragment);
                fragmentTransaction.commit();
            }
        });

//        lostCardFragment = (LostCardFragment) getSupportFragmentManager().findFragmentById(R.id.content);
//        listView = lostCardFragment.getPullToRefreshListView();
//
//        listView = (PullToRefreshListView) findViewById(R.id.listview_lostcards);

        //TODO:方便使用，到时候去掉
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        final Button lostPublish = (Button) findViewById(R.id.lost_publish);
        lostPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LostPublishActivity.class));
            }
        });
    }

}