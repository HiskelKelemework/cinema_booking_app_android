package com.example.wake.ethiocinemamovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wake.ethiocinemamovie.Adapters.MovieListAdapter;
import com.example.wake.ethiocinemamovie.Models.Movie;
import com.example.wake.ethiocinemamovie.Models.MoviesManager;

import java.util.ArrayList;


public class MoviesListFrag extends Fragment implements MoviesManager.OnMoviesLoadedListener, MovieListAdapter.MovieChosenListener {
    public static final int ALL_MOVIES = 0;
    public static final int TODAY_MOVIES = 1;
    public static final int TOP_RATED = 2;

    View view;
    RecyclerView recyclerView;
    MovieListAdapter adapter;
    LinearLayoutManager layoutManager;
    ProgressBar pb;

    MoviesManager moviesManager;
    ArrayList<Movie> moviesToList;
    LinearLayout connectionProblem;
    int pageType = ALL_MOVIES;

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.movie_list_recyler);
        moviesManager = MoviesManager.getInstance(getActivity());
        moviesToList = new ArrayList<Movie>();

        connectionProblem = view.findViewById(R.id.movies_conn_prob);
        pb = view.findViewById(R.id.movie_loading_prog);
        pb.setVisibility(View.VISIBLE);

        setUpMoviesToList();
        adapter = new MovieListAdapter(moviesToList, getActivity(), this);


        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        Button retry = view.findViewById(R.id.movies_retry);

        retry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setUpMoviesToList();
                pb.setVisibility(View.VISIBLE);
                connectionProblem.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void setUpMoviesToList() {
        Log.e("erere", "hre");
        if (moviesManager.areMoviesLoaded()) {
            pb.setVisibility(View.GONE);

            if (pageType == ALL_MOVIES) {
                for (int i = 0; i < moviesManager.getAllMovies().size(); i++) {
                    moviesToList.add(moviesManager.getMovie(i));
                }
//                adapter.notifyDataSetChanged();
                Log.i("succ", "hre all movies");
            }
            else if(pageType == TODAY_MOVIES) {
                moviesToList.add(moviesManager.getMovie(0));
                adapter.notifyDataSetChanged();
            }
            else if(pageType == TOP_RATED) {
                moviesToList.add(moviesManager.getMovie(1));
                Toast.makeText(getActivity(), " " + moviesToList.size(), Toast.LENGTH_SHORT).show();
//                adapter.notifyDataSetChanged();
            }
        }
        else {
            Log.e("erere", "hre");
            moviesManager.loadMoviesAndNotifyMe(this);
        }

    }

    private ArrayList<Movie> getTopRatedMovies(ArrayList<Movie> allMovies) {
        // logic to filter out top rated movies outof all movies
        return null;
    }

    private ArrayList<Movie> getTodayMovies(ArrayList<Movie> allMovies) {
        // the logic to filter out today's movies
        return null;
    }


    public ArrayList<Movie> getMovies() {
/*
        ArrayList<Movie> movies = new ArrayList<>();
        moviesToList.add(
                new Movie("mukera", "Yehone", "4.5", "faf", "Faf")
        );
        movies.add(
                new Movie("mukera", "Yehone", "4.5", "faf", "Faf")
        );
        movies.add(
                new Movie("mukera", "Yehone", "4.5", "faf", "Faf")
        );
//        pb.setVisibility(View.GONE);
//        connectionProblem.setVisibility(View.VISIBLE);

        return movies; */
        return null;
    }

    @Override
    public void OnMoviesLoaded() {
        pb.setVisibility(View.GONE);
        Log.i("succ", "from listneer");
        if (moviesManager.areMoviesLoaded()) {
            setUpMoviesToList();
        }

        else {
            connectionProblem.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (adapter == null) {
            adapter = new MovieListAdapter(moviesToList, getActivity(), this);
        }
    }

    @Override
    public void onMovieChosen(String movieId) {
        Intent intent = new Intent(getActivity(), SingleMovieActivity.class);
        intent.putExtra(MoviesActivity.KEY_CHOSEN_MOVIE_POSITION,
                moviesManager.getMoviePosition(movieId));
        getActivity().startActivity(intent);
    }
}
