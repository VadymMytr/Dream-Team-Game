package ua.internteam.dreamteamgame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ua.internteam.dreamteamgame.api.Api;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.Team;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Api api;
    Team team;
    Integer count;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.urlET);
        Button btn = (Button) findViewById(R.id.setIP);

        api = new Api("http://10.0.1.125:8080");
        team = new Team(1);
        count = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickM();
            }
        });

    }

    void onClickM() {
        answer = editText.getText().toString();
        new ProgressTask().execute();

    }

    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        Answer answera;
        @Override
        protected Void doInBackground(Void... unused) {
            try {
                answera = api.sendAnswer( new Answer( count++,team, answer) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (null);
        }

        @Override
        protected void onProgressUpdate(Integer... items) {

        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(getApplicationContext(), "send: "+answera.getText(), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}


