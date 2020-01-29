package ua.internteam.dreamteamgame.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ua.internteam.dreamteamgame.MediaPlayerActivity;
import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRGeneratorActivity extends AppCompatActivity {
    Boolean isCaptainDevice;
    String streamUrl;

    String serverUrl;
    Team team;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        getIntentInfo();
        generateQr(streamUrl);
        //TODO add button and navigation to next activity on click
//        navigateToVideoPlayerActivity();
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
            intent.putExtra("serverURL", serverUrl);
            intent.putExtra("team", team);
        }

        startActivity(intent);
    }
}
