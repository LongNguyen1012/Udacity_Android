package com.example.android.booklisting;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by Long Nguyen on 1/7/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Return the formatted rating string showing 1 decimal place (i.e. "3.2")
     * from a decimal rating value.
     */
    private String formatRating(double rating) {
        if (rating != 0.0) {
            DecimalFormat ratingFormat = new DecimalFormat("0.0");
            return ratingFormat.format(rating);
        } else {
            return "NR";
        }
    }

    private StringBuilder buildStringAuthor (ArrayList<String> author) {
        StringBuilder authorString = new StringBuilder();
        for (int i = 0; i < author.size(); i++) {
            authorString.append(author.get(i));
            if (i != (author.size() - 1)) {
                authorString.append(", ");
            }
        }
        return authorString;
    }

    private int getRatingColor(double rating) {
        int ratingColorResourceId;
        int ratingFloor = (int) Math.floor(rating);
        switch (ratingFloor) {
            case 0:
            case 1:
                ratingColorResourceId = R.color.rating1;
                break;
            case 2:
                ratingColorResourceId = R.color.rating2;
                break;
            case 3:
                ratingColorResourceId = R.color.rating3;
                break;
            case 4:
                ratingColorResourceId = R.color.rating4;
                break;
            case 5:
                ratingColorResourceId = R.color.rating5;
                break;
            case 6:
                ratingColorResourceId = R.color.rating6;
                break;
            case 7:
                ratingColorResourceId = R.color.rating7;
                break;
            case 8:
                ratingColorResourceId = R.color.rating8;
                break;
            case 9:
                ratingColorResourceId = R.color.rating9;
                break;
            default:
                ratingColorResourceId = R.color.rating10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), ratingColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Book currentBook = getItem(position);

        TextView ratingTextView = (TextView) listItemView.findViewById(R.id.rating);
        // Get the rating from the current book object and
        // set this text on the rating TextView
        String formattedrating = formatRating(currentBook.getRating());
        ratingTextView.setText(formattedrating);

        // Set the proper background color on the rating circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable ratingCircle = (GradientDrawable) ratingTextView.getBackground();

        // Get the appropriate background color based on the current book rating
        int ratingColor = getRatingColor(currentBook.getRating());

        // Set the color on the rating circle
        ratingCircle.setColor(ratingColor);

        // Find the TextView in the list_item.xml layout with the ID version_number
        String title = currentBook.getTitle();
        String author = currentBook.getAuthor();

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        titleTextView.setText(title);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
        authorTextView.setText(author);

        String date = currentBook.getDate();

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Display the date of the current book in that TextView
        dateView.setText(date);

        return listItemView;
    }
}
