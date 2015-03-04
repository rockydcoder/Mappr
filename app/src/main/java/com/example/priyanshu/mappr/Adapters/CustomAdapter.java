package com.example.priyanshu.mappr.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.Data.SingleRowData;

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
        viewHolder.rlRow.setBackgroundColor(current.getRowBg());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setViewOnClickListener(final ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;

    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private ImageView ivLogo, ivArrow;
        private RelativeLayout rlRow;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            rlRow = (RelativeLayout) itemView.findViewById(R.id.rlRow);
        }

        @Override
        public void onClick(View view) {
            if(viewOnClickListener!=null)
               viewOnClickListener.onItemClick(view,getPosition());
        }
    }

    /**
     * For callback to any class to get the position of the item clicked
     */
    public interface ViewOnClickListener{
        public void onItemClick(View item,int position);

    }


}

