package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = (Button) findViewById(R.id.setIP);
        final EditText editText = (EditText) findViewById(R.id.urlET);

//        editText.setText("rtmp://10.0.1.125/hls/stream");
        editText.setText("rtmp://stream1.livestreamingservices.com:1935/tvmlive/tvmlive");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickM(editText.getText().toString());
            }
        });

    }

    void onClickM(String url){
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("streamURL",url);
        startActivity(intent);

    }
}

