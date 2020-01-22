package ua.internteam.dreamteamgame;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;

import java.io.IOException;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

public class MediaPlayerSource {
//source factory
    private DataSource.Factory rtmpDataSourceFactory;

//media source
    private MediaSource mediaSource;
    public MediaSource getMediaSource() {
        return mediaSource;
    }

    private MediaPlayerSource(){
        rtmpDataSourceFactory = new RtmpDataSourceFactory();
    }

    public MediaPlayerSource(String uriPath){
        this();
            mediaSource = new ProgressiveMediaSource.Factory(rtmpDataSourceFactory)
                                .createMediaSource(Uri.parse(uriPath));
    }
}
