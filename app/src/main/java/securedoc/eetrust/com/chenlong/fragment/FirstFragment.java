package securedoc.eetrust.com.chenlong.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.util.ArrayList;
import java.util.List;

import securedoc.eetrust.com.chenlong.R;
import securedoc.eetrust.com.chenlong.adapter.FirstFragAdapter;
import securedoc.eetrust.com.chenlong.bean.FileBean;

/**
 * Created by long on 2016/6/10.
 */
public class FirstFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    View root;
    private ListView lv;
    private SwipeRefreshLayout refresh;
    private Toolbar toolbar;
    private FirstFragAdapter adapter;

    private  List<FileBean> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=  inflater.inflate(R.layout.fragment_first,null);
        lv=(ListView)  root.findViewById(R.id.firstFra_lv);
         data=new ArrayList<FileBean>();
           for (int i=0;i<5;i++){
               FileBean fileBean=new FileBean();
               fileBean.name="文件夹"+i;
               fileBean.isFile=false;
               data.add(fileBean);

           }
        for (int j=0;j<15;j++){
            FileBean fileBean=new FileBean();
            fileBean.name="文件";
            fileBean.isFile=true;
            data.add(fileBean);
        }
        refresh=(SwipeRefreshLayout)root.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.GREEN);
     refresh.setOnRefreshListener(this);
       toolbar=(Toolbar) root.findViewById(R.id.firstfrag_toolbar);
        toolbar.inflateMenu(R.menu.firstfrag_menu);
        toolbar.setOnMenuItemClickListener(this);
         adapter=new FirstFragAdapter(getActivity(),data);
        lv.setAdapter(adapter);
       //View popuContent= inflater.inflate(R.layout.firstfrag_popu,null);
        //popupWindow=new PopupWindow(popuContent, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        return root;
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
              case R.id.add:

                  break;
              case R.id.select:
                  if (adapter.isCheckMode()) {
                      adapter.setCheckMode(false);
                      item.setIcon(R.mipmap.navfont);
                      for (FileBean bean:data)
                          bean.isChecked=false;
                  }else {
                      adapter.setCheckMode(true);
                      item.setIcon(R.mipmap.navfont_cancel);
                  }

                  break;
          }
        return true;
    }
}
