package ua.internteam.dreamteamgame.api;

import java.io.IOException;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ua.internteam.dreamteamgame.api.entity.Answer;

public class Api {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private String url;
    private Gson gson;

    public Api(String url) {
        this.url = url;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public Api() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Answer sendAnswer(Answer answer) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(answer));
        Request request = new Request.Builder()
                .url(url + "/answer/add")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(),Answer.class);
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
