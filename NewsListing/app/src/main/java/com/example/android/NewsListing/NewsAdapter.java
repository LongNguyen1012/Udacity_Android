package com.example.android.NewsListing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by Long Nguyen on 1/7/2017.
 */

public class NewsAdapter extends ArrayAdapter<GuardianNews> {
    public NewsAdapter(Context context, List<GuardianNews> news) {
        super(context, 0, news);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(String Date) {
        return Date.substring(0,10);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        GuardianNews currentNews = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_number
        String title = currentNews.getTitle();
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title);
        titleTextView.setText(title);

        String section = currentNews.getSection();
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.news_section);
        sectionTextView.setText(section);

        // Find the TextView with view ID date
        String date = formatDate(currentNews.getDate());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Display the date of the current earthquake in that TextView
        dateView.setText(date);

        return listItemView;
    }
}
