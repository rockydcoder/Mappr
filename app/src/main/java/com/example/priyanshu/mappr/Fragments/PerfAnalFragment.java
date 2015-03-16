package com.example.priyanshu.mappr.Fragments;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanshu.mappr.Adapters.BadgeChartAdapter;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import java.util.List;


public class PerfAnalFragment extends SimpleFragment {

    public static Fragment newInstance() {
        return new PerfAnalFragment();
    }

    private PieChart mChart;
    static int positivePercentage;
    private RecyclerView mRecyclerView;
    private BadgeChartAdapter badgeChartAdapter;
    List<BadgesEarned> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_report_card, container, false);


        mChart = (PieChart) v.findViewById(R.id.pieChart);
        mChart.setDescription("");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf"));
        mChart.setCenterTextSize(28f);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);


        // enable / disable drawing of x- and y-values
//        mChart.setDrawYValues(false);
//        mChart.setDrawXValues(false);
        data=BadgesEarnedFragment.getData(v);
        mChart.setData(generatePieData(data));
        if(positivePercentage>=0) {
            mChart.setCenterText(positivePercentage + "%\nPositive");
            mChart.setCenterTextColor(Color.BLACK);
        }
        else {
            mChart.setCenterText((100+positivePercentage) + "%\nPositive");
            mChart.setCenterTextColor(Color.BLACK);
        }
        Legend l = mChart.getLegend();
        l.setEnabled(false);
       /* l.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf"));
        l.setTextColor(Color.BLACK);
        l.setTextSize(15f);
        l.setFormToTextSpace(10f);
       */
        int totalBadges=0;
        for(BadgesEarned badgeEarned:data)
            totalBadges+=badgeEarned.getBadgeCount();
        for(int i=0;i<data.size();i++)
            data.get(i).setBadgePercentage((int)data.get(i).getBadgeCount()*100/totalBadges);

        badgeChartAdapter = new BadgeChartAdapter(getActivity(),data);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.badge_list);
        mRecyclerView.setPadding(0,0,0,0);
        badgeChartAdapter = new BadgeChartAdapter(getActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(badgeChartAdapter);

        return v;
    }


}

