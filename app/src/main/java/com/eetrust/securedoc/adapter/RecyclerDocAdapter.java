package com.eetrust.securedoc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eetrust.securedoc.R;

import java.util.List;



/**
 * Created by android on 2016/6/17.
 */
public class RecyclerDocAdapter extends RecyclerView.Adapter<RecyclerDocAdapter.MyViewHolder> {
    List<String> list;

    public  RecyclerDocAdapter(List<String> list){
         this.list=list;
     }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(parent.getContext(), R.layout.sharedoc_item,null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tv_filename.setText(list.get(position));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.iv_del.getVisibility()==View.GONE){
                    holder.iv_del.setVisibility(View.VISIBLE);
                }else {
                    holder.iv_del.setVisibility(View.GONE);
                }
            }
        });

        holder.iv_del.setVisibility(View.GONE);
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
     ImageView icon;
        TextView tv_filename;
        TextView tv_size;
        RelativeLayout relativeLayout;
        ImageView iv_del;
        public MyViewHolder(View itemView) {
            super(itemView);
           relativeLayout= (RelativeLayout) itemView.findViewById(R.id.rel);
            icon= (ImageView) itemView.findViewById(R.id.sharedoc_icon);
            tv_filename= (TextView) itemView.findViewById(R.id.sharedoc_filename);
            tv_size= (TextView) itemView.findViewById(R.id.sharedoc_size);
            iv_del= (ImageView) itemView.findViewById(R.id.deletesharedoc);
        }
    }
}
