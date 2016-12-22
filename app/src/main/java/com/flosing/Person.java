package com.flosing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 56468 on 2016/12/21.
 */

public class Person extends Activity {
    private ListView plv;
    private List<Map<String,Object>> data;
    private ImageButton btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.person);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.person_title);

        plv=(ListView)findViewById(R.id.plv);
        data=getData();
        Person.MyAdapter adapter=new Person.MyAdapter(this);
        plv.setAdapter(adapter);

        btn_home=(ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(listener);
    }
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton btn = (ImageButton) v;
            switch (btn.getId()) {
                case R.id.btn_home : {
                    Intent intent = new Intent();
                    intent.setClass(Person.this, Home.class);
                    startActivity(intent);
                }
                break;
            }
        }
    };
    private List<Map<String,Object>> getData()
    {
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map;

            map=new HashMap<String,Object>();
            map.put("pt","修改联系方式");
            list.add(map);
        map=new HashMap<String,Object>();
        map.put("pt","修改密码");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("pt","历史相关帖子");
        list.add(map);
        return list;
    }
    static class ViewHolder
    {

        public TextView pt;

    }

    public class MyAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater = null;
        private MyAdapter(Context context)
        {
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            //How many items are in the data set represented by this Adapter.
            //在此适配器中所代表的数据集中的条目数
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            // Get the data item associated with the spec ified position in the data set.
            //获取数据集中与指定索引对应的数据项
            return position;
        }
        @Override
        public long getItemId(int position) {
            //Get the row id associated with the specified position in the list.
            //获取在列表中与指定索引对应的行id
            return position;
        }

        //Get a View that displays the data at the specified position in the data set.
        //获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person.ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null)
            {
                holder = new Person.ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.plist, null);
                holder.pt = (TextView)convertView.findViewById(R.id.pt);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }else
            {
                holder = (Person.ViewHolder)convertView.getTag();
            }
            holder.pt.setText((String)data.get(position).get("pt"));
            return convertView;
        }

    }
}
