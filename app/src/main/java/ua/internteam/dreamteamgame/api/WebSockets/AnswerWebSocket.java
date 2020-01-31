package ua.internteam.dreamteamgame.api.WebSockets;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import ua.internteam.dreamteamgame.api.entity.Answer;

public class AnswerWebSocket {
    private static OkHttpClient client;
    private static String webSocketUrl;
    private static Request request;
    private static WebSocket webSocket;

    public AnswerWebSocket(String webSocketUrl) {
        this.client = new OkHttpClient();
        this.webSocketUrl = "ws://10.177.1.16:8080";

        this.request = new Request.Builder()
                .url(this.webSocketUrl + "/answer")
                .build();
        this.webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }
        });
    }


    public void sendAnswer(Answer answer){
        webSocket.send(new Gson().toJson(answer));
    }
}
