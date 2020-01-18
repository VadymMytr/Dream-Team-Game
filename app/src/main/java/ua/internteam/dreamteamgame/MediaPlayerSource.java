package ua.internteam.dreamteamgame;

import android.net.Uri;

import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

public class MediaPlayerSource {
//source factory
    private RtmpDataSourceFactory rtmpDataSourceFactory;

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
        mediaSource = new ExtractorMediaSource.Factory(rtmpDataSourceFactory)
                        .createMediaSource(Uri.parse(uriPath));
    }
}
