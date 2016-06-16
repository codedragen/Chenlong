package securedoc.eetrust.com.chenlong.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import securedoc.eetrust.com.chenlong.LoginActivity;
import securedoc.eetrust.com.chenlong.MainActivity;
import securedoc.eetrust.com.chenlong.R;

/**
 * Created by eetrust on 16/6/12.
 */
public class PersonalCenterFragment extends Fragment implements View.OnClickListener {
    private Button pesonalInfo;
    private Button vesionInfo;
    private Button cleancaCache;
    private TextView logOut;
    private View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.personalcenter, null);
      logOut= (TextView) root.findViewById(R.id.exit);
        logOut.setOnClickListener(this);

        return  root;
    }

    @Override
    public void onClick(View v) {
        //Process.killProcess(Process.myPid());
        //System.exit(0);
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
