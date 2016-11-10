package com.orlanth23.popularmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.activity.DetailMovieActivity;
import com.orlanth23.popularmovie.fragment.DetailMovieFragment;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private ArrayList<Movie> movies;
    private Picasso picasso;
    private Context context;

    public MovieAdapter(Context pcontext, ArrayList<Movie> pmovies) {
        movies = pmovies;
        picasso = Picasso.with(pcontext);
        context = pcontext;
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
        picasso.load(Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_URL).concat(movie.getPoster_path())).into(holder.movieThumbnail);

        holder.movieThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DetailMovieFragment.ARG_MOVIE, movie);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, DetailMovieActivity.class);

                context.startActivity(intent);
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
