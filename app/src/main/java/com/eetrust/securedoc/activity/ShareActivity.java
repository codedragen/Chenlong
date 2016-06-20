package com.eetrust.securedoc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.eetrust.securedoc.R;
import com.eetrust.securedoc.adapter.RecyclePersonAdapter;
import com.eetrust.securedoc.adapter.RecyclerDocAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by eetrust on 16/6/14.
 */
public class ShareActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private RecyclerView recycle_sharedoc;
    private RecyclerView recycle_shareperson;
    private RecyclerView recycle_sharegroup;
    private Toolbar toolbar;
    String [] name={"嬴政","刘彻","曹操","李世民","朱元璋","成吉思汗","爱新觉罗·弘历","赵匡胤","努尔哈赤","多尔衮","武则天","康熙","顺治","孔子","老子","孟子","三皇五帝"};
     String [] group={"开发者","移动组","标密小组","海南电信小组","文档安全组"};
    WebView webView;
    private RecyclePersonAdapter adapter;
    private RecyclePersonAdapter groupadapter;
    private RecyclerDocAdapter docadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
       recycle_shareperson=(RecyclerView) findViewById(R.id.shareperson);
        recycle_sharegroup=(RecyclerView) findViewById(R.id.sharegroup);
        recycle_sharedoc= (RecyclerView) findViewById(R.id.sharedoc);
        List<String> list=new ArrayList<String>();

        list.addAll(Arrays.asList(name));
       adapter= new RecyclePersonAdapter(list,this);
        recycle_shareperson.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        recycle_shareperson.setItemAnimator(new DefaultItemAnimator());
        recycle_shareperson.setAdapter(adapter);
        List<String> listgroup=new ArrayList<String>();
        for (int i=0;i<10;i++){
            listgroup.add(group[(int) (Math.random()*group.length)]);
        }
        groupadapter=new RecyclePersonAdapter(listgroup,this);
        recycle_sharegroup.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false));
        recycle_sharegroup.setItemAnimator(new DefaultItemAnimator());
        recycle_sharegroup.setAdapter(groupadapter);
        List<String> listfile=new ArrayList<String>();
        for (int i=0;i<10;i++){
            listfile.add("文档"+i+".doc");
        }
        docadapter=new RecyclerDocAdapter(listfile);
        recycle_sharedoc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recycle_sharedoc.setItemAnimator(new DefaultItemAnimator());
        recycle_sharedoc.setAdapter(docadapter);
        toolbar= (Toolbar) findViewById(R.id.share_tool);
        toolbar.inflateMenu(R.menu.share_menu);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
