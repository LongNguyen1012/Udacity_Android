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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NightClub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.nightclub_1_name,
                R.string.nightclub_1_address,
                R.string.nightclub_1_phone,
                R.string.nightclub_1_web,
                R.drawable.nightclub_1));

        locations.add(new Location(R.string.nightclub_2_name,
                R.string.nightclub_2_address,
                R.string.nightclub_2_phone,
                R.string.nightclub_2_web,
                R.drawable.nightclub_2));

        locations.add(new Location(R.string.nightclub_3_name,
                R.string.nightclub_3_address,
                R.string.nightclub_3_phone,
                R.string.nightclub_3_web,
                R.drawable.nightclub_3));

        locations.add(new Location(R.string.nightclub_4_name,
                R.string.nightclub_4_address,
                R.string.nightclub_4_phone,
                R.string.nightclub_4_web,
                R.drawable.nightclub_4));

        locations.add(new Location(R.string.nightclub_5_name,
                R.string.nightclub_5_address,
                R.string.nightclub_5_phone,
                R.string.nightclub_5_web,
                R.drawable.nightclub_5));

        locations.add(new Location(R.string.nightclub_6_name,
                R.string.nightclub_6_address,
                R.string.nightclub_6_phone,
                R.string.nightclub_6_web,
                R.drawable.nightclub_6));

        locations.add(new Location(R.string.nightclub_7_name,
                R.string.nightclub_7_address,
                R.string.nightclub_7_phone,
                R.string.nightclub_7_web,
                R.drawable.nightclub_7));

        locations.add(new Location(R.string.nightclub_8_name,
                R.string.nightclub_8_address,
                R.string.nightclub_8_phone,
                R.string.nightclub_8_web,
                R.drawable.nightclub_8));

        locations.add(new Location(R.string.nightclub_9_name,
                R.string.nightclub_9_address,
                R.string.nightclub_9_phone,
                R.string.nightclub_9_web,
                R.drawable.nightclub_9));

        locations.add(new Location(R.string.nightclub_10_name,
                R.string.nightclub_10_address,
                R.string.nightclub_10_phone,
                R.string.nightclub_10_web,
                R.drawable.nightclub_10));

        LocationAdapter itemsAdapter = new LocationAdapter(this, locations, R.color.category_nightclub, R.color.nightclub_link);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
