package ua.internteam.dreamteamgame.QRCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import ua.internteam.dreamteamgame.R;

public class BeforeQRCaptainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_qr_captain);

        Activity currentActivity = this;

        Button scanButton = findViewById(R.id.scanQR);
        scanButton.setOnClickListener(v -> {
            Intent nextIntent = new Intent(currentActivity, QRScannerActivity.class);
            startActivity(nextIntent);
        });
    }
}
