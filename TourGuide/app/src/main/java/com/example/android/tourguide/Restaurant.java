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

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.restaurant_1_name,
                R.string.restaurant_1_address,
                R.string.restaurant_1_phone,
                R.string.restaurant_1_web,
                R.drawable.restaurant_1));

        locations.add(new Location(R.string.restaurant_2_name,
                R.string.restaurant_2_address,
                R.string.restaurant_2_phone,
                R.string.restaurant_2_web,
                R.drawable.restaurant_2));

        locations.add(new Location(R.string.restaurant_3_name,
                R.string.restaurant_3_address,
                R.string.restaurant_3_phone,
                R.string.restaurant_3_web,
                R.drawable.restaurant_3));

        locations.add(new Location(R.string.restaurant_4_name,
                R.string.restaurant_4_address,
                R.string.restaurant_4_phone,
                R.string.restaurant_4_web,
                R.drawable.restaurant_4));

        locations.add(new Location(R.string.restaurant_5_name,
                R.string.restaurant_5_address,
                R.string.restaurant_5_phone,
                R.string.restaurant_5_web,
                R.drawable.restaurant_5));

        locations.add(new Location(R.string.restaurant_6_name,
                R.string.restaurant_6_address,
                R.string.restaurant_6_phone,
                R.string.restaurant_6_web,
                R.drawable.restaurant_6));

        locations.add(new Location(R.string.restaurant_7_name,
                R.string.restaurant_7_address,
                R.string.restaurant_7_phone,
                R.string.restaurant_7_web,
                R.drawable.restaurant_7));

        locations.add(new Location(R.string.restaurant_8_name,
                R.string.restaurant_8_address,
                R.string.restaurant_8_phone,
                R.string.restaurant_8_web,
                R.drawable.restaurant_8));

        locations.add(new Location(R.string.restaurant_9_name,
                R.string.restaurant_9_address,
                R.string.restaurant_9_phone,
                R.string.restaurant_9_web,
                R.drawable.restaurant_9));

        locations.add(new Location(R.string.restaurant_10_name,
                R.string.restaurant_10_address,
                R.string.restaurant_10_phone,
                R.string.restaurant_10_web,
                R.drawable.restaurant_10));

        LocationAdapter itemsAdapter = new LocationAdapter(this, locations, R.color.category_restaurant, R.color.restaurant_link);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
