package com.example.priyanshu.mappr.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanshu.mappr.Adapters.BadgesAdapter;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 3/3/15.
 */
public class BadgesEarnedFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private BadgesAdapter badgesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_fragment_default, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.default_list);
        badgesAdapter = new BadgesAdapter(getActivity(), getData(layout));
        mRecyclerView.setAdapter(badgesAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return layout;
    }

    public static List<BadgesEarned> getData(View view) {
        List<BadgesEarned> data = new ArrayList<>();
        String[] titles = {"Aggressive", "Attentive", "Banned Items",
                "Creative", "Disrespect","Well Groomed",
                "Foul Language","Hardworking","Helping",
                "Late","Motivated","No Homework",
                "Participation","Respect","Talks",
                "Untidy"
        };

        int badgeCount=1;
        int[] badges={R.drawable.aggressive,R.drawable.attentive,R.drawable.banned_items,
                R.drawable.creative,R.drawable.disrespect,R.drawable.wellgroomed,
                R.drawable.foul_lang,R.drawable.hardwork,R.drawable.helping,
                R.drawable.late,R.drawable.motivated,R.drawable.no_hw,
                R.drawable.participation,R.drawable.respect,R.drawable.talks,
                R.drawable.untidy
        };
        for(int i = 0; i < titles.length; i++) {
            BadgesEarned badge = new BadgesEarned();
            badge.setBadge(view.getResources().getDrawable(badges[i]));
            badge.setBadgeName(titles[i]);
            badge.setBadgeAssigned("x"+badgeCount);
            data.add(badge);
        }

        return data;

    }

}
