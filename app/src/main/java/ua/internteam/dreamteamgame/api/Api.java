package ua.internteam.dreamteamgame.api;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.StreamUrl;
import ua.internteam.dreamteamgame.api.entity.Team;

public class Api {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private String serverUrl;
    private Gson gson;
//
//    private final TimerWebSocketListener listener;
//    private final WebSocket webSocket;

    public Api(String serverUrl) {
        this.client = new OkHttpClient();
        this.serverUrl = serverUrl;
        //TODO DELETE NAFIG
//

        this.gson = new Gson();
    }

    public String getUrl() {
        return serverUrl;
    }

    public Answer sendAnswer(Answer answer) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(answer));
        Request request = new Request.Builder()
                .url(serverUrl + "/answer")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(), Answer.class);
    }

    public StreamUrl getStreamUrl(Team team) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(team));
        Request request = new Request.Builder()
                .url(serverUrl + "/geturl")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String streamUrl = response.body().string();
        System.out.println(streamUrl);
        return gson.fromJson(streamUrl, StreamUrl.class);
    }

    public Team getTeam(Team team) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(team));
        Request request = new Request.Builder()
                .url(serverUrl + "/getteam")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(), Team.class);
    }

//    String doGetRequest(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();

//    }


}
