package ua.internteam.dreamteamgame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Server {
        @POST("answer/add")
        Call<List<AddResult>> addAnswer(@Body String answers);
    }
