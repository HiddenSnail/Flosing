package com.our.flosing.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.LostCardAdapter;
import com.our.flosing.presenter.HomePagePresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangrui on 2016/12/29.
 */

public class MainActivity extends AppCompatActivity implements IHomePageView {
    static private HomePagePresenter homePagePresenter;
    static private int pageNumber;
    private PullToRefreshListView listView;
    static private List<LostCard> mLostCards = new ArrayList<>();
    LostCardAdapter lostCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (PullToRefreshListView) findViewById(R.id.listview_lostcards);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;



        if (homePagePresenter == null) homePagePresenter = new HomePagePresenter(this);
        homePagePresenter.takeView(this);

        lostCardAdapter = new LostCardAdapter(MainActivity.this, R.layout.lostcard_item, mLostCards);
        listView.setAdapter(lostCardAdapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new ResetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }
        });

        //子项点击事件，将子项id带入intent中启动lostDetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LostCard lostCard = mLostCards.get(position);
                Intent intent = new Intent(MainActivity.this,LostDetailActivity.class);
                intent.putExtra("lostDetailId",lostCard.getId());
                startActivity(intent);
            }
        });



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

    private class ResetDataTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params){
            lostCardAdapter.clear();
            pageNumber = 0;
            homePagePresenter.getPageOfLosts(++pageNumber);
            return "";
        }

        @Override
        protected void onPostExecute(String result){
            listView.onRefreshComplete();
        }
    }

    private class GetDataTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params){
            homePagePresenter.getPageOfLosts(++pageNumber);
            return "";
        }

        @Override
        protected void onPostExecute(String result){
            listView.onRefreshComplete();
        }

    }


    @Override
    public void updateView() {
    }

    @Override
    public void refreshView(List<LostCard> lostCards) {

        //将新一页的数据放入adapter并更新
        mLostCards.addAll(lostCards);
        lostCardAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onResume(){
        super.onResume();

        //清空listView
        lostCardAdapter.clear();
        listView.setAdapter(lostCardAdapter);

        //重新获取第一页数据
        pageNumber = 0;
        homePagePresenter.getPageOfLosts(++pageNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) homePagePresenter = null;
    }
}

//    private void initLostCards() {
//        LostCard lostCard1 = new LostCard();
//        lostCard1.setTitle("title");
//        lostCard1.setName("wallet");
//        lostCard1.setType("钱包");
//        lostCard1.setSDate(new Date());
//        lostCard1.setEDate(new Date());
//        lostCards.add(lostCard1);
//        LostCard lostCard2 = new LostCard();
//        lostCards.add(lostCard2);
//        LostCard lostCard3 = new LostCard();
//        lostCards.add(lostCard3);
//        LostCard lostCard4 = new LostCard();
//        lostCards.add(lostCard4);
//        LostCard lostCard5 = new LostCard();
//        lostCards.add(lostCard5);
//        LostCard lostCard6 = new LostCard();
//        lostCards.add(lostCard6);
//        LostCard lostCard7 = new LostCard();
//        lostCards.add(lostCard7);
//        LostCard lostCard8 = new LostCard();
//        lostCards.add(lostCard8);
//        LostCard lostCard9 = new LostCard();
//        lostCards.add(lostCard9);
//        LostCard lostCard10 = new LostCard();
//        lostCards.add(lostCard10);
//        LostCard lostCard11 = new LostCard();
//        lostCards.add(lostCard11);
//    }