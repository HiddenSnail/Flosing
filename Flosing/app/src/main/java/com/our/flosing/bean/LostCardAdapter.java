package com.our.flosing.bean;

import android.content.Context;
//import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.our.flosing.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by RunNishino on 2016/12/29.
 */


//TODO:未添加图片
public class LostCardAdapter extends ArrayAdapter<LostCard> {

    private int resourceId;

    public LostCardAdapter(Context context, int textViewResourceId, List<LostCard> lostCards){
        super(context,textViewResourceId,lostCards);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LostCard lostCard = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        TextView lostCardTitle = (TextView) view.findViewById(R.id.lostcard_title);
        TextView lostCardName = (TextView) view.findViewById(R.id.lostcard_name);
        TextView lostCardType = (TextView) view.findViewById(R.id.lostcard_type);
        TextView lostCardSDate = (TextView) view.findViewById(R.id.lostcard_sDate);
        TextView lostCardEDate = (TextView) view.findViewById(R.id.lostcard_eDate);

        String sDate;
        String eDate;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sDate = sdf.format(lostCard.getSDate());
        eDate = sdf.format(lostCard.getEDate());


        lostCardTitle.setText(lostCard.getTitle());
        lostCardName.setText(lostCard.getName());
        lostCardType.setText(lostCard.getType());
        lostCardSDate.setText(sDate);
        lostCardEDate.setText(eDate);


        return view;
    }
}