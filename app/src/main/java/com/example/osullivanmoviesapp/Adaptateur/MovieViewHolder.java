package com.example.osullivanmoviesapp.Adaptateur;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osullivanmoviesapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView movieImage;

    TextView movieTitle;
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        movieImage = itemView.findViewById(R.id.movieImage);
        movieTitle = itemView.findViewById(R.id.movieTitle);
    }
}

