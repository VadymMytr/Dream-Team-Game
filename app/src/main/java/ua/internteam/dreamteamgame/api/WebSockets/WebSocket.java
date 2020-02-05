package ua.internteam.dreamteamgame.api.WebSockets;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import ua.internteam.dreamteamgame.api.entity.Answer;

public class WebSocket {
    private static OkHttpClient client;
    private static String webSocketUrl;
    private static Request request;
    private static okhttp3.WebSocket webSocket;

    public WebSocket(String webSocketUrl) {
        this.client = new OkHttpClient();
        this.webSocketUrl = webSocketUrl;
    }

    public WebSocket(String webSocketUrl, String teamId) {
        this(webSocketUrl);
        createRequest(teamId);
        createWebSocket();
    }

    public WebSocket(String webSocketUrl, String teamId, String captainToken) {
        this(webSocketUrl);
        createRequest(teamId, captainToken);
        createWebSocket();
    }

    private void createRequest(String teamId) {
        this.request = new Request.Builder()
                .addHeader("TeamId", teamId)
                .url(this.webSocketUrl + "/socket")
                .build();
    }

    private void createRequest(String teamId, String captainToken) {
        this.request = new Request.Builder()
                .addHeader("TeamId", teamId)
                .addHeader("Token", captainToken)
                .url(this.webSocketUrl + "/socket")
                .build();
    }

    private void createWebSocket() {
        this.webSocket = client.newWebSocket(request, new WebSocketHandler());
    }

    public void sendAnswer(Answer answer) {
        webSocket.send(new Gson().toJson(answer));
    }
}
