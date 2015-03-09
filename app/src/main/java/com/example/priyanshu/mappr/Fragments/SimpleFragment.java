package com.example.priyanshu.mappr.Fragments;

/**
 * Created by priyanshu-sekhar on 4/3/15.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleFragment extends Fragment {
    ArrayList<String> xVals;
    ArrayList<Entry>  entries1;
    PieDataSet ds1;
    int count,totalBadges;
    boolean noBadge=true;
    private Typeface tf;

    public SimpleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected PieData generatePieData(List<BadgesEarned> badgesEarned) {

        count = 0;
        int totBadges=0,maxBadgeCount=0;
        ArrayList<Integer> colors=new ArrayList<>();
        entries1 = new ArrayList<>();
        xVals = new ArrayList<>();
        totalBadges=0;
        for(BadgesEarned badgeEarned:badgesEarned) {
            totalBadges+=badgeEarned.badgeCount;
            if(badgeEarned.positiveBadge)
                totBadges+=badgeEarned.badgeCount;
            else
                totBadges-=badgeEarned.badgeCount;
            if(badgeEarned.badgeCount>maxBadgeCount)
                maxBadgeCount=badgeEarned.badgeCount;
        }
        for(BadgesEarned badgeEarned:badgesEarned){
            if(badgeEarned.badgeCount!=0) {
                float percentage = (float) (badgeEarned.badgeCount * 100) / totalBadges;
                xVals.add(badgeEarned.getBadgeName() + "-" + percentage + "%");
                entries1.add(new Entry(percentage, count++));
                if (badgeEarned.positiveBadge)
                    colors.add(Color.rgb(badgeEarned.badgeCount * 255 / maxBadgeCount, 0, 0));
                else
                    colors.add(Color.rgb(0, badgeEarned.badgeCount * 255 / maxBadgeCount, 0));
            }
        }


        PerfAnalFragment.positivePercentage=totBadges*100/totalBadges;
        ds1 = new PieDataSet(entries1, "Performance Analysis");
        ds1.setValueFormatter(new PercentFormatter());
        ds1.setSliceSpace(2f);
        ds1.setColors(colors);

        PieData d = new PieData(xVals, ds1);
        d.setDrawValues(false);
        d.setValueTextSize(15f);
        d.setValueTextColor(Color.BLACK);
        d.setValueFormatter(new PercentFormatter());
        d.setValueTypeface(tf);
        return d;
    }


       private String[] mLabels = new String[]{"Company A", "Company B", "Company C", "Company D", "Company E", "Company F"};
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

    private String getLabel(int i) {
        return mLabels[i];
    }
}

