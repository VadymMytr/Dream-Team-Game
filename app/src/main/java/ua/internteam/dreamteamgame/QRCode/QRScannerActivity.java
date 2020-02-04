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

import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.WebSockets.WebSocket;
import ua.internteam.dreamteamgame.api.entity.StreamUrl;
import ua.internteam.dreamteamgame.api.entity.Team;

public class QRScannerActivity extends AppCompatActivity {
    private Activity activity = this;
    private CodeScanner mCodeScanner;

    private Boolean isCaptainDevice;

    private WebSocket webSocket;

    private StreamUrl streamUrl;
    private String serverURL;
    private String webSocketURL;
    private Team team;

    private String token;


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
                        //http://<server_url>/<team_id>/<captain_token>
                        String[] resultParts = result.getText().split("/");
                        setIsCaptainDevice(resultParts);
                        decodeQR(resultParts);
                        if (isCaptainDevice)
                            webSocket = new WebSocket(webSocketURL, team.getId(), token);
                        else
                            webSocket = new WebSocket(webSocketURL, team.getId());

                        navigateToQRCodeShareActivity();
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

    private void setIsCaptainDevice(String[] resultParts) {
        //check for captain mode
        if (resultParts.length == 5) //captain has token
            isCaptainDevice = true;
        else if (resultParts.length == 4) //player hasn't token
            isCaptainDevice = false;
        else
            isCaptainDevice = null;
    }

    private void decodeQR(String[] resultParts) {
        serverURL = resultParts[0] + "//" + resultParts[2];
        team = new Team(resultParts[3]);
        webSocketURL = serverURL.replace("http", "ws");
        //captain device:
        if (isCaptainDevice) {
            token = resultParts[4];
        }
    }

    private void navigateToQRCodeShareActivity() {
        Intent intent;
        intent = new Intent(activity, QRGeneratorActivity.class);
//        intent.putExtra("streamURL", streamUrl.getUrl());
        intent.putExtra("team", team);
        intent.putExtra("serverURL", serverURL);
        intent.putExtra("isCaptainDevice", isCaptainDevice);

        //captain also have to put serverURL and team info
        if (isCaptainDevice) {
            intent.putExtra("websocketURL", webSocketURL);
            intent.putExtra("token", token);
        }

        startActivity(intent);
        finish();
    }
}