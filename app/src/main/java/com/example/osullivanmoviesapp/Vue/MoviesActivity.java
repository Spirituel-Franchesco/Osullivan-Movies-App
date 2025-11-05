package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.osullivanmoviesapp.API.TMDB_API;
import com.example.osullivanmoviesapp.Adaptateur.MoviesRecyclerViewAdapter;
import com.example.osullivanmoviesapp.Modele.Movie;
import com.example.osullivanmoviesapp.Modele.MovieResponse;
import com.example.osullivanmoviesapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a67b57849deb687f2cd49d7a8298b366";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDB_API api = retrofit.create(TMDB_API.class);

        Call<MovieResponse> call = api.getPopularMovies("a67b57849deb687f2cd49d7a8298b366", "en-US", 1);

        Log.d("RetrofitTest", call.request().url().toString());


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesRecyclerViewAdapter(MoviesActivity.this, movies));
                } else {
                    Toast.makeText(MoviesActivity.this, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MoviesActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
