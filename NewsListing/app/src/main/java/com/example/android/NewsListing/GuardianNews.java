package com.example.android.NewsListing;

/**
 * Created by Long Nguyen on 1/7/2017.
 */

public class GuardianNews {
    private String mTitle;
    private String mSection;
    private String mUrl;
    private String mDate;

    public GuardianNews(String Title, String Section, String Date, String Url) {
        mTitle = Title;
        mSection = Section;
        mUrl = Url;
        mDate = Date;
    }

    public String getTitle () {
        return mTitle;
    }

    public String getSection () {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDate () {
        return mDate;
    }
}
