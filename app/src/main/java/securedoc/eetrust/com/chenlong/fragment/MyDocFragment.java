package securedoc.eetrust.com.chenlong.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import securedoc.eetrust.com.chenlong.R;
import securedoc.eetrust.com.chenlong.adapter.SecondFragAdapter;
import securedoc.eetrust.com.chenlong.bean.FileBean;

/**
 * Created by android on 2016/6/16.
 */
public class MyDocFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View root;
    private Toolbar toolbar;
    private SwipeRefreshLayout refresh;
    private SecondFragAdapter adapter;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          root=inflater.inflate(R.layout.fragment_mydoc,null);
        toolbar= (Toolbar) root.findViewById(R.id.mydocfrag_toolbar);
       // toolbar.inflateMenu(R.menu.secondfrag_menu);
       // toolbar.setOnMenuItemClickListener(this);

        refresh= (SwipeRefreshLayout) root.findViewById(R.id.refresh_mydoc);
        refresh.setOnRefreshListener(this);
        lv=(ListView)root.findViewById(R.id.mydocFra_lv);
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
       // popuViewTop=inflater.inflate(R.layout.popusecureshare,null);
        //tv.setOnClickListener(this);

//        popupWindowTop=new PopupWindow(popuViewTop, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindowTop.setBackgroundDrawable(new BitmapDrawable());
//        popupWindowTop.setOutsideTouchable(true);



        return  root;
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

}
