package com.example.priyanshu.mappr.Data;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by priyanshu-sekhar on 3/3/15.
 */
public class BadgesEarned {

    // DATA MEMBERS
    private String badgeAssigned;
    private int badgeCount;
    private boolean positiveBadge;
    private float badgePercentage;
    private Drawable badge;
    private String badgeName;

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public boolean isPositiveBadge() {
        return positiveBadge;
    }

    public void setPositiveBadge(boolean positiveBadge) {
        this.positiveBadge = positiveBadge;
    }

    public float getBadgePercentage() {
        return badgePercentage;
    }

    public void setBadgePercentage(float badgePercentage) {
        this.badgePercentage = badgePercentage;
    }



    public Drawable getBadge() {
        return badge;
    }

    public void setBadge(Drawable badge) {
        this.badge = badge;
    }



    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }



    public String getBadgeAssigned() {
        return badgeAssigned;
    }

    public void setBadgeAssigned(String badgeAssigned) {
        this.badgeAssigned = badgeAssigned;
    }


}
