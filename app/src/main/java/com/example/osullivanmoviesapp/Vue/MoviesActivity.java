package com.example.osullivanmoviesapp.Vue;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.osullivanmoviesapp.API.TMDB_API;
import com.example.osullivanmoviesapp.Adaptateur.MoviesViewPagerAdapter;
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

    private ViewPager2 viewPager;
    private MoviesViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        viewPager = findViewById(R.id.moviesViewPager);
        loadMovies();
    }

    // ✅ NOUVEAU : Méthode publique pour aller au film précédent
    public void goToPreviousMovie() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1, true);
        }
    }

    // ✅ NOUVEAU : Méthode publique pour aller au film suivant
    public void goToNextMovie() {
        int currentItem = viewPager.getCurrentItem();
        if (adapter != null && currentItem < adapter.getItemCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        }
    }

    private void loadMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDB_API api = retrofit.create(TMDB_API.class);
        Call<MovieResponse> call = api.getPopularMovies(API_KEY, "en-US", 1);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    setupViewPager(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setupViewPager(List<Movie> movies) {
        adapter = new MoviesViewPagerAdapter(this, movies);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new DepthPageTransformer());
    }

    private static class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) {
                view.setAlpha(0f);
            } else if (position <= 0) {
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);
            } else if (position <= 1) {
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0f);
            }
        }
    }
}