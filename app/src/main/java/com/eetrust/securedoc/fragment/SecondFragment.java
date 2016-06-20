package com.eetrust.securedoc.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eetrust.securedoc.R;
import com.eetrust.securedoc.activity.ShareActivity;
import com.eetrust.securedoc.adapter.SecondFragAdapter;
import com.eetrust.securedoc.bean.FileBean;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by eetrust on 16/6/12.
 */
public class SecondFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private View root;
   private PopupWindow popupWindowTop;
    private View popuViewTop,popuViewBottom;

    private Toolbar toolbar;
    private TextView tv;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private SecondFragAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.fragment_second,null);
        toolbar= (Toolbar) root.findViewById(R.id.secondfrag_toolbar);
        toolbar.inflateMenu(R.menu.secondfrag_menu);
        toolbar.setOnMenuItemClickListener(this);
        tv=(TextView)root.findViewById(R.id.secureshare);
        refresh= (SwipeRefreshLayout) root.findViewById(R.id.refresh_second);
       refresh.setOnRefreshListener(this);
        lv=(ListView)root.findViewById(R.id.secondFra_lv);
        List<FileBean> data=new ArrayList<FileBean>();
        for (int i=0;i<5;i++){
            FileBean fileBean=new FileBean();
            fileBean.name="文件夹"+i;
            fileBean.isFile=false;
            fileBean.sender="发送人:陈龙";
            data.add(fileBean);

        }
        for (int j=0;j<15;j++){
            FileBean fileBean=new FileBean();
            fileBean.name="文件";
            fileBean.sender="发送人:陈龙";
            fileBean.isFile=true;
            data.add(fileBean);
        }
        adapter=new SecondFragAdapter(getActivity(),data);
        lv.setAdapter(adapter);
        popuViewTop=inflater.inflate(R.layout.popusecureshare,null);
       tv.setOnClickListener(this);

        popupWindowTop=new PopupWindow(popuViewTop, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowTop.setBackgroundDrawable(new BitmapDrawable());
        popupWindowTop.setOutsideTouchable(true);




        return  root;
    }

    @Override
    public void onClick(View v) {
             popupWindowTop.showAsDropDown(toolbar);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        popupWindowTop.dismiss();
        popupWindowTop=null;
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        },3000);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.second_share:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
        }
        return true;
    }
}
