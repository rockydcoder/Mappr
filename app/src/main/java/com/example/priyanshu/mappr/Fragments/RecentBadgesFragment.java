package com.example.priyanshu.mappr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanshu.mappr.Adapters.CommentAdapter;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.Data.CommentRow;
import com.example.priyanshu.mappr.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 5/3/15.
 */
public class RecentBadgesFragment extends Fragment {
    RecyclerView mRecyclerView;
    CommentAdapter commentAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.layout_recent_badges,container,false);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recent_badge_list);
        mRecyclerView.setPadding(20,20,20,40);
        commentAdapter = new CommentAdapter(getActivity(), getData(layout));
        mRecyclerView.setAdapter(commentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        return layout;

    }

    public ArrayList getData(View view){
        List<BadgesEarned> badgeInfo=BadgesEarnedFragment.getData(view);
        ArrayList<CommentRow> data=new ArrayList<>();
        for(int i=0;i<badgeInfo.size();i++){
            CommentRow info=new CommentRow();
            String name;
            if(badgeInfo.get(i).positiveBadge)
                name="<font color='green'>"+badgeInfo.get(i).getBadgeName()+"</font>";
            else
                name="<font color='red'>"+badgeInfo.get(i).getBadgeName()+"</font>";

            info.userName= String.format("%s <font color=#9E9E9E> awarded by </font>" +
                    "<font color=#1674ab>Mr.Soham Chokshi</font>", name);
            info.userComment="<font color=#BBBBBB> 5th April 2015, 10:30pm</font>";
            data.add(info);
        }

        return data;
    }
}
