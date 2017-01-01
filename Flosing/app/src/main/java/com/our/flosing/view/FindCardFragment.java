package com.our.flosing.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.our.flosing.R;

/**
 * Created by RunNishino on 2017/1/1.
 */

public class FindCardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_find_list, container, false);

        return view;
    }
}
