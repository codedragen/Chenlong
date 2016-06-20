package com.eetrust.securedoc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eetrust.securedoc.R;
import com.eetrust.securedoc.utils.ConnectUtil;
import com.eetrust.securedoc.utils.HttpControler;
import com.eetrust.securedoc.utils.XMLUtlis;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by eetrust on 16/6/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private Button btn_login;
    private Toolbar toolbar;
    private ImageView iv_ispwdshow;
    private HttpControler controler;
    private  EditText et_username,et_pwd;
    private LinearLayout rootview;
    private static final String HOST="http://10.3.43.249/securedoc";
    private static final String LOGIN_URL=HOST+"/clientInterface/clientLogin.do";

    private Observable<Map<String,Object>> observable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        rootview=(LinearLayout) findViewById(R.id.login_root);
        btn_login=(Button)findViewById(R.id.login);
        et_username= (EditText) findViewById(R.id.username);
        et_pwd= (EditText) findViewById(R.id.pwd);
        iv_ispwdshow= (ImageView) findViewById(R.id.iv_pwdshow);
        iv_ispwdshow.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        toolbar.inflateMenu(R.menu.login_menu);
        toolbar.setOnMenuItemClickListener(this);
        controler= HttpControler.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if (ConnectUtil.isConnenct(this)) {
                    if (et_pwd.getText().toString()==null||"".equals(et_pwd.getText().toString())||"".equals(et_username.getText().toString())||et_username.getText().toString()==null){
                        Snackbar.make(rootview,"用户名或密码不能为空",Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    RequestBody body=new FormBody.Builder().addEncoded("loginType","pwd").addEncoded("userName",et_username.getText().toString()).add("passWord",et_pwd.getText().toString()).add("version","3").build();
                    observable=controler.sendPostRequest(LOGIN_URL,body, XMLUtlis.LOGIN);
                    observable.subscribe(new Subscriber<Map<String, Object>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Map<String, Object> map) {
                           if ("1".equals(map.get("result"))){
                               startActivity(new Intent(LoginActivity.this, MainActivity.class));
                               finish();
                           }else{
                               Snackbar.make(rootview,(String)(map.get("error")),Snackbar.LENGTH_LONG).show();
                           }
                        }
                    });
                }else {
                    AlertDialog dialog=new AlertDialog.Builder(this).setTitle("警告").setMessage("无网络连接，将进入离线模式").setPositiveButton("确定",null).setNegativeButton("取消",null).create();
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
