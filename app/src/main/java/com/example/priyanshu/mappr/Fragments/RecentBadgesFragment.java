package com.example.priyanshu.mappr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.priyanshu.mappr.Adapters.RecentBadgesAdapter;
import com.example.priyanshu.mappr.Data.Badge;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.Data.CommentRow;
import com.example.priyanshu.mappr.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 5/3/15.
 */
public class RecentBadgesFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecentBadgesAdapter recentBadgesAdapter;
    ArrayList<Badge> recentBadgesList = new ArrayList<>();

    public RecentBadgesFragment(ArrayList<Badge> recentBadges) {
        recentBadgesList = recentBadges;
        Collections.reverse(recentBadgesList);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.layout_recent_badges,container,false);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recent_badge_list);
        mRecyclerView.setPadding(20,20,20,40);
        recentBadgesAdapter = new RecentBadgesAdapter(getActivity(), getData(layout));
        mRecyclerView.setAdapter(recentBadgesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        return layout;

    }

    public ArrayList getData(View view){
        List<BadgesEarned> badgeInfo = BadgesEarnedFragment.getData(view);
        ArrayList<CommentRow> data = new ArrayList<>();
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        for(int i = 0; i < recentBadgesList.size(); i++) {
            CommentRow info = new CommentRow();
            String name;
            Badge badge = recentBadgesList.get(i);
            if(badge.isPositiveBadge())
                name = "<font color='green'>" + badge.getBadgeTitle() + "</font>";

            else
                name = "<font color='red'>" + badge.getBadgeTitle() + "</font>";

            info.userName = String.format("%s <font color=#9E9E9E> awarded by </font>" +
                    "<font color=#1674ab>" + badge.getTeacher() + "</font>", name);
            info.userComment = "<font color=#BBBBBB> " + ft.format(badge.getTimestamp()) + " </font>";
            data.add(info);
        }

        return data;
    }
}
