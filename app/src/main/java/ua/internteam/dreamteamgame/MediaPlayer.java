package ua.internteam.dreamteamgame;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class MediaPlayer {
//core parameters
    private BandwidthMeter bandwidthMeter;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private TrackSelector trackSelector;
//player
    private SimpleExoPlayer mediaPlayer;
    public SimpleExoPlayer getMediaPlayer() {
            return mediaPlayer;
    }

    private MediaPlayer(){
        //init defaults
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
    }

    public MediaPlayer(AppCompatActivity appCompatActivity){
        this();
        //init player
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(appCompatActivity, trackSelector);
    }
}
