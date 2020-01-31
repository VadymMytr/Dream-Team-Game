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
import ua.internteam.dreamteamgame.ActivityStyleHandler;
import ua.internteam.dreamteamgame.MediaPlayerActivity;
import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRGeneratorActivity extends AppCompatActivity {
    Boolean isCaptainDevice;
    String streamUrl;

    String webSocketUrl;
    String serverUrl;
    Team team;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        getIntentInfo();
        generateQr(streamUrl);
        //TODO add button and navigation to next activity on click
        Button streamBtn = findViewById(R.id.StreamBtn);
        streamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVideoPlayerActivity();
            }
        });
    }

    private void generateQr(String streamLink) {
        QRGEncoder qrgEncoder = new QRGEncoder(streamLink, null, QRGContents.Type.TEXT, 300);
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
            streamUrl = bundle.getString("streamURL");
            bundle.remove("streamURL");

            if (isCaptainDevice) {
                serverUrl = bundle.getString("serverURL");
                bundle.remove("serverURL");
                webSocketUrl = bundle.getString("websocketURL");
                bundle.remove("websocketURL");
                team = (Team) bundle.get("team");
                bundle.remove("team");
            }
        }
    }

    private void navigateToVideoPlayerActivity() {
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("streamURL", streamUrl);
        intent.putExtra("isCaptainDevice", isCaptainDevice);

        if (isCaptainDevice) {
            intent.putExtra("websocketURL", webSocketUrl);
            intent.putExtra("serverURL", serverUrl);
            intent.putExtra("team", team);
        }

        startActivity(intent);
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

    private void setStyle() {
        ActivityStyleHandler handler = new ActivityStyleHandler(this,
                findViewById(R.id.activity_media_player));
        handler.setStyle();
    }
}
