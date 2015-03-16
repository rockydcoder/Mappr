package com.example.priyanshu.mappr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanshu.mappr.Adapters.BadgesAdapter;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 3/3/15.
 */
public class BadgesEarnedFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private BadgesAdapter badgesAdapter;
    private static ArrayList<Integer> badgeCount = new ArrayList<>();
    private static String[] badgeTitles;

    public BadgesEarnedFragment(ArrayList<Integer> list) {
        badgeCount = list;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_fragment_default, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.default_list);
        badgeTitles = getResources().getStringArray(R.array.badges_list);
        badgesAdapter = new BadgesAdapter(getActivity(), getData(layout));
        mRecyclerView.setAdapter(badgesAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return layout;
    }

    public static List<BadgesEarned> getData(View view) {
        List<BadgesEarned> data = new ArrayList<>();


        int[] badges={R.drawable.hardwork, R.drawable.creative, R.drawable.helping,
                R.drawable.participation, R.drawable.respect, R.drawable.motivated,
                R.drawable.disrespect, R.drawable.aggressive, R.drawable.talks,
                R.drawable.late, R.drawable.no_hw, R.drawable.untidy
        };

        boolean[] positiveBadge={
                true,true,true,true,true,true,
                false,false,false,false,false,false
        };

        for(int i = 0; i < badgeTitles.length; i++) {
            BadgesEarned badge = new BadgesEarned();
            badge.setBadge(view.getResources().getDrawable(badges[i]));
            badge.setBadgeName(badgeTitles[i]);
            badge.setBadgeAssigned("x" + badgeCount.get(i));
            badge.setBadgeCount(badgeCount.get(i));
            badge.setPositiveBadge(positiveBadge[i]);
            if (badge.getBadgeCount() != 0)
                data.add(badge);
        }

        return data;

    }

}
