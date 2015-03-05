package com.example.priyanshu.mappr.Data;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by priyanshu-sekhar on 3/3/15.
 */
public class BadgesEarned {
    public Drawable getBadge() {
        return badge;
    }

    public void setBadge(Drawable badge) {
        this.badge = badge;
    }

    private Drawable badge;

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    private String badgeName;

    public String getBadgeAssigned() {
        return badgeAssigned;
    }

    public void setBadgeAssigned(String badgeAssigned) {
        this.badgeAssigned = badgeAssigned;
    }

    private String badgeAssigned;
    public int badgeCount;
    public boolean positiveBadge;
    public float badgePercentage;
}
