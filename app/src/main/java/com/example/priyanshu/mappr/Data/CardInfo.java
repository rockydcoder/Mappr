package com.example.priyanshu.mappr.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by priyanshu-sekhar on 25/2/15.
 */
public class CardInfo implements Parcelable{
    public String title;
    public String subTitle;
    public String timeStamp;
    public long timeAdded;
    public long timeDisplayed;

    public CardInfo(Parcel in){
      title=in.readString();
      subTitle=in.readString();
      timeStamp=in.readString();
    }

    public CardInfo(String title,String subTitle,String timeStamp){
       this.title=title;
       this.subTitle=subTitle;
       this.timeStamp=timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() {
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };
}
