package ua.internteam.dreamteamgame.api;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class TimerWebSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        //TODO add timer logic
        System.out.println(text);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("error: " + t.getMessage());
    }
}
