package com.example.android.mysound;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.android.mysound.MainActivity.mPlayer;

public class VietPopActivity extends AppCompatActivity {

    String[] viet_pop_name_list = new String[]{"Tình Yêu Màu Nắng", "Mình Là Gì Của Nhau", "Yêu Nhau Dài Lâu", "Làm Người Yêu Anh Nhé Baby",
            "Một Ai Đó Khác", "Làm Ơn Tránh Người Yêu Tôi Ra", "Góc Ban Công", "Em Đừng Quay Về Đây",
            "Hãy Ra Khỏi Người Đó Đi", "I Don’t Believe In You"};

    String[] viet_pop_link_list = new String[]
            {"http://aredir.nixcdn.com/0636139857bbd8c3d0e65cebd4b9b7bb/5803a0fc/Singer_Audio5/TinhYeuMauNang-DoanThuyTrangBigDaddy-2512120.mp3",
                    "http://f9.stream.nixcdn.com/b21f641348c2c58d27afdec2c0ad8083/5803a0fc/NhacCuaTui928/MinhLaGiCuaNhau-LouHoang-4592687.mp3",
                    "http://f9.stream.nixcdn.com/548a45f2ed874752f48d451b708c89dd/5803a0fc/NhacCuaTui928/YeuNhauDaiLau-OnlyCBaoThy-4608592.mp3",
                    "http://f9.stream.nixcdn.com/e048aa7c043c670b5c2775a634c27bf4/5803a0fc/NhacCuaTui927/LamNguoiYeuAnhNheBaby-NguyenJendaTuanDenManhMh-4581404.mp3",
                    "http://f9.stream.nixcdn.com/b5a3b5cdc4d4adf1e01e9082b537afa8/5803a0fc/NhacCuaTui927/MotAiDoKhac-TheMenNguyenHoangDuy-4581218.mp3",
                    "http://f9.stream.nixcdn.com/98d5b447ab066a176e41ecf9e2bd6d0c/5803a0fc/NhacCuaTui928/LamOnTranhNguoiYeuToiRa-LamChanHuy-4614503.mp3",
                    "http://f9.stream.nixcdn.com/ab3333a10a5792bb0a64a7c0bce3806b/5803a0fc/NhacCuaTui926/GocBanCong-VuCatTuong-4567007.mp3",
                    "http://f9.stream.nixcdn.com/32d06cfbf34ac63f5723513ca976fb07/5803a0fc/NhacCuaTui927/EmDungQuayVeDay-TrinhThangBinh-4582181.mp3",
                    "http://f9.stream.nixcdn.com/c1d982d2e72e76c558f71d3c0e300ee4/5803a0fc/NhacCuaTui927/HayRaKhoiNguoiDoDi-PhanManhQuynh-4571831.mp3",
                    "http://f9.stream.nixcdn.com/d911405c719e4cb45fca484d20754213/5803a0fc/NhacCuaTui928/IDontBelieveInYouAsiaSongFestival2016-NooPhuocThinhBasick-4611490.mp3"};

    class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        if (intent != null) {
            String data = intent.getExtras().getString("uniqueid");
            if (data.equals("from_main")) {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }

        setContentView(R.layout.activity_viet_pop);

        UpdateListView(viet_pop_name_list, viet_pop_link_list);

        // Find the View that shows the new releases navigation
        TextView new_release_textview = (TextView) findViewById(R.id.new_release);

        // Set a click listener on that View
        new_release_textview.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent
                Intent new_release_intent = new Intent(VietPopActivity.this, MainActivity.class);
                new_release_intent.putExtra("uniqueid","from_viet");
                // Start the new activity
                startActivity(new_release_intent);
            }
        });

        TextView current_play_textview = (TextView) findViewById(R.id.current_play);

        current_play_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent current_play_intent = new Intent(VietPopActivity.this, CurrentPlay.class);
                current_play_intent.putExtra("uniqueid","from_viet");
                startActivity(current_play_intent);
            }
        });
    }

    public void UpdateListView(String[] song_name, final String[] song_link) {

        final ListView listview = (ListView) findViewById(R.id.viet_pop_listview);

        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < song_name.length; ++i) {
            list.add(song_name[i]);
        }

        final VietPopActivity.StableArrayAdapter adapter = new VietPopActivity.StableArrayAdapter(VietPopActivity.this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                final String link = (String) song_link[position];

                Intent current_play_intent = new Intent(VietPopActivity.this, CurrentPlay.class);
                current_play_intent.putExtra("uniqueid","from_viet");
                current_play_intent.putExtra("song_name",item);
                current_play_intent.putExtra("song_id",position);

                MyApplication application = (MyApplication) VietPopActivity.this.getApplication();
                application.setSongId(position);
                application.setSection("viet");
                application.setSong(item);

                startActivity(current_play_intent);

                PlayMusic(link);
            }
        });
    }

    public void PlayMusic(String song_link) {

        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(song_link);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        mPlayer.start();
    }
}
