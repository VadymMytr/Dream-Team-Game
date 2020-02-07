package ua.internteam.dreamteamgame.api.WebSockets;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.AnticheatMesage;

public class Socket {
    private static OkHttpClient client;
    private static String webSocketUrl;
    private static Request request;
    private static WebSocket webSocket;

    public Socket(String webSocketUrl) {
        this.client = new OkHttpClient();
        this.webSocketUrl = webSocketUrl;
    }

    public Socket(String webSocketUrl, String teamId) {
        this(webSocketUrl);
        createRequest(teamId);
        createWebSocket();
    }

    public Socket(String webSocketUrl, String teamId, String captainToken) {
        this(webSocketUrl);
        createRequest(teamId, captainToken);
        createWebSocket();
    }

    public Socket(WebSocket webSocket){
        this.webSocket = webSocket;
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
        System.out.println(new Gson().toJson(answer));
    }

    public void sendCheatingMessage(AnticheatMesage anticheatMesage){
        webSocket.send(new Gson().toJson(anticheatMesage));
    }
}
