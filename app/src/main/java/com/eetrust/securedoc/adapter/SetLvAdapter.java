package com.eetrust.securedoc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eetrust.securedoc.R;

import java.util.List;



/**
 * Created by android on 2016/6/17.
 */
public class SetLvAdapter extends BaseAdapter {
    private final List<String> list;

    public SetLvAdapter(List<String> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView==null){
          convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.setactivity_lv_item,null);

       }

        return convertView;
    }
}
