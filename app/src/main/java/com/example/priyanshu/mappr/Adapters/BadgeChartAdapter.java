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
 * Created by priyanshu-sekhar on 4/3/15.
 */
public class BadgeChartAdapter extends RecyclerView.Adapter<BadgeChartAdapter.MyBadgeHolder>{
    private LayoutInflater inflater;
    List<BadgesEarned> data = Collections.emptyList();

    public BadgeChartAdapter(Context context, List<BadgesEarned> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

   
    @Override
    public void onBindViewHolder(MyBadgeHolder viewHolder, int i) {
        BadgesEarned badge = data.get(i);
        viewHolder.badgeName.setText(badge.getBadgeName());
        viewHolder.badgePercentage.setText(badge.badgePercentage+"%");
    }

    @Override
    public MyBadgeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.badge_table_row, parent, false);
        MyBadgeHolder myBadgeHolder = new MyBadgeHolder(view);
        return myBadgeHolder;
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyBadgeHolder extends RecyclerView.ViewHolder {
        private TextView badgeName;
        private TextView badgePercentage;

        public MyBadgeHolder(View itemView) {
            super(itemView);
            badgeName = (TextView) itemView.findViewById(R.id.badgeTitle);
            badgePercentage = (TextView) itemView.findViewById(R.id.badgePercentage);
        }


    }


}
