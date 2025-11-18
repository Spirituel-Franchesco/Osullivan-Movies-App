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

import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private List<Movie> movies;
    private LayoutInflater mLayoutInflater;

    public MoviesRecyclerViewAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.recycleview_item_layout, parent, false);
        return new MovieViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.movieTitle.setText(movie.getTitle());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().error(R.drawable.ic_launcher_background))
                .into(holder.movieImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie_id", movie.getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }
}
