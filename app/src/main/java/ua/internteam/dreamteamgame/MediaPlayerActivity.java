package ua.internteam.dreamteamgame;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        playerView = findViewById(R.id.simple_player);

        setStyle();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            url = bundle.getString("streamURL");
            bundle.remove("streamURL");
        }
        initializePlayer();

        timer = new Timer();
        timer.schedule(new UpdateTimeTask(), 0, 1000);
    }

    private void setStyle(){
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        View overlay = findViewById(R.id.activity_media_player);
        View lay = findViewById(R.id.answerLay);

        overlay.setSystemUiVisibility(flags);
        lay.setSystemUiVisibility(flags);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initializePlayer(){
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        videoSource = new MediaPlayerSource(url).getMediaSource();
//Prepare the player with the source.
        player.prepare(videoSource);
//auto start playing
        player.setPlayWhenReady(true);
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
                    if(!player.isLoading())
                        player.retry();
                }
            });
        }
    }
}