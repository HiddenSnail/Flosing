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
import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.FoundCardAdapter;
import com.our.flosing.bean.LostCard;
import com.our.flosing.presenter.PersonFoundPresenter;
import com.our.flosing.presenter.PersonLostPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RunNishino on 2017/1/4.
 */

public class PersonFoundFragment extends Fragment implements IFoundPersonFragmentView {

    private final int RESETDATA = 1;
    private final int GETDATA = 2;

    private List<FoundCard> mFoundCards = new ArrayList<>();
    FoundCardAdapter foundCardAdapter;
    static private PersonFoundPresenter personFoundPresenter;
    static private int pageNumber;
    private PullToRefreshListView listView;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case RESETDATA:
                    foundCardAdapter.clear();
                    pageNumber = 0;
                    personFoundPresenter.getPersonFound(++pageNumber);
                    break;
                case GETDATA:
                    personFoundPresenter.getPersonFound(++pageNumber);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_found_person, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listview_foundcards_person);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        pageNumber = 1;

        if (personFoundPresenter == null) personFoundPresenter = new PersonFoundPresenter(this);
        personFoundPresenter.takeView(this);

        foundCardAdapter = new FoundCardAdapter(this.getActivity(), R.layout.foundcard_item, mFoundCards);
        foundCardAdapter.clear();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FoundCard foundCard = mFoundCards.get(position - 1);
                if (!foundCard.getIsFinish()) {
                    Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                    intent.putExtra("cardID", "F" + foundCard.getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"这件物品已经归还",Toast.LENGTH_SHORT).show();
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
        personFoundPresenter.getPersonFound(++pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) personFoundPresenter = null;
    }
}
