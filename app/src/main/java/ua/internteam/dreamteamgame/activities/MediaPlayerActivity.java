package ua.internteam.dreamteamgame.activities;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.Timer;
import java.util.TimerTask;

import ua.internteam.dreamteamgame.ActivityStyleHandler;
import ua.internteam.dreamteamgame.Connection;
import ua.internteam.dreamteamgame.MediaPlayerSource;
import ua.internteam.dreamteamgame.R;
import ua.internteam.dreamteamgame.api.WebSockets.WebSocket;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.Team;

public class MediaPlayerActivity extends AppCompatActivity {
    private Boolean isCaptainDevice;

    private Team team;
    private String token;

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private MediaSource videoSource;
    private Boolean isPlayingStream;
    private ImageView waitingImage;

    private String streamUrl;

    public static Connection connection;

    private String serverUrl;
    private String webSocketUrl;
    private static TimeoutBar timeoutBar;
    private EditText answerField;
    private Answer answer;
    private WebSocket webSocket;
    private int counter;
    private final Activity context = this;
    private Anticheat anticheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        waitingImage = findViewById(R.id.imageView);
        playerView = findViewById(R.id.simple_player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        //TODO WARNING GOVNOCODE
        connection = new Connection() {
            @Override
            public void connect(String url) {
                streamUrl = url;
            }
        };


        isPlayingStream = false;
        anticheat = new Anticheat(context);
        counter = 1;

        getIntentInfo();

        if (isCaptainDevice) {
            timeoutBar = new TimeoutBar(findViewById(R.id.timeoutBar));
            answerField = findViewById(R.id.answerET);

            webSocket = new WebSocket(webSocketUrl, team.getId(), token);
        }
        else
            webSocket = new WebSocket(webSocketUrl, team.getId());


        setStyle();
        initializePlayer();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Неможливо вийти назад", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // anticheat toast
        anticheat.showAttentionMessage();
        //TODO send info about cheating to operator
    }

    private void setStyle() {
        ActivityStyleHandler handler = new ActivityStyleHandler(this,
                findViewById(R.id.activity_media_player));
        handler.setStyle();
    }

    private void getIntentInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isCaptainDevice = bundle.getBoolean("isCaptainDevice");
            bundle.remove("isCaptainDevice");
            serverUrl = bundle.getString("serverURL");
            bundle.remove("serverURL");
//            streamUrl = bundle.getString("streamURL");
//            bundle.remove("streamURL");
            team = (Team) bundle.get("team");
            bundle.remove("team");

            if (isCaptainDevice) {
                webSocketUrl = bundle.getString("websocketURL");
                bundle.remove("websocketURL");
                token = bundle.getString("token");
                bundle.remove("token");
            }
        }
    }

    private void sendAnswer() {
        String answerText = answerField.getText().toString();
        if(answerText.length() >= 100)
            answerText = answerText.substring(0, 100);

        answer = new Answer(counter++, answerText);
        System.out.println(answerText);
        answerField.setText("");
        webSocket.sendAnswer(answer);
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
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
                if (isPlaying) {
                    waitingImage.setVisibility(View.GONE);
                    isPlayingStream = true;
                }
                else if(isPlayingStream){
                    //TODO exit
                    anticheat.setWorking(false);
                    Intent intent = new Intent(context, ExitActivity.class);
                    startActivity(intent);
                    finish();
                }
                else player.retry();
            }
        });
        player.prepare(videoSource);
    }

    public static void initializeTimeoutBar(int time) {
        timeoutBar.initialize(time);
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void changeAnswerFieldVisibility(int visibility) {
        answerField.setVisibility(visibility);
    }

    class TimeoutBar {
        private Timer timer;
        private Integer answerTimeout;
        private ProgressBar progressBar;

        public void initialize(int timeInSecond) {
            //seconds * 50

            //Calls when answers must be send
            progressBar.setMax(timeInSecond * 50);
            answerTimeout = timeInSecond * 50;
            timer = new Timer();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    answerField.setEnabled(true);
                    progressBar.setVisibility(View.VISIBLE);
                    changeAnswerFieldVisibility(View.VISIBLE);
                }
            });

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (answerTimeout <= 0) {
                        timer.cancel();
                        //change visibility
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                answerField.setEnabled(false);
                                progressBar.setVisibility(View.GONE);
                                changeAnswerFieldVisibility(View.GONE);
                                sendAnswer();
                            }
                        });

                    } else {
                        progressBar.setProgress(answerTimeout);
                        answerTimeout--;
                    }
                }
            }, 0, 20);
        }

        public TimeoutBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
            answerTimeout = progressBar.getMax();
        }
    }
    class Anticheat{
        private Boolean working;
        private Activity activity;

        public Boolean getWorking() {
            return working;
        }

        public void setWorking(Boolean working) {
            this.working = working;
        }

        public Anticheat(Activity activity){
            working = true;
            this.activity = activity;
        }

        public void showAttentionMessage(){
            if(working) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(activity, "Порушення правил гри веде до покарання.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastContainer = (LinearLayout) toast.getView();
                        ImageView attentionImage = new ImageView(getApplicationContext());
                        attentionImage.setImageResource(R.drawable.attention1);
                        toastContainer.addView(attentionImage, 0);
                        toast.show();
                    }
                });
            }
        }
    }
}