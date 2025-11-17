package com.example.osullivanmoviesapp.Vue;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.osullivanmoviesapp.API.TMDB_API;
import com.example.osullivanmoviesapp.Modele.Movie;
import com.example.osullivanmoviesapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String API_KEY = "a67b57849deb687f2cd49d7a8298b366";
    private static final String TAG = "MovieDetails";

    // Déclaration des vues
    private ImageView backdropImage;
    private TextView titleView;
    private TextView overviewView;
    private TextView ratingView;
    private TextView releaseDateView;
    private TextView languageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton fabFavorite;
    private Toolbar toolbar;

    private boolean isFavorite = false; // Pour gérer l'état du favori

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // 1. INITIALISATION DES VUES
        initViews();

        // 2. CONFIGURATION DE LA TOOLBAR
        setupToolbar();


        // 4. RÉCUPÉRATION DE L'ID DU FILM
        int movieId = getIntent().getIntExtra("movie_id", -1);

        if (movieId == -1) {
            Toast.makeText(this, "Erreur: Film introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 5. CHARGEMENT DES DÉTAILS DU FILM
        loadMovieDetails(movieId);
    }

    /**
     * Initialise toutes les vues de l'interface
     */
    private void initViews() {
        backdropImage = findViewById(R.id.movieImage);
        titleView = findViewById(R.id.movieTitle);
        overviewView = findViewById(R.id.movieDescription);
        ratingView = findViewById(R.id.movieNote);
        releaseDateView = findViewById(R.id.movieReleaseDate);
        languageView = findViewById(R.id.movieLanguage);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.toolbar);
    }

    /**
     * Configure la Toolbar avec le bouton retour
     */
    private void setupToolbar() {
        setSupportActionBar(toolbar);

        // Active le bouton retour
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Le titre sera géré par le CollapsingToolbarLayout
            getSupportActionBar().setTitle("");
        }
    }

    /**
     * Configure le bouton favoris (FloatingActionButton)
     */


    /**
     * Charge les détails du film depuis l'API TMDB
     */
    private void loadMovieDetails(int movieId) {
        // Configuration de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDB_API api = retrofit.create(TMDB_API.class);
        Call<Movie> call = api.getMovieDetails(movieId, API_KEY, "en-US");

        Log.d(TAG, "URL de la requête: " + call.request().url().toString());

        // Appel asynchrone à l'API
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    displayMovieDetails(movie);
                } else {
                    Log.e(TAG, "Erreur réponse: " + response.code());
                    Toast.makeText(MovieDetailsActivity.this,
                            "Erreur lors du chargement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "Erreur réseau: " + t.getMessage());
                Toast.makeText(MovieDetailsActivity.this,
                        "Erreur de connexion", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    /**
     * Affiche les détails du film dans l'interface
     */
    private void displayMovieDetails(Movie movie) {
        // Titre (aussi dans le CollapsingToolbar)
        titleView.setText(movie.getTitle());
        collapsingToolbar.setTitle(movie.getTitle());

        // Note avec formatage
        String rating = String.format("%.1f", movie.getVoteAverage());
        ratingView.setText(rating);

        // Synopsis
        overviewView.setText(movie.getOverview());

        // Date de sortie
        if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()) {
            releaseDateView.setText(movie.getReleaseDate());
        }

        // Langue originale
        if (movie.getOriginalLanguage() != null) {
            languageView.setText(movie.getOriginalLanguage().toUpperCase());
        }

        // IMPORTANT: Charge le BACKDROP (image horizontale) au lieu du poster
        loadBackdropImage(movie);
    }

    /**
     * Charge l'image backdrop (plein écran)
     * CONCEPT: Le backdrop est différent du poster, c'est une image horizontale
     */
    private void loadBackdropImage(Movie movie) {
        String imageUrl;

        // Priorité 1: Backdrop (image horizontale plein écran)
        if (movie.getBackdropPath() != null && !movie.getBackdropPath().isEmpty()) {
            // w1280 = haute qualité pour backdrop
            imageUrl = "https://image.tmdb.org/t/p/w1280" + movie.getBackdropPath();
        }
        // Priorité 2: Poster si pas de backdrop
        else if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
            imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        }
        // Fallback: Image par défaut
        else {
            backdropImage.setImageResource(R.drawable.ic_launcher_background);
            return;
        }

        // Chargement avec Glide
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop() // Remplit tout l'espace
                        .error(R.drawable.ic_launcher_background)) // Image si erreur
                .into(backdropImage);

        Log.d(TAG, "Chargement image: " + imageUrl);
    }

    /**
     * Gère le clic sur le bouton retour de la toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gère le bouton retour
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Retour à l'écran précédent
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}