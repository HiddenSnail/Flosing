package com.our.flosing.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.LostCardAdapter;
import com.our.flosing.presenter.PersonLostPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RunNishino on 2017/1/3.
 */

public class PersonLostFragment extends Fragment implements ILostPersonFragmentView {

    private final int RESETDATA = 1;
    private final int GETDATA = 2;

    private List<LostCard> mLostCards = new ArrayList<>();
    LostCardAdapter lostCardAdapter;
    static private PersonLostPresenter personLostPresenter;
    static private int pageNumber;
    private PullToRefreshListView listView;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case RESETDATA:
                    lostCardAdapter.clear();
                    pageNumber = 0;
                    personLostPresenter.getPersonLost(++pageNumber);
                    break;
                case GETDATA:
                    personLostPresenter.getPersonLost(++pageNumber);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_lost_person, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listview_lostcards_person);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;

        if (personLostPresenter == null) personLostPresenter = new PersonLostPresenter(this);
        personLostPresenter.takeView(this);

        lostCardAdapter = new LostCardAdapter(this.getActivity(), R.layout.lostcard_item, mLostCards);
        lostCardAdapter.clear();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LostCard lostCard = mLostCards.get(position - 1);
                if (!lostCard.getIsFinish()) {
                    Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                    intent.putExtra("cardID", "L" + lostCard.getId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), LostDetailActivity.class);
                    intent.putExtra("lostDetailId", lostCard.getId());
                    startActivity(intent);
                }
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
        personLostPresenter.getPersonLost(++pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) personLostPresenter = null;
    }
}
