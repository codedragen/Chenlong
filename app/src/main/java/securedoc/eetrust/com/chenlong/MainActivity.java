package securedoc.eetrust.com.chenlong;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioGroup;

import java.util.List;

import securedoc.eetrust.com.chenlong.fragment.FirstFragment;
import securedoc.eetrust.com.chenlong.fragment.MyDocFragment;
import securedoc.eetrust.com.chenlong.fragment.PersonalCenterFragment;
import securedoc.eetrust.com.chenlong.fragment.SecondFragment;





public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup tab_layout;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;

    private PersonalCenterFragment personalCenterFragment;
    private MyDocFragment mydocFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_home);
        tab_layout = (RadioGroup) findViewById(R.id.tab_layout);
        tab_layout.check(R.id.tab_first);
        if(firstFragment==null)
        firstFragment = new FirstFragment();
        if (secondFragment==null)
        secondFragment = new SecondFragment();
        if (personalCenterFragment==null)
        personalCenterFragment = new PersonalCenterFragment();
        if (mydocFragment==null)
        mydocFragment=new MyDocFragment();

       if (savedInstanceState!=null) {
           //getSupportFragmentManager().beginTransaction().add(R.id.container, firstFragment).add(R.id.container, secondFragment).add(R.id.container, personalCenterFragment).add(R.id.container, mydocFragment).hide(personalCenterFragment).show(firstFragment).hide(secondFragment).hide(mydocFragment).commit();
           //List<Fragment> list= getSupportFragmentManager().getFragments();
           showFragment(firstFragment);
       }else
           getSupportFragmentManager().beginTransaction().add(R.id.container, firstFragment).add(R.id.container, secondFragment).add(R.id.container, personalCenterFragment).add(R.id.container,mydocFragment).hide(personalCenterFragment).show(firstFragment).hide(secondFragment).hide(mydocFragment).commit();

        tab_layout.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tab_first:
                if (firstFragment.isHidden())
                    showFragment(firstFragment);
                break;
            case R.id.tab_second:
                if (secondFragment.isHidden())
                showFragment(secondFragment);
                break;
            case R.id.tab_fouth:
                if (personalCenterFragment.isHidden())
                showFragment(personalCenterFragment);
                break;
           case  R.id.tab_thrid:
               if (mydocFragment.isHidden())
                 showFragment(mydocFragment);
               break;

        }


    }


    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(firstFragment).hide(secondFragment).hide(personalCenterFragment).hide(mydocFragment).show(fragment).commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
    }
}