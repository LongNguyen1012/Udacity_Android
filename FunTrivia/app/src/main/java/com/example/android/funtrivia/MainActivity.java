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

import static com.example.android.funtrivia.R.id.WestSussex;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void CheckAnswer(View view) {

        int score = 0;

        RadioButton Tennessee = (RadioButton) findViewById(R.id.Tennessee);
        RadioButton Blood = (RadioButton) findViewById(R.id.Blood);
        RadioButton Six = (RadioButton) findViewById(R.id.Six);
        EditText Passionfruit = (EditText) findViewById(R.id.Passionfruit);
        EditText Coconut = (EditText) findViewById(R.id.Coconut);
        CheckBox HoChiMinh = (CheckBox) findViewById(R.id.HoChiMinh);
        CheckBox Hanoi = (CheckBox) findViewById(R.id.Hanoi);
        CheckBox Tandung = (CheckBox) findViewById(R.id.Tandung);
        EditText WestSussex = (EditText) findViewById(R.id.WestSussex);
        CheckBox Apple = (CheckBox) findViewById(R.id.Apple);
        CheckBox Pineapple = (CheckBox) findViewById(R.id.Pineapple);
        CheckBox Banana = (CheckBox) findViewById(R.id.Banana);
        CheckBox Mangle = (CheckBox) findViewById(R.id.Mangle);


        boolean tennessee_check = Tennessee.isChecked();
        boolean blood_check = Blood.isChecked();
        boolean six_check = Six.isChecked();
        boolean hoChiMinh_check = HoChiMinh.isChecked();
        boolean hanoi_check = Hanoi.isChecked();
        boolean tan_dung_check = Tandung.isChecked();
        boolean apple_check = Apple.isChecked();
        boolean pineapple_check = Pineapple.isChecked();
        boolean banana_check = Banana.isChecked();
        boolean mangle_check = Mangle.isChecked();
        String passionfruit_text = Passionfruit.getText().toString();
        String coconut_text = Coconut.getText().toString();
        String westSussex_text = WestSussex.getText().toString();

        if (tennessee_check) {
            score += 1;
        }
        if (blood_check) {
            score += 1;
        }
        if (six_check) {
            score += 1;
        }
        if ("passion fruit".equals(passionfruit_text.toLowerCase())) {
            score += 1;
        }
        if ("coconut".equals(coconut_text.toLowerCase())) {
            score += 1;
        }
        if (hoChiMinh_check && hanoi_check && !tan_dung_check) {
            score += 1;
        }
        if ("west sussex".equals(westSussex_text.toLowerCase())) {
            score += 1;
        }
        if (apple_check && pineapple_check && !banana_check && !mangle_check) {
            score += 1;
        }
        displayScore(view, score);
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
