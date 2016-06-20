package com.eetrust.securedoc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eetrust.securedoc.R;

import java.util.List;


/**
 * Created by eetrust on 16/6/14.
 */
public class RecyclePersonAdapter extends RecyclerView.Adapter<RecyclePersonAdapter.MyViewHolder> {


    private final Context context;
    private final List<String> list;

    public RecyclePersonAdapter(List<String> list, Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shareuser_item,null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       holder.del.setVisibility(View.GONE);
        holder.lable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.del.getVisibility()==View.GONE)
                    holder.del.setVisibility(View.VISIBLE);
                else
                    holder.del.setVisibility(View.GONE);
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAdapterPosition());
              notifyItemRemoved(holder.getAdapterPosition());

               // notifyDataSetChanged();
            }
        });
        holder.lable.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
     public TextView lable;
       public  ImageView del;
        public MyViewHolder(View itemView) {
            super(itemView);
            lable= (TextView) itemView.findViewById(R.id.lable);
            del= (ImageView) itemView.findViewById(R.id.sharedel);

        }
    }
}
