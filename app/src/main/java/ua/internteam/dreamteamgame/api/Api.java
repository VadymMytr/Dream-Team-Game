package ua.internteam.dreamteamgame.api;

import java.io.IOException;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ua.internteam.dreamteamgame.api.entity.Answer;
import ua.internteam.dreamteamgame.api.entity.StreamUrl;
import ua.internteam.dreamteamgame.api.entity.Team;

public class Api {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private String serverUrl;
    private Gson gson;

    public Api(String serverUrl) {
        this.serverUrl = serverUrl;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public Api() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public void setUrl(String url) {
        this.serverUrl = url;
    }

    public Answer sendAnswer(Answer answer) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(answer));
        Request request = new Request.Builder()
                .url(serverUrl + "/answer/add")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(),Answer.class);
    }

    public String getStreamUrl(Team team) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(team));
        Request request = new Request.Builder()
                .url(serverUrl + "/geturl")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    public Team getTeam(Team team) throws IOException{
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
