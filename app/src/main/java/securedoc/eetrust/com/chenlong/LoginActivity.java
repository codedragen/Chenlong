package securedoc.eetrust.com.chenlong;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;
import securedoc.eetrust.com.chenlong.bean.UserInfo;
import securedoc.eetrust.com.chenlong.utils.ConnectUtil;
import securedoc.eetrust.com.chenlong.utils.HttpControler;
import securedoc.eetrust.com.chenlong.utils.XMLUtlis;

/**
 * Created by eetrust on 16/6/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private Button btn_login;
    private Toolbar toolbar;
    private ImageView iv_ispwdshow;
    SpannableString spannableString;
    HttpControler controler;
    EditText et_username,et_pwd;
    public String xml="<resultInfo>" +
            "<result>1</result>" +
            "<loginName>shen</loginName>" +
            "<userName>陈龙</userName>" +
            "<configdential >机密</configdential>" +
            "<email>598516810@qq.com</email>" +
            "<phone>15179839187</phone>" +
            "<error>0</error>" +
            "</resultInfo>";
  XMLUtlis utils=XMLUtlis.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.login);
        et_username= (EditText) findViewById(R.id.username);
        et_pwd= (EditText) findViewById(R.id.pwd);
        iv_ispwdshow= (ImageView) findViewById(R.id.iv_pwdshow);
        iv_ispwdshow.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        toolbar.inflateMenu(R.menu.login_menu);
        toolbar.setOnMenuItemClickListener(this);
        spannableString=new SpannableString("f}无网络链接");
        Drawable drawable=getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0,0,50,50);
        ImageSpan span=new ImageSpan(drawable);
        spannableString.setSpan(span,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
     controler=HttpControler.getInstance();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if (ConnectUtil.isConnenct(this)) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }else {
                    AlertDialog dialog=new AlertDialog.Builder(this).setTitle("警告").setMessage(spannableString).setPositiveButton("确定",null).setNegativeButton("取消",null).create();
                    dialog.show();
                }
                break;
            case R.id.iv_pwdshow:
                if (et_pwd.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_ispwdshow.setImageResource(R.mipmap.login_show_icon);
                    et_pwd.setSelection(et_pwd.getText().toString().length());
                }else {
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_ispwdshow.setImageResource(R.mipmap.login_hidden_icon);
                    et_pwd.setSelection(et_pwd.getText().toString().length());
                }
                break;
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
