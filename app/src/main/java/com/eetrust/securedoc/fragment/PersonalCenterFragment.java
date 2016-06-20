package com.eetrust.securedoc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.eetrust.securedoc.activity.LoginActivity;
import com.eetrust.securedoc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by eetrust on 16/6/12.
 */
public class PersonalCenterFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View root;
    private ListView lv;
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private int []image={R.mipmap.personal_per_icon,
            R.mipmap.personal_version_icon,
            R.mipmap.personal_cache_icon,
            R.mipmap.personal_pwd_icon,
            R.mipmap.personal_set_icon,
            R.mipmap.personal_exit_icon};
    private  String[] title={"个人信息","版本信息","清除缓存","修改密码","设置","退出登录"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.personalcenter, null);


          lv= (ListView) root.findViewById(R.id.personinfo_lv);
           for (int i=0;i<image.length;i++){
               Map map=new HashMap<String,Object>();
               map.put("icon",image[i]);
               map.put("title",title[i]);
               map.put("arrow",R.mipmap.personal_arrow_icon);
               list.add(map);
           }
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),list,R.layout.personinfo_item,new String[]{"icon","title","arrow"},new int[]{R.id.personinfo_item_iv,R.id.personinfo_item_tv,R.id.personinfo_item_arrow});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        return  root;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          switch (position){
              case 5 :
                  startActivity(new Intent(getActivity(),LoginActivity.class));
                  getActivity().finish();
                  break;
          }
    }
}
