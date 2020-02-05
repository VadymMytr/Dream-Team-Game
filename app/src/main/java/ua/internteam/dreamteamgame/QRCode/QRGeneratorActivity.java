package ua.internteam.dreamteamgame.QRCode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ua.internteam.dreamteamgame.activities.MediaPlayerActivity;
import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRGeneratorActivity extends AppCompatActivity {
    Boolean isCaptainDevice;

    String webSocketUrl;
    String serverUrl;
    String token;
    Team team;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        getIntentInfo();
        generateQr(serverUrl + "/" + team.getId());
        //TODO add button and navigation to next activity on click
        Button streamBtn = findViewById(R.id.StreamBtn);
        streamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVideoPlayerActivity();
            }
        });
    }

    private void generateQr(String qrText) {
        QRGEncoder qrgEncoder = new QRGEncoder(qrText, null, QRGContents.Type.TEXT, 300);
        Bitmap bitmap;
        ImageView qrImage = findViewById(R.id.qrImage);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void getIntentInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isCaptainDevice = bundle.getBoolean("isCaptainDevice");
            bundle.remove("isCaptainDevice");
            team = (Team) bundle.get("team");
            bundle.remove("team");
            serverUrl = bundle.getString("serverURL");
            bundle.remove("serverURL");
            webSocketUrl = bundle.getString("websocketURL");
            bundle.remove("websocketURL");

            if (isCaptainDevice) {
                token = bundle.getString("token");
                bundle.remove("token");
            }
        }
    }

    private void navigateToVideoPlayerActivity() {
        Intent intent = new Intent(this, MediaPlayerActivity.class);
//        intent.putExtra("streamURL", streamUrl);
        intent.putExtra("team", team);
        intent.putExtra("serverURL", serverUrl);
        intent.putExtra("isCaptainDevice", isCaptainDevice);
        intent.putExtra("websocketURL", webSocketUrl);

        if (isCaptainDevice) {
            intent.putExtra("token", token);
        }

        startActivity(intent);
        finish();
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
