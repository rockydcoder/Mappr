package com.example.priyanshu.mappr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rakesh Sarangi on 14-Feb-15.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<SingleRowData> data = Collections.emptyList();
    private ViewOnClickListener viewOnClickListener;

    public CustomAdapter(Context context, List<SingleRowData> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        SingleRowData current = data.get(i);
        viewHolder.tvTitle.setText(current.getText());
        viewHolder.ivLogo.setImageResource(current.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener){
        this.viewOnClickListener=viewOnClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private ImageView ivLogo, ivArrow;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
        }

        @Override
        public void onClick(View view) {
            viewOnClickListener.onItemClick(view,getPosition());
        }
    }

    public interface ViewOnClickListener{
        public void onItemClick(View item,int position);
    }
}

