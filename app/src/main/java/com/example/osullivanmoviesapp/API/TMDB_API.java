package com.example.osullivanmoviesapp.API;

import com.example.osullivanmoviesapp.Modele.Movie;
import com.example.osullivanmoviesapp.Modele.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDB_API {

    // Liste des films populaires
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    // Détails d’un film
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
