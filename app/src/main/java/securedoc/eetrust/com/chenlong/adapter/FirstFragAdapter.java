package securedoc.eetrust.com.chenlong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.List;

import securedoc.eetrust.com.chenlong.R;
import securedoc.eetrust.com.chenlong.bean.FileBean;
import securedoc.eetrust.com.chenlong.ui.FileDialog;

/**
 * Created by long on 2016/6/10.
 */
public class FirstFragAdapter extends BaseAdapter {
    private final Context context;
    private final List<FileBean> data;
    private  boolean checkMode;
    PopupWindow popupWindow;
    View popuView;
    TextView tvdel;
    private AlertDialog dialog;

    public  FirstFragAdapter(final Context context, List<FileBean> data){
       this.context=context;
       this.data=data;
        popuView=LayoutInflater.from(context).inflate(R.layout.firstpopubottom,null);
      tvdel= (TextView) popuView.findViewById(R.id.delete);
        tvdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new AlertDialog.Builder(context).setTitle("警告!").setMessage("确认删除吗").setPositiveButton("确定",null).setNegativeButton("取消",null).setIcon(R.mipmap.ic_launcher).create();
                dialog.show();
            }
        });
        popupWindow= new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f5f5f5")));
    }

    public void setCheckMode(boolean checkMode) {
        this.checkMode = checkMode;
        notifyDataSetChanged();
    }
    public boolean isCheckMode(){
        return checkMode;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
          ViewHolder holder;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.firstfragment_lv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

          holder.tv_filename.setText(data.get(position).name);
        holder.iv_operate.setVisibility(View.VISIBLE);
        holder.cb_check.setVisibility(View.GONE);
        holder.tv_sender.setText("");
        if (data.get(position).isFile){
            holder.iv_operate.setVisibility(View.VISIBLE);
            holder.iv_icon.setImageResource(R.mipmap.com_pdf_icon);
        }else{
            holder.iv_operate.setVisibility(View.GONE);
            holder.iv_icon.setImageResource(R.mipmap.com_folder_icon);
        }

        if (checkMode&&data.get(position).isFile) {
            holder.cb_check.setVisibility(View.VISIBLE);
            holder.iv_operate.setVisibility(View.GONE);
        }
        holder.iv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDialog dialog=new FileDialog(context,data.get(position).name);

                dialog.show();
            }
        });
        holder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int count=0;
                data.get(position).isChecked=isChecked;
                for (FileBean bean:data) {

                    if(bean.isChecked)
                       count++;
                }
                if (count>0)
                    popupWindow.showAtLocation(parent,Gravity.BOTTOM,0,0);
                else
                    popupWindow.dismiss();


            }
        });
    holder.cb_check.setChecked(data.get(position).isChecked);

        return convertView;
    }

    class ViewHolder  {
        public TextView tv_filename;
        public ImageView iv_icon;
        public TextView tv_date;
        public TextView tv_time;
        public ImageView iv_operate;
        public CheckBox cb_check;
        public TextView tv_sender;
        ViewHolder(View itemView){
            tv_date= (TextView) itemView.findViewById(R.id.date);
            tv_filename= (TextView) itemView.findViewById(R.id.filename);
            iv_icon= (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_time= (TextView) itemView.findViewById(R.id.time);
            iv_operate= (ImageView) itemView.findViewById(R.id.operate);
            tv_sender= (TextView) itemView.findViewById(R.id.sender);
            cb_check= (CheckBox) itemView.findViewById(R.id.check);
        }



    }
}
