package ua.internteam.dreamteamgame.QRCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.internteam.dreamteamgame.R;

public class BeforeQRPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_qr_player);

        Activity currentActivity = this;
        Button scanButton = findViewById(R.id.scanQR2);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(currentActivity, QRScannerActivity.class);
                startActivity(nextIntent);
            }
        });
    }
}
