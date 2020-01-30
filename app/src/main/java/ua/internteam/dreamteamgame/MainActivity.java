package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ua.internteam.dreamteamgame.QRCode.QRScannerActivity;
import ua.internteam.dreamteamgame.api.Api;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickM();
            }
        });
    }

    void onClickM() {
        //TODO navigate to rules activity
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        startActivity(intent);
    }

}


