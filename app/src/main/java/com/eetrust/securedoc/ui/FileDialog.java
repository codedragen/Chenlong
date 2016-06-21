package com.eetrust.securedoc.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.eetrust.securedoc.R;


/**
 * Created by long on 2016/6/10.
 */
public class FileDialog extends Dialog {
    String title;
    TextView tv_title;
    public FileDialog(Context context,String title) {
        super(context);
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_file);

            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setWindowAnimations(R.style.dialogAnim);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       tv_title= (TextView) findViewById(R.id.dialog_title);
        tv_title.setText(title);

       setCanceledOnTouchOutside(true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
