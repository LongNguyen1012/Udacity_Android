package com.example.android.mysound;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    static MediaPlayer mPlayer;
    Button buttonStop;

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

    String[] new_song_name_list = new String[]{"Dành Cho Anh", "Làm Ơn Tránh Người Yêu Tôi Ra", "Nhờ", "Vì Em Yêu Lắm", "Don't Wanna Know",
            "Blood Sweat & Tears", "Freal Luv", "Thinking About You", "Fire", "On Your Side"};

    String[] new_song_link_list = new String[]
            {"http://f9.stream.nixcdn.com/2edc551d3b93f13287cec3f047496e19/580399f4/NhacCuaTui927/DanhChoAnh-ThaiTrinh-4583329.mp3",
                    "http://f9.stream.nixcdn.com/38f6deba54658e91a283cc987cd93fc4/580399f4/NhacCuaTui928/LamOnTranhNguoiYeuToiRa-LamChanHuy-4614503.mp3",
                    "http://f9.stream.nixcdn.com/eacd648f24e22cedccce4c139acc33b4/580399f4/NhacCuaTui928/Nho-AnhQuanIdol-4606493.mp3",
                    "http://f9.stream.nixcdn.com/5680a754fcd73f8a4571f7f1fb773bf0/580399f4/NhacCuaTui928/ViEmYeuLam-TraMyIdol-4612611.mp3",
                    "http://f9.stream.nixcdn.com/5ce75578d6e1a54209af433d699c2cd0/580399f4/NhacCuaTui929/DontWannaKnow-Maroon5KendrickLamar-4615752.mp3",
                    "http://f9.stream.nixcdn.com/458416fb1690cbdf8c9bcacd4b2f6b20/580399f4/NhacCuaTui928/BloodSweatTears-BTSBangtanBoys-4611195.mp3",
                    "http://f9.stream.nixcdn.com/a9225b82479b65d98f11dafc296479ee/580399f4/NhacCuaTui929/FrealLuv-FarEastMovementMarshmelloChanYeolEXOKTinashe-4616668.mp3",
                    "http://f9.stream.nixcdn.com/24dbe9f766da5a348fb8fa055d876b1f/580399f4/NhacCuaTui929/ThinkingAboutYou-HardwellJaySean-4615829.mp3",
                    "http://f9.stream.nixcdn.com/4de755149ad7233035ac3edc27e9afa5/580399f4/NhacCuaTui929/Fire-3LAUSaidTheSky-4617349.mp3",
                    "http://f9.stream.nixcdn.com/8f9b08bc8dcf57f43e7a829700a90044/580399f4/NhacCuaTui929/OnYourSide-TheVeronicas-4617345.mp3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = this.getIntent();

        /* Obtain String from Intent  */

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setContentView(R.layout.activity_main);

        UpdateListView(new_song_name_list, new_song_link_list);

        TextView viet_pop_textview = (TextView) findViewById(R.id.viet_pop);

        viet_pop_textview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent viet_pop_intent = new Intent(MainActivity.this, VietPopActivity.class);
                viet_pop_intent.putExtra("uniqueid","from_main");
                startActivity(viet_pop_intent);
            }
        });

        TextView current_play_textview = (TextView) findViewById(R.id.current_play);

        current_play_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent current_play_intent = new Intent(MainActivity.this, CurrentPlay.class);
                current_play_intent.putExtra("uniqueid","from_main");
                startActivity(current_play_intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void UpdateListView(String[] song_name, final String[] song_link) {

        final ListView listview = (ListView) findViewById(R.id.new_song_listview);

        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < song_name.length; ++i) {
            list.add(song_name[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                final String link = (String) song_link[position];

                MyApplication application = (MyApplication) MainActivity.this.getApplication();
                application.setSongId(position);
                application.setSection("main");
                application.setSong(item);

                Intent current_play_intent = new Intent(MainActivity.this, CurrentPlay.class);
                current_play_intent.putExtra("uniqueid","from_main");
                current_play_intent.putExtra("song_name",item);
                current_play_intent.putExtra("song_id",position);

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