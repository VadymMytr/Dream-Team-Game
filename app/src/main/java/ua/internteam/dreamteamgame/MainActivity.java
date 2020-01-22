package ua.internteam.dreamteamgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.setIP);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickM();
            }
        });

    }

    void onClickM(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.1.125:8080/answer/add") // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create()) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .build();

        Server service = retrofit.create(Server.class);
        Call<List<AddResult>> call = service.addAnswer("TEST");
        call.enqueue(new Callback<List<AddResult>>() {
            @Override
            public void onResponse(Call<List<AddResult>> call, Response<List<AddResult>> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<List<AddResult>> call, Throwable t) {
                // ошибка во время выполнения запроса
            }
        });
    }
}

