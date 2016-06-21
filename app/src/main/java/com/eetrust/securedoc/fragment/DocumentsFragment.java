package com.eetrust.securedoc.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eetrust.securedoc.R;
import com.eetrust.securedoc.adapter.DocumentsAdapter;
import com.eetrust.securedoc.bean.FileBean;
import com.eetrust.securedoc.http.HttpControler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by long on 2016/6/10.
 */
public class DocumentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    View root;
    private ListView lv;
    private SwipeRefreshLayout refresh;
    private Toolbar toolbar;
    private DocumentsAdapter adapter;
    private static final String HOST="http://10.3.43.249/securedoc";
    private static final String DOCUMENTS=HOST+"/clientInterface/clientLogin.do?method=update_pwd";
    private List<FileBean> data;
   private HttpControler controler;
    private FormBody body;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_first, null);
        lv = (ListView) root.findViewById(R.id.firstFra_lv);
        data = new ArrayList<FileBean>();
        controler=HttpControler.getInstance(getActivity());
        //RequestBody body=new FormBody.Builder().addEncoded("startTime",0+"").add("loginName","shen").add("type","2").build();
        body=new FormBody.Builder().add("oldPwd","abcd1234").add("newPwd","1234567").build();
        Observable observable= controler.test(DOCUMENTS,body);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("dfsfsd",throwable.toString());
            }
        });
        for (int i = 0; i < 5; i++) {
            FileBean fileBean = new FileBean();
            fileBean.name = "文件夹" + i;
            fileBean.isFile = false;
            data.add(fileBean);

        }
        for (int j = 0; j < 15; j++) {
            FileBean fileBean = new FileBean();
            fileBean.name = "文件";
            fileBean.isFile = true;
            data.add(fileBean);
        }
        refresh = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refresh.setOnRefreshListener(this);
        toolbar = (Toolbar) root.findViewById(R.id.firstfrag_toolbar);
        toolbar.inflateMenu(R.menu.firstfrag_menu);
        toolbar.setOnMenuItemClickListener(this);
        adapter = new DocumentsAdapter(getActivity(), data);
        lv.setAdapter(adapter);
        return root;
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:

                break;
            case R.id.select:
                if (adapter.isCheckMode()) {
                    adapter.setCheckMode(false);
                    item.setIcon(R.mipmap.navfont);
                    for (FileBean bean : data)
                        bean.isChecked = false;
                } else {
                    adapter.setCheckMode(true);
                    item.setIcon(R.mipmap.navfont_cancel);
                }

                break;
        }
        return true;
    }
}
