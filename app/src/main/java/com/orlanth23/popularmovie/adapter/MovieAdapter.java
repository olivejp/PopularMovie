package com.orlanth23.popularmovie.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.activity.DetailMovieActivity;
import com.orlanth23.popularmovie.fragment.DetailMovieFragment;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.utils.ConfSingleton;
import com.orlanth23.popularmovie.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.orlanth23.popularmovie.activity.MainActivity.DETAILFRAGMENT_TAG;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private ArrayList<Movie> movies;
    private Picasso picasso;
    private AppCompatActivity activity;

    public MovieAdapter(AppCompatActivity p_activity, ArrayList<Movie> p_movies) {
        movies = p_movies;
        picasso = Picasso.with(p_activity);
        activity = p_activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayoutView = inflater.inflate(R.layout.cardview_movie, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        String imagePath = Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_URL).concat(movie.getPoster_path());
        picasso.load(imagePath).into(holder.movieThumbnail);

        holder.movieThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prepare the bundle
                Bundle bundle = new Bundle();
                bundle.putParcelable(DetailMovieFragment.ARG_MOVIE, movie);

                // the singleton is used to know if we get 1 or 2 panes.
                if (ConfSingleton.getInstance().isTwoPane()){
                    DetailMovieFragment fragment = new DetailMovieFragment();
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_detail_container, fragment, DETAILFRAGMENT_TAG)
                            .commit();
                }else{
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(activity, DetailMovieActivity.class);
                    activity.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieThumbnail;
        ViewHolder(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumbnail);
        }
    }
}
