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

    private Gson gson;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        //get stream url
//        String body = null;
//        StreamUrl url = null;
//        try {
//            body = response.body().string();
//            url = new Gson().fromJson(body, StreamUrl.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (url != null)
//            MediaPlayerActivity.streamUrl = url;
//        System.out.println(url.getUrl());
        gson = new Gson();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        parse(text);
    }

    private void parse(String text) {
        if(gson.fromJson(text, StreamUrl.class) != null){
            StreamUrl url = null;
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
                MediaPlayerActivity.initializeTimeoutBar(time.getSeconds());
        }
    }
}
