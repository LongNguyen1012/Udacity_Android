package com.example.android.booklisting;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Long Nguyen on 1/7/2017.
 */

public class Book {
    private double mRating;
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mUrl;

    public Book(double Rating, String Title, String Author, String Date, String Url) {

        mAuthor = Author;
        mRating = Rating;
        mTitle = Title;
        mDate = Date;
        mUrl = Url;
    }

    public double getRating () {
        return mRating;
    }

    public String getTitle () {
        return mTitle;
    }

    public String getAuthor () {
        return mAuthor;
    }

    public String getDate () {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
