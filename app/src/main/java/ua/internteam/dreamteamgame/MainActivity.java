package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ua.internteam.dreamteamgame.api.Api;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.Team;

public class MainActivity extends AppCompatActivity {
//
//    EditText editText;
//    Api api;
//    Team team;
//    Integer count;
//    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.setIP);
//
//        api = new Api("http://10.0.1.125:8080");
//        team = new Team(1);
//        count = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickM();
            }
        });

    }


    void onClickM() {
  Intent intent = new Intent(this, QRScannerActivity.class);
        startActivity(intent);
    }


}


