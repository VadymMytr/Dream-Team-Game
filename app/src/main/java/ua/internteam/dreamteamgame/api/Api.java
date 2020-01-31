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

    public Api(String serverUrl) {
        this.client = new OkHttpClient();
        this.serverUrl = serverUrl;
        this.gson = new Gson();
    }

    public String getUrl() {
        return serverUrl;
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
}
