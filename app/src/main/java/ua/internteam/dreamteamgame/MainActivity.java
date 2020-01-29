package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ua.internteam.dreamteamgame.QRCode.QRGeneratorActivity;
import ua.internteam.dreamteamgame.QRCode.QRScannerActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.setIP);

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


