package ua.internteam.dreamteamgame.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.internteam.dreamteamgame.QRCode.BeforeQRCaptainActivity;
import ua.internteam.dreamteamgame.QRCode.BeforeQRPlayerActivity;
import ua.internteam.dreamteamgame.R;

public class RulesActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Context context = this;

        CheckBox checkBox = findViewById(R.id.checkBox);
        Button playerBtn = findViewById(R.id.playerButton);
        Button captainBtn = findViewById(R.id.captainButton);

        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    intent = new Intent(context, BeforeQRPlayerActivity.class);
                    startActivity(intent);
                } else
                    printAttentionToast();
            }
        });

        captainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    intent = new Intent(context, BeforeQRCaptainActivity.class);
                    startActivity(intent);
                } else
                    printAttentionToast();

            }
        });
    }

    private void printAttentionToast() {
        Toast toast = Toast.makeText(this, "Спочатку ознайомтесь із правилами користування додатком.", Toast.LENGTH_SHORT);
        toast.show();
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
