package com.our.flosing.bean;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.our.flosing.R;

import java.util.List;

/**
 * Created by RunNishino on 2017/1/1.
 */

public class FoundCardAdapter extends ArrayAdapter<FoundCard> {

    private int resourceId;

    public FoundCardAdapter(Context context, int textViewResourceId, List<FoundCard> foundCards){
        super(context,textViewResourceId,foundCards);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FoundCard foundCard = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        TextView foundCardTitle = (TextView) view.findViewById(R.id.foundcard_title);
        TextView foundCardName = (TextView) view.findViewById(R.id.foundcard_name);
        TextView foundCardType = (TextView) view.findViewById(R.id.foundcard_type);
        TextView foundCardSDate = (TextView) view.findViewById(R.id.foundcard_sDate);
        TextView foundCardEDate = (TextView) view.findViewById(R.id.foundcard_eDate);

        String sDate;
        String eDate;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sDate = sdf.format(foundCard.getSDate());
        eDate = sdf.format(foundCard.getEDate());


        foundCardTitle.setText(foundCard.getTitle());
        foundCardName.setText(foundCard.getName());
        foundCardType.setText(foundCard.getType());
        foundCardSDate.setText(sDate);
        foundCardEDate.setText(eDate);


        return view;
    }
}
