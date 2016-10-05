package com.example.android.funtrivia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void CheckAnswer(View view) {

        int Score = 0;

        RadioButton Tennessee = (RadioButton) findViewById(R.id.Tennessee);
        RadioButton Blood = (RadioButton) findViewById(R.id.Blood);
        RadioButton Six = (RadioButton) findViewById(R.id.Six);
        EditText Passionfruit = (EditText) findViewById(R.id.Passionfruit);
        EditText Coconut = (EditText) findViewById(R.id.Coconut);
        CheckBox HoChiMinh = (CheckBox) findViewById(R.id.HoChiMinh);
        CheckBox Hanoi = (CheckBox) findViewById(R.id.Hanoi);
        EditText WestSussex = (EditText) findViewById(R.id.WestSussex);
        CheckBox Apple = (CheckBox) findViewById(R.id.Apple);
        CheckBox Pineapple = (CheckBox) findViewById(R.id.Pineapple);

        boolean Tennessee_check = Tennessee.isChecked();
        boolean Blood_check = Blood.isChecked();
        boolean Six_check = Six.isChecked();
        boolean HoChiMinh_check = HoChiMinh.isChecked();
        boolean Hanoi_check = Hanoi.isChecked();
        boolean Apple_check = Apple.isChecked();
        boolean Pineapple_check = Pineapple.isChecked();
        String Passionfruit_text = Passionfruit.getText().toString();
        String Coconut_text = Coconut.getText().toString();
        String WestSussex_text = WestSussex.getText().toString();

        if (Tennessee_check) {
            Score += 1;
        }
        if (Blood_check) {
            Score += 1;
        }
        if (Six_check) {
            Score += 1;
        }
        if ("passion fruit".equals(Passionfruit_text.toLowerCase())) {
            Score += 1;
        }
        if ("coconut".equals(Coconut_text.toLowerCase())) {
            Score += 1;
        }
        if (HoChiMinh_check & Hanoi_check) {
            Score += 1;
        }
        if ("west sussex".equals(WestSussex_text.toLowerCase())) {
            Score += 1;
        }
        if (Apple_check & Pineapple_check) {
            Score += 1;
        }
        displayScore(view, Score);
        Log.d("Fun", "" + (Passionfruit_text.toLowerCase() == "passion fruit"));
    }

    private void displayScore(View view, int Score) {
        Context context = getApplicationContext();
        CharSequence text = "";
        if (Score == 8) {
            text = "Congratulation! You have answered all the questions correctly!";
        } else if (Score < 8) {
            text = "Sorry, you only scored " + Score;
        }
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
