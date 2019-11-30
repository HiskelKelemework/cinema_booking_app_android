package com.example.wake.ethiocinemamovie.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.R;
import com.example.wake.ethiocinemamovie.MoviesActivity;
import com.example.wake.ethiocinemamovie.SingleMovieActivity;

import java.util.ArrayList;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<Movie> movies;
    private Context ctx;
    private MovieChosenListener listener;

    public MovieListAdapter(ArrayList<Movie> movies, Context ctx, MovieChosenListener listener) {
        this.movies = movies;
        this.ctx = ctx;
        this.listener = listener;
    }

    public interface MovieChosenListener {
        public void onMovieChosen(String movieId);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_movie, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final Movie currentMovie = movies.get(position);
        holder.title.setText(currentMovie.getTitle());
        holder.genre.setText(currentMovie.getGenre());
        holder.rating.setText(currentMovie.getRating());

        holder.view.findViewById(R.id.singleMovieClickable).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onMovieChosen(currentMovie.getId());
                        /*
                        Intent intent = new Intent(ctx, SingleMovieActivity.class);
                        intent.putExtra(MoviesActivity.KEY_CHOSEN_MOVIE_POSITION,
                                 currentMovie.getId());
                        ActivityOptionsCompat options =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        (Activity) ctx, holder.image, ViewCompat.getTransitionName(holder.image));
                        ctx.startActivity(intent, options.toBundle()); */
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, genre, rating, today;
        public ImageView image;
        public View view;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.movie_title);
            genre = itemView.findViewById(R.id.movie_genre);
            rating = itemView.findViewById(R.id.movie_rating);
            image = itemView.findViewById(R.id.movie_pic);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(ctx, "From the other side", Toast.LENGTH_SHORT).show();
        }
    }
}
