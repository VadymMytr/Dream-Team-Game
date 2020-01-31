package ua.internteam.dreamteamgame.QRCode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.IOException;

import ua.internteam.dreamteamgame.MediaPlayerActivity;
import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.Api;
import ua.internteam.dreamteamgame.api.entity.StreamUrl;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRScannerActivity extends AppCompatActivity {
    private Activity activity = this;
    private CodeScanner mCodeScanner;

    private Boolean isCaptainDevice;

    private Api api;
    private String serverURL;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        if (!cameraPermissionGranted())
            requestCameraPermission();

        createScanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraPermissionGranted())
            mCodeScanner.startPreview();
    }

    private Boolean cameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 50: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted
                    mCodeScanner.startPreview();
                } else {
                    //Permission was denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void createScanner() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String qrText = result.getText();
                        setIsCaptainDevice(qrText);
                        decodeQR(qrText);
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    private void setIsCaptainDevice(String qrText) {
        //check for captain mode
        if (qrText.contains("http")) //captain has server link
            isCaptainDevice = true;
        else if (qrText.contains("rtmp")) //player has stream link
            isCaptainDevice = false;
        else
            isCaptainDevice = null;
    }

    private void decodeQR(String qrText) {
        //captain device:
        if (isCaptainDevice) {
            //<server_url>|<team_token>
            String[] resultParts = qrText.split("\\|");
            serverURL = resultParts[0];
            team = new Team(resultParts[1], "");
            api = new Api(serverURL);
            new SendStreamUrlRequest().execute();
        }
        //player device:
        else
            //<stream_url>
            navigateToQRCodeShareActivity(new StreamUrl(qrText));
    }

    private void navigateToQRCodeShareActivity(StreamUrl streamURL) {
        Intent intent;
        intent = new Intent(activity, MediaPlayerActivity.class);
        intent.putExtra("streamURL", streamURL.getUrl());
        intent.putExtra("isCaptainDevice", isCaptainDevice);

        //captain also have to put serverURL and team info
        if (isCaptainDevice) {
            intent.putExtra("serverURL", serverURL);
            intent.putExtra("team", team);
        }

        startActivity(intent);
    }

    class SendStreamUrlRequest extends AsyncTask<Void, Integer, StreamUrl> {
        @Override
        protected StreamUrl doInBackground(Void... unused) {
            try {
                return api.getStreamUrl(team);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(StreamUrl streamUrl) {
            if (streamUrl != null)
                navigateToQRCodeShareActivity(streamUrl);
        }
    }
}