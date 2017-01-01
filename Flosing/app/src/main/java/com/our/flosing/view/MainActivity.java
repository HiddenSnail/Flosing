package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.LostCardAdapter;
import com.our.flosing.presenter.LostFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangrui on 2016/12/29.
 */

public class MainActivity extends AppCompatActivity {
    private PullToRefreshListView listView;
    LostCardFragment lostCardFragment;

    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button lost_list = (Button) findViewById(R.id.lost_list);
        Button find_list = (Button) findViewById(R.id.find_list);

        //默认fragment
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content,new LostCardFragment());
//        fragmentTransaction.commit();

        lostCardFragment = (LostCardFragment) getFragmentManager().findFragmentById(R.id.content);
        listView = lostCardFragment.getPullToRefreshListView();

        listView = (PullToRefreshListView) findViewById(R.id.listview_lostcards);

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