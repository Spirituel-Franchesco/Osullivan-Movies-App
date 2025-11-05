package com.example.osullivanmoviesapp.Vue;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.osullivanmoviesapp.API.TMDB_API;
import com.example.osullivanmoviesapp.Modele.Movie;
import com.example.osullivanmoviesapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String API_KEY = "a67b57849deb687f2cd49d7a8298b366";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView imageView = findViewById(R.id.movieImage);
        TextView titleView = findViewById(R.id.movieTitle);
        TextView overviewView = findViewById(R.id.movieDescription);
        TextView ratingView = findViewById(R.id.movieNote);

        int movieId = getIntent().getIntExtra("movie_id", -1);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDB_API api = retrofit.create(TMDB_API.class);
        Call<Movie> call = api.getMovieDetails(movieId, API_KEY, "en-US");

        Log.d("RetrofitTest", call.request().url().toString());

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    titleView.setText(movie.getTitle());
                    overviewView.setText(movie.getOverview());
                    ratingView.setText( " " + movie.getVoteAverage());
                    Glide.with(MovieDetailsActivity.this)
                            .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
