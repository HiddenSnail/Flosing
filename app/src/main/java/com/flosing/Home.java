package com.flosing;

import android.app.Activity;
import android.content.Context;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.R.id.list;

public class Home extends Activity {
    private ListView lv;
    private List<Map<String,Object>> data;
    private ImageButton btn_person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_home);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.home_title_find);
        lv=(ListView)findViewById(R.id.lv);
        data=getData();
        MyAdapter adapter=new MyAdapter(this);
        lv.setAdapter(adapter);

        btn_person=(ImageButton) findViewById(R.id.btn_person);
        btn_person.setOnClickListener(listener);
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton btn = (ImageButton) v;
            switch (btn.getId()) {
                case R.id.btn_person: {
                    Intent intent = new Intent();
                    intent.setClass(Home.this, Person.class);
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
        for(int i=0;i<10;i++)
        {
            map=new HashMap<String,Object>();
            map.put("img",R.drawable.protrait);
            map.put("name","小王");
            map.put("announce_time","12.22 8:00");
            map.put("img2",R.drawable.umbrella);
            list.add(map);
        }
        return list;
    }
    static class ViewHolder
    {
        public ImageView img;
        public TextView name;
        public TextView announce_time;
        public ImageView img2;
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
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null)
            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.flist, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.name = (TextView)convertView.findViewById(R.id.name);
                holder.announce_time = (TextView)convertView.findViewById(R.id.announce_time);
                holder.img2=(ImageView)convertView.findViewById(R.id.img2);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
            holder.name.setText((String)data.get(position).get("name"));
            holder.announce_time.setText((String)data.get(position).get("announce_time"));
            holder.img2.setBackgroundResource((Integer)data.get(position).get("img2"));
            return convertView;
        }

    }
}
