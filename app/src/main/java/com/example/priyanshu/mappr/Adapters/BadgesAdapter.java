package com.example.priyanshu.mappr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 3/3/15.
 */
public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    List<BadgesEarned> data = Collections.emptyList();
    private ViewOnClickListener viewOnClickListener;

    public BadgesAdapter(Context context, List<BadgesEarned> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.badges_row, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        BadgesEarned badge = data.get(i);
        viewHolder.badgeAssigned.setBackground(badge.getBadge());
        viewHolder.badgeAssigned.setText(badge.getBadgeAssigned());
        viewHolder.badgeName.setText(badge.getBadgeName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setViewOnClickListener(final ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;

    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView  badgeName;
        private TextView badgeAssigned;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            badgeName = (TextView) itemView.findViewById(R.id.badgeName);
            badgeAssigned = (TextView) itemView.findViewById(R.id.badgeAssigned);
        }

        @Override
        public void onClick(View view) {
            if(viewOnClickListener!=null)
                viewOnClickListener.onItemClick(view,getPosition());
        }
    }

    public interface ViewOnClickListener{
        public void onItemClick(View item,int position);

    }
}
