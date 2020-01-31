package ua.internteam.dreamteamgame.api.WebSockets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import ua.internteam.dreamteamgame.MediaPlayerActivity;


public class TimerWebSocket {
    private static OkHttpClient client;
    private static Request request;
    private static WebSocket webSocket;

    public void init(String webSocketUrl){
        client = new OkHttpClient();

        request = new Request.Builder()
                .url(webSocketUrl + "/timer")
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener(){
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                try {
                    MediaPlayerActivity.initializeTimeoutBar(Integer.parseInt(text));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
