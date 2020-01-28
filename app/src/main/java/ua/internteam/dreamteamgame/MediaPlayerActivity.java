package ua.internteam.dreamteamgame;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;

import ua.internteam.dreamteamgame.api.Api;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.Team;

public class MediaPlayerActivity extends AppCompatActivity {
    private int currentApiVersion;
    private Api api;
    private Team team;

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private MediaSource videoSource;
    private ImageView saver;

    private String streamUrl;
    private String serverUrl;

    private EditText answerField;
    private String answer;
    private Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViews();
        setStyle();
        setIntents();
        setApi();

        initializePlayer();
        preparePlayerToPlay();

        setAnswerPart();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Неможлиово вийти назад", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showAnticheatToast();
        //TODO send info about cheating to operator
    }

    private void setStyle(){
        ActivityStyleHandler handler = new ActivityStyleHandler(this,
                findViewById(R.id.activity_media_player));
        handler.setStyle();
    }
    private void setViews(){
        setContentView(R.layout.activity_media_player);
        saver = findViewById(R.id.imageView);
        playerView = findViewById(R.id.simple_player);
    }

    private void setIntents(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            serverUrl = bundle.getString("serverURL");
            bundle.remove("serverURL");
            streamUrl = bundle.getString("streamURL");
            bundle.remove("streamURL");
            team = (Team) bundle.get("team");
            bundle.remove("team");
        }
    }

    private void setApi(){
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        api = new Api(serverUrl);
        count = 0;
    }

    private void setAnswerPart(){
        answerField = findViewById(R.id.answerET);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = answerField.getText().toString();
                answerField.setText("");
                new ProgressTask().execute();
            }
        });
    }

    private void initializePlayer(){
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        player.setVolume(0);
        videoSource = new MediaPlayerSource(streamUrl).getMediaSource();
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                player.retry();
            }
        });
        player.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onIsPlayingChanged(EventTime eventTime, boolean isPlaying) {
                if(isPlaying)
                    saver.setVisibility(View.GONE);

            }
        });
    }

    private void preparePlayerToPlay() { player.prepare(videoSource); }

    private void showAnticheatToast(){
        Toast toast = Toast.makeText(this, "Порушення правил гри веде до покарання.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView attentionImage = new ImageView(getApplicationContext());
        attentionImage .setImageResource(R.drawable.attention1);
        toastContainer.addView(attentionImage , 0);
        toast.show();
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
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