package ua.internteam.dreamteamgame.api.WebSockets;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.spec.ECField;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import ua.internteam.dreamteamgame.QRCode.QRScannerActivity;
import ua.internteam.dreamteamgame.activities.MediaPlayerActivity;
import ua.internteam.dreamteamgame.api.entity.StreamUrl;
import ua.internteam.dreamteamgame.api.entity.Time;

public class WebSocketHandler extends WebSocketListener {

    private static Gson gson = new Gson();
    private static final Integer TIMER_DELAY_IN_SECONDS = 0;
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        parse(text);
    }

    private void parse(String text) {
        StreamUrl url = gson.fromJson(text, StreamUrl.class);
        if(url.getUrl() != null){

            try {
                url = gson.fromJson(text, StreamUrl.class);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (url != null)
                MediaPlayerActivity.streamUrl = url;
        }
        else if(gson.fromJson(text, Time.class) != null)
        {
            Time time = null;
            try {
                time = gson.fromJson(text, Time.class);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (time != null)
                MediaPlayerActivity.initializeTimeoutBar(time.getSeconds() - TIMER_DELAY_IN_SECONDS);
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        try{
            MediaPlayerActivity.webSocket = new Socket(webSocket);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
