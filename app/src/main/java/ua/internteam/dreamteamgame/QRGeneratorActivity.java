package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRGeneratorActivity extends AppCompatActivity {
    String serverUrl;
    String streamUrl;
    Team team;

    Boolean captainMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        getIntentInfo();
        generateQr(streamUrl);
    }

    private void generateQr(String streamLink){
        QRGEncoder qrgEncoder = new QRGEncoder(streamLink, null, QRGContents.Type.TEXT, 300);
        Bitmap bitmap;
        ImageView qrImage = findViewById(R.id.qrImage);
        try{
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }
    }

    private void getIntentInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serverUrl = bundle.getString("serverURL");
            bundle.remove("serverURL");
            streamUrl = bundle.getString("streamURL");
            bundle.remove("streamURL");
            team = (Team) bundle.get("team");
            bundle.remove("team");

            captainMode = bundle.getBoolean("captainMode");
            bundle.remove("captainMode");
        }
    }

    private void navigateToVideoPlayerActivity(){
        //TODO add navigation logic
    }
}
