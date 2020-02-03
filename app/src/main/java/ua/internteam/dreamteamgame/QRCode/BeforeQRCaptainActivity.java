package ua.internteam.dreamteamgame.QRCode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ua.internteam.dreamteamgame.ActivityStyleHandler;
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


    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
