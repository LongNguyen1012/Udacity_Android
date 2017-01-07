/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the numbers category
        TextView park = (TextView) findViewById(R.id.park);

        // Set a click listener on that View
        park.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Park}
                Intent parkIntent = new Intent(MainActivity.this, Park.class);

                // Start the new activity
                startActivity(parkIntent);
            }
        });

        // Find the View that shows the nightclub category
        TextView nightclub = (TextView) findViewById(R.id.nightclub);

        // Set a click listener on that View
        nightclub.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NightClub}
                Intent nightclubIntent = new Intent(MainActivity.this, NightClub.class);

                // Start the new activity
                startActivity(nightclubIntent);
            }
        });

        // Find the View that shows the restaurant category
        TextView restaurant = (TextView) findViewById(R.id.restaurant);

        // Set a click listener on that View
        restaurant.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Restaurant}
                Intent restaurantIntent = new Intent(MainActivity.this, Restaurant.class);

                // Start the new activity
                startActivity(restaurantIntent);
            }
        });

        // Find the View that shows the casino category
        TextView casino = (TextView) findViewById(R.id.casino);

        // Set a click listener on that View
        casino.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Casino}
                Intent casinoIntent = new Intent(MainActivity.this, Casino.class);

                // Start the new activity
                startActivity(casinoIntent);
            }
        });
    }
}
