package securedoc.eetrust.com.chenlong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import securedoc.eetrust.com.chenlong.R;
import securedoc.eetrust.com.chenlong.adapter.RecyclePersonAdapter;

/**
 * Created by eetrust on 16/6/14.
 */
public class ShareActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private RecyclerView recycle_sharedoc;
    private RecyclerView recycle_shareperson;
    private RecyclerView recycle_sharegroup;
    private Toolbar toolbar;
    String [] name={"嬴政","刘彻","曹操","李世民","朱元璋","成吉思汗","爱新觉罗·弘历","赵匡胤","努尔哈赤","多尔衮","武则天","康熙","顺治","孔子","老子","孟子","三皇五帝"};
     String [] group={"开发者","移动组","标密小组","海南电信小组","文档安全组"};

    private RecyclePersonAdapter adapter;
    private RecyclePersonAdapter groupadapter;

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

        toolbar= (Toolbar) findViewById(R.id.share_tool);
        toolbar.inflateMenu(R.menu.share_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }
}
