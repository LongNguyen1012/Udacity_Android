package com.example.android.mysound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.mysound.MainActivity.mPlayer;
import static com.example.android.mysound.R.id.artist_name;
import static com.example.android.mysound.R.id.song_name;

public class CurrentPlay extends AppCompatActivity {

    String[] new_artist_list = new String[] {"Thái Trinh", "Lâm Chấn Huy", "Trịnh Đình Quang", "Trà My Idol",
                                             "Maroon 5", "BTS", "Far East Movement", "Hardwell ft. Jay Sean",
                                             "3LAU ft. Said the Sky", "The Veronicas"};

    String[] viet_artist_list = new String[] {"Đoàn Thúy Trang", "Lou Hoàng", "Only C", "Nguyên Jenda",
                                              "The Men", "Lâm Chấn Huy", "Vũ Cát Tường", "Trịnh Thăng Bình",
                                              "Phan Mạnh Quỳnh", "Noo Phước Thịnh"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        setContentView(R.layout.activity_current_play);

        Button buttonStop = (Button) findViewById(R.id.button_stop);
        TextView artist_name = (TextView)findViewById(R.id.artist_name);
        TextView song = (TextView)findViewById((song_name));
        TextView artist_header = (TextView)findViewById(R.id.artist_heading);
        TextView song_header = (TextView)findViewById(R.id.song_heading);

        Intent intent = this.getIntent();

        if (intent != null) {
            String song_name = intent.getExtras().getString("song_name");
            String data = intent.getExtras().getString("uniqueid");
            int song_id = intent.getExtras().getInt("song_id");

            if (data.equals("from_main") && song_name!=null) {
                artist_name.setText(new_artist_list[song_id]);
                song.setText(song_name);
                buttonStop.setVisibility(View.VISIBLE);

            } else if (data.equals("from_viet") && song_name!=null) {
                artist_name.setText(viet_artist_list[song_id]);
                song.setText(song_name);
                buttonStop.setVisibility(View.VISIBLE);

            } else if (mPlayer != null && mPlayer.isPlaying()){
                MyApplication application = (MyApplication) CurrentPlay.this.getApplication();
                String section = ((MyApplication) CurrentPlay.this.getApplication()).getSection();
                song_id = ((MyApplication) CurrentPlay.this.getApplication()).getSongId();
                song_name = ((MyApplication) CurrentPlay.this.getApplication()).getSong();

                if (section.equals("main")) {
                    artist_name.setText(new_artist_list[song_id]);
                    song.setText(song_name);
                    buttonStop.setVisibility(View.VISIBLE);
                } else {
                    artist_name.setText(viet_artist_list[song_id]);
                    song.setText(song_name);
                    buttonStop.setVisibility(View.VISIBLE);
                }

            } else {
                artist_header.setText("");
                song_header.setText("");
                artist_name.setText("No song is currently playing");
                song.setText("");
                buttonStop.setVisibility(View.GONE);
            }
        }

        // Find the View that shows the new releases navigation
        TextView new_release_textview = (TextView) findViewById(R.id.new_release);

        // Set a click listener on that View
        new_release_textview.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent
                Intent new_release_intent = new Intent(CurrentPlay.this, MainActivity.class);
                new_release_intent.putExtra("uniqueid","from_current");
                // Start the new activity
                startActivity(new_release_intent);
            }
        });

        TextView viet_pop_textview = (TextView) findViewById(R.id.viet_pop);

        viet_pop_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viet_pop_intent = new Intent(CurrentPlay.this, VietPopActivity.class);
                viet_pop_intent.putExtra("uniqueid","from_current");
                startActivity(viet_pop_intent);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    Intent new_release_intent = new Intent(CurrentPlay.this, MainActivity.class);
                    new_release_intent.putExtra("uniqueid","from_current");
                    // Start the new activity
                    startActivity(new_release_intent);
                }
            }
        });
    }
}
