package securedoc.eetrust.com.chenlong;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import securedoc.eetrust.com.chenlong.utils.ConnectUtil;

/**
 * Created by eetrust on 16/6/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private Button btn_login;
    private Toolbar toolbar;
    SpannableString spannableString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.login);
        btn_login.setOnClickListener(this);
        toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        toolbar.inflateMenu(R.menu.login_menu);
        toolbar.setOnMenuItemClickListener(this);
       spannableString=new SpannableString("f}无网络链接");
       // Drawable drawable=getResources().getDrawable(R.mipmap.ic_launcher);
       // drawable.setBounds(0,0,50,50);


       // ImageSpan span=new ImageSpan(drawable);
        ImageSpan span;
         span=new ImageSpan(this, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

        spannableString.setSpan(span,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


    }

    @Override
    public void onClick(View v) {
        if (ConnectUtil.isConnenct(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            AlertDialog dialog=new AlertDialog.Builder(this).setTitle("警告").setMessage(spannableString).setPositiveButton("确定",null).setNegativeButton("取消",null).create();
            dialog.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()==R.id.set){
            startActivity(new Intent(this,SettingActivity.class));

        }
        return true;
    }


}
