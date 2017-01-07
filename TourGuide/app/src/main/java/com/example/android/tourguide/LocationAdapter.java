package com.example.android.tourguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Long Nguyen on 1/2/2017.
 */

public class LocationAdapter extends ArrayAdapter<Location> {

    private static final String LOG_TAG = LocationAdapter.class.getSimpleName();
    private int mColorResourceID;
    private int mActivityLinkColorId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param locations A List of Location objects to display in a list
     */
    public LocationAdapter(Activity context, ArrayList<Location> locations, int ColorResourceID, int ActivityLinkColorId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, locations);
        mColorResourceID = ColorResourceID;
        mActivityLinkColorId = ActivityLinkColorId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Location currentLocation = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentLocation.getName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        final TextView addressTextView = (TextView) listItemView.findViewById(R.id.address_text_view);
        // Get the address from the current Location object and
        // set this text on the address TextView
        addressTextView.setText(currentLocation.getAddress());

        // Get the phone number from the current Location object and
        // set this text on the phone TextView
        TextView phoneTextView = (TextView) listItemView.findViewById(R.id.phone_text_view);
        phoneTextView.setText(currentLocation.getPhoneNumber());

        int link_color = ContextCompat.getColor(getContext(), mActivityLinkColorId);
        phoneTextView.setLinkTextColor(link_color);

        TextView webTextView = (TextView) listItemView.findViewById(R.id.web_text_view);

        if (currentLocation.hasWeb()) {
            webTextView.setText(currentLocation.getWebsite());
            webTextView.setVisibility(View.VISIBLE);
            webTextView.setLinkTextColor(link_color);
        } else {
            webTextView.setVisibility(View.GONE);
        }

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current Location object and
        // set the image to iconView
        iconView.setImageResource(currentLocation.getImageResourceId(R.id.image));
        iconView.setVisibility(View.VISIBLE);

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);
        // Return the whole list item layout (containing 4 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}