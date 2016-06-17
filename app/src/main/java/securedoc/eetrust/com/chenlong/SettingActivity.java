package securedoc.eetrust.com.chenlong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import securedoc.eetrust.com.chenlong.adapter.SetLvAdapter;

/**
 * Created by eetrust on 16/6/15.
 */
public class SettingActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private Toolbar toolbar;
    private ListView lv;
    private SetLvAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar=(Toolbar)findViewById(R.id.setactivity_toolbar);
        toolbar.inflateMenu(R.menu.setactivity_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv_set);
        List<String> ls=new ArrayList<>();
        for (int i=0;i<6;i++)
            ls.add("ddd");

        adapter=new SetLvAdapter(ls);
        lv.setAdapter(adapter);

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
