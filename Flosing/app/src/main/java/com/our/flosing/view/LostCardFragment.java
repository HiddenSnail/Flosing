package com.our.flosing.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.LostCardAdapter;
import com.our.flosing.presenter.LostFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RunNishino on 2017/1/1.
 */

public class LostCardFragment extends Fragment implements ILostFragmentView {

    private final int RESETDATA = 1;
    private final int GETDATA = 2;

    static private List<LostCard> mLostCards = new ArrayList<>();
    LostCardAdapter lostCardAdapter;
    static private LostFragmentPresenter lostFragmentPresenter;
    static private int pageNumber;
    private PullToRefreshListView listView;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case RESETDATA:
                    lostCardAdapter.clear();
                    pageNumber = 0;
                    lostFragmentPresenter.getPageOfLosts(++pageNumber);
                    break;
                case GETDATA:
                    lostFragmentPresenter.getPageOfLosts(++pageNumber);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lost_list,container,false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listview_lostcards);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;

        if (lostFragmentPresenter == null) lostFragmentPresenter = new LostFragmentPresenter(this);
        lostFragmentPresenter.takeView(this);

        //TODO:判断在fragment中使用哪个context
        lostCardAdapter = new LostCardAdapter(this.getActivity(), R.layout.lostcard_item, mLostCards);
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
                LostCard lostCard = mLostCards.get(position - 1);
                Intent intent = new Intent(getActivity(),LostDetailActivity.class);
                intent.putExtra("lostDetailId",lostCard.getId());
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
//            lostCardAdapter.clear();
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
    public void refreshView(List<LostCard> lostCards) {

        //将新一页的数据放入adapter并更新
        mLostCards.addAll(lostCards);
        lostCardAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();

        //清空listView
        lostCardAdapter.clear();
        listView.setAdapter(lostCardAdapter);

        //重新获取第一页数据
        pageNumber = 0;
        lostFragmentPresenter.getPageOfLosts(++pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) lostFragmentPresenter = null;
    }


}
