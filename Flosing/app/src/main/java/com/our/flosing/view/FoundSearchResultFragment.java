package com.our.flosing.view;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.our.flosing.R;
import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.FoundCardAdapter;
import com.our.flosing.presenter.FoundSearchPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by RunNishino on 2017/1/2.
 */

public class FoundSearchResultFragment extends Fragment implements IFoundSearchResultFragmentView{
    private final int RESETDATA = 1;
    private final int GETDATA = 2;

    static private FoundSearchPresenter foundSearchPresenter;
    static private List<FoundCard> mFoundCards = new ArrayList<>();
    private PullToRefreshListView listView;
    static private int pageNumber;
    FoundCardAdapter foundCardAdapter;
    String fragmentSearchName;
    String fragmentSearchType;
    String fragmentSearchDate;
    Date searchDate;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case RESETDATA:
                    foundCardAdapter.clear();
                    pageNumber = 0;
                    foundSearchPresenter.searchFounds(fragmentSearchType,fragmentSearchName,searchDate,++pageNumber);
                    break;
                case GETDATA:
                    foundSearchPresenter.searchFounds(fragmentSearchType,fragmentSearchName,searchDate,++pageNumber);
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_found_result,container,false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listview_foundcards_result);

        if (foundSearchPresenter == null) foundSearchPresenter = new FoundSearchPresenter(this);
        foundSearchPresenter.takeView(this);

        if (!"".equals(((SearchResultActivity) getActivity()).getSearchName()))
            fragmentSearchName = ((SearchResultActivity) getActivity()).getSearchName();
        if (!"".equals(((SearchResultActivity) getActivity()).getSearchType()))
            fragmentSearchType = ((SearchResultActivity) getActivity()).getSearchType();
        if (!"".equals(((SearchResultActivity) getActivity()).getSearchDate()))
            fragmentSearchDate = ((SearchResultActivity) getActivity()).getSearchDate();

        /*if (!"".equals(fragmentSearchName))*/ Log.d("searchFragment","name:"+fragmentSearchName);
        /*if (!"".equals(fragmentSearchType))*/ Log.d("searchFragment","type:"+fragmentSearchType);
        /*if (!"".equals(fragmentSearchDate))*/ Log.d("searchFragment","date:"+fragmentSearchDate);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try{
            searchDate = sdf.parse(fragmentSearchDate);
        }catch (Exception e){

        }

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;

        foundCardAdapter = new FoundCardAdapter(this.getActivity(),R.layout.foundcard_item,mFoundCards);
        listView.setAdapter(foundCardAdapter);

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
                FoundCard foundCard = mFoundCards.get(position - 1);
                Intent intent = new Intent(getActivity(),FoundDetailActivity.class);
                intent.putExtra("foundDetailId",foundCard.getId());
                startActivity(intent);
            }
        });

        return view;
    }

    private class ResetDataTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params){
            Message message = new Message();
            message.what = RESETDATA;
            handler.sendMessage(message);
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
            Message message = new Message();
            message.what = GETDATA;
            handler.sendMessage(message);
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
    public void refreshView(List<FoundCard> foundCards){
        mFoundCards.addAll(foundCards);
        foundCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();

        //清空listView
        foundCardAdapter.clear();
        listView.setAdapter(foundCardAdapter);

        //重新获取第一页数据
        pageNumber = 0;
        foundSearchPresenter.searchFounds(fragmentSearchType,fragmentSearchName,searchDate,++pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) foundSearchPresenter = null;
    }
}
