package ua.internteam.dreamteamgame;

import android.annotation.SuppressLint;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerActivity extends AppCompatActivity {
    private int currentApiVersion;
    private PlayerView playerView;
    private String url;
    private SimpleExoPlayer player;
    private MediaSource videoSource;
    private Timer timer;
    private ImageView saver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                currentApiVersion = android.os.Build.VERSION.SDK_INT;
        setViews();
        setStyle();
        setStreamUrl();

        initializePlayer();
        initializeRefreshTimer();

        preparePlayerToPlay();
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
    private void setStreamUrl(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            url = bundle.getString("streamURL");
            bundle.remove("streamURL");
        }

    }

    private void initializePlayer(){
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        videoSource = new MediaPlayerSource(url).getMediaSource();
        player.setPlayWhenReady(true);
    }
    private void initializeRefreshTimer(){
        timer = new Timer();
        timer.schedule(new UpdateTimeTask(), 0, 1000);
    }

    private void preparePlayerToPlay() { player.prepare(videoSource); }

    private void showAnticheatToast(){
        Toast toast = Toast.makeText(this, "Порушення правил гри веде до покарання.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView attentionImage = new ImageView(getApplicationContext());
        attentionImage .setImageResource(R.drawable.attention);
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

    private class UpdateTimeTask extends TimerTask{
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    player.clearVideoDecoderOutputBufferRenderer();

                    if(player.isPlaying())
                        saver.setVisibility(View.GONE);

                }
            });
        }
    }
}