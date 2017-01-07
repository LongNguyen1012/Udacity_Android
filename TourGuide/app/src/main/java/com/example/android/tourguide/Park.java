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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;

public class Park extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.park_1_name,
                R.string.park_1_address,
                R.string.park_1_phone,
                R.drawable.park_1));

        locations.add(new Location(R.string.park_2_name,
                R.string.park_2_address,
                R.string.park_2_phone,
                R.drawable.park_2));

        locations.add(new Location(R.string.park_3_name,
                R.string.park_3_address,
                R.string.park_3_phone,
                R.drawable.park_3));

        locations.add(new Location(R.string.park_4_name,
                R.string.park_4_address,
                R.string.park_4_phone,
                R.drawable.park_4));

        locations.add(new Location(R.string.park_5_name,
                R.string.park_5_address,
                R.string.park_5_phone,
                R.drawable.park_5));

        locations.add(new Location(R.string.park_6_name,
                R.string.park_6_address,
                R.string.park_6_phone,
                R.drawable.park_6));

        locations.add(new Location(R.string.park_7_name,
                R.string.park_7_address,
                R.string.park_7_phone,
                R.drawable.park_7));

        locations.add(new Location(R.string.park_8_name,
                R.string.park_8_address,
                R.string.park_8_phone,
                R.drawable.park_8));

        locations.add(new Location(R.string.park_9_name,
                R.string.park_9_address,
                R.string.park_9_phone,
                R.drawable.park_9));

        locations.add(new Location(R.string.park_10_name,
                R.string.park_10_address,
                R.string.park_10_phone,
                R.drawable.park_10));

        LocationAdapter itemsAdapter = new LocationAdapter(this, locations, R.color.category_park, R.color.park_link);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}