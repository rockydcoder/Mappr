package com.example.priyanshu.mappr.Data;

import java.util.Date;

/**
 * Created by rocky on 16/3/15.
 */
public class Badge {

    // DATA MEMBERS
    private String badgeTitle;
    private boolean positiveBadge;
    private Date timestamp;
    private String teacher;
    private int teacherId;
    private int badgeId;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getBadgeTitle() {
        return badgeTitle;
    }

    public void setBadgeTitle(String badgeTitle) {
        this.badgeTitle = badgeTitle;
    }

    public boolean isPositiveBadge() {
        return positiveBadge;
    }

    public void setPositiveBadge(boolean positiveBadge) {
        this.positiveBadge = positiveBadge;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
