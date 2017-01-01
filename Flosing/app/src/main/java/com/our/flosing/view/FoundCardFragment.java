package com.our.flosing.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.our.flosing.bean.LostCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RunNishino on 2017/1/1.
 */


//TODO:将foundFragmentPresenter添加进代码
public class FoundCardFragment extends Fragment implements IFoundFragmentView{

    static private List<FoundCard> mFoundCards = new ArrayList<>();
    FoundCardAdapter foundCardAdapter;
//    static private FoundFragmentPresenter foundFragmentPresenter;
    static private int pageNumber;
    private PullToRefreshListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_found_list, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listview_foundcards);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;

//        if (foundFragmentPresenter == null) foundFragmentPresenter = new FoundFragmentPresenter(this);
//        foundFragmentPresenter.takeView(this);

        foundCardAdapter = new FoundCardAdapter(this.getActivity(),R.layout.fragment_found_list,mFoundCards);
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
    public PullToRefreshListView getPullToRefreshListView(){
        return listView;
    }

    private class ResetDataTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params){
            foundCardAdapter.clear();
            pageNumber = 0;
//            foundFragmentPresenter.getPageOfLosts(++pageNumber);
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
//            foundFragmentPresenter.getPageOfLosts(++pageNumber);
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
    public void refreshView(List<FoundCard> foundCards) {

        //将新一页的数据放入adapter并更新
        mFoundCards.addAll(foundCards);
        foundCardAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {
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
//        foundFragmentPresenter.getPageOfLosts(++pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (getActivity().isFinishing()) foundFragmentPresenter = null;
    }

}
