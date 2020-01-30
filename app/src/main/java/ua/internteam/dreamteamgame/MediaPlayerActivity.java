package ua.internteam.dreamteamgame;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
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
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.internteam.dreamteamgame.api.Api;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.Team;

public class MediaPlayerActivity extends AppCompatActivity {
    private Boolean isCaptainDevice;
    private String streamUrl;

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private MediaSource videoSource;
    private ImageView saver;

    private String serverUrl;
    private Api api;
    private Team team;
    private Answer answer;
    private EditText answerField;
    private static TimeoutBar timeoutBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        saver = findViewById(R.id.imageView);
        playerView = findViewById(R.id.simple_player);

        getIntentInfo();

        if (isCaptainDevice) {
            timeoutBar = new TimeoutBar(findViewById(R.id.timeoutBar));
            answerField = findViewById(R.id.answerET);
            api = new Api(serverUrl);
        }

        setStyle();
        initializePlayer();

        if (isCaptainDevice) {
            //TODO connect to websocket with timer
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Неможлиово вийти назад", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
// anticheat toast
        Toast toast = Toast.makeText(this, "Порушення правил гри веде до покарання.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView attentionImage = new ImageView(getApplicationContext());
        attentionImage.setImageResource(R.drawable.attention1);
        toastContainer.addView(attentionImage, 0);
        toast.show();

        //TODO send info about cheating to operator
    }

    private void setStyle() {
        ActivityStyleHandler handler = new ActivityStyleHandler(this,
                findViewById(R.id.activity_media_player));
        handler.setStyle();
    }

    private void getIntentInfo() {
        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            isCaptainDevice = bundle.getBoolean("isCaptainDevice");
//            bundle.remove("isCaptainDevice");
//            streamUrl = bundle.getString("streamURL");
//            bundle.remove("streamURL");
//
//            if (isCaptainDevice) {
//                serverUrl = bundle.getString("serverURL");
//                bundle.remove("serverURL");
//                team = (Team) bundle.get("team");
//                bundle.remove("team");
//            }
//        }
        isCaptainDevice = true;
        streamUrl = "rtmp://10.177.1.26/hls";
//        serverUrl = "ws://10.177.1.16:8080";
        serverUrl = "http://10.177.1.16:8080";
        team = new Team("afsa");
    }


    private void sendAnswer() {
        String answerText = answerField.getText().toString();
        Pattern answerPattern = Pattern.compile("[^a-zA-z0-9]");
        Matcher matcher = answerPattern.matcher(answerText);
        answerText = matcher.replaceAll("");
        answer = new Answer(team.getToken(), answerText);
        System.out.println(answerText);
        answerField.setText("");
        //TODO send answer
//        new SendAnswerTask().execute();
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        videoSource = new MediaPlayerSource(streamUrl).getMediaSource();
        player.setPlayWhenReady(true);
        player.prepare(videoSource);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                player.retry();
            }
        });
        player.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onIsPlayingChanged(EventTime eventTime, boolean isPlaying) {
                if (isPlaying)
                    saver.setVisibility(View.GONE);
            }
        });
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
                            }
                        });
//                        sendAnswer();
                    } else {
                        progressBar.setProgress(answerTimeout);
                        answerTimeout--;
                    }
                    System.out.println(answerTimeout);
                }
            }, 0, 20);
        }

        public TimeoutBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
            answerTimeout = progressBar.getMax();
        }
    }

    class SendAnswerTask extends AsyncTask<Void, Integer, Answer> {
        @Override
        protected Answer doInBackground(Void... unused) {
            try {
                return api.sendAnswer(answer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Answer answerResult) {
            if (answerResult != null)
                Toast.makeText(getApplicationContext(), "send: " + answerResult.getText(), Toast.LENGTH_SHORT)
                        .show();
        }
    }
}