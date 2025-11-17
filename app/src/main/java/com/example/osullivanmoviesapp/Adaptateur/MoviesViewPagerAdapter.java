package com.example.osullivanmoviesapp.Adaptateur;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.osullivanmoviesapp.Modele.Movie;
import com.example.osullivanmoviesapp.R;
import com.example.osullivanmoviesapp.Vue.MovieDetailsActivity;
import com.example.osullivanmoviesapp.Vue.MoviesActivity;

import java.util.List;

/**
 * Adapter pour le ViewPager2 qui affiche les films en carrousel
 */
public class MoviesViewPagerAdapter extends RecyclerView.Adapter<MoviesViewPagerAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MoviesViewPagerAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.viewpager_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        // Affiche le titre
        holder.movieTitle.setText(movie.getTitle());

        // Affiche le score
        holder.scoreText.setText("IMDB Score: " + movie.getFormattedRating());

        // Charge l'image
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background))
                .into(holder.moviePoster);

        // Clic sur l'image pour voir les détails
        holder.moviePoster.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie_id", movie.getId());
            context.startActivity(intent);
        });

        // ✅ Gestion du bouton PRÉCÉDENT
        holder.btnPrevious.setOnClickListener(v -> {
            if (context instanceof MoviesActivity) {
                ((MoviesActivity) context).goToPreviousMovie();
            }
        });

        // ✅ Gestion du bouton SUIVANT
        holder.btnNext.setOnClickListener(v -> {
            if (context instanceof MoviesActivity) {
                ((MoviesActivity) context).goToNextMovie();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * ✅ ViewHolder INTERNE - Cette classe doit RESTER ici
     * Elle contient les références aux vues de chaque page du carrousel
     */
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        TextView scoreText;
        TextView btnPrevious;  // Bouton ◄◄
        TextView btnNext;      // Bouton ►►

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Trouve toutes les vues
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            scoreText = itemView.findViewById(R.id.scoreText);
            btnPrevious = itemView.findViewById(R.id.btnPrevious);
            btnNext = itemView.findViewById(R.id.btnNext);
        }
    }
}