package ua.internteam.dreamteamgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.setIP);
        EditText editText = (EditText) findViewById(R.id.urlET);


    }

    void onClickM(String url){
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("streamURL",url);
        startActivity(intent);

    }

}
