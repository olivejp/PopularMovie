package com.orlanth23.popularmovie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailMovieFragment extends CustomChangeTitleFragment {

    public static final String TAG = DetailMovieFragment.class.getSimpleName();

    public static final String ARG_MOVIE = "ARG_MOVIE";
    private Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_MOVIE)) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the fragment detail movie
        View fragmentView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        // bind the views
        ImageView image_poster = (ImageView) fragmentView.findViewById(R.id.movie_detail_poster);
        TextView overview = (TextView) fragmentView.findViewById(R.id.movie_detail_overview);
        TextView releaseDate = (TextView) fragmentView.findViewById(R.id.movie_detail_releaseDate);
        TextView voteAverage = (TextView) fragmentView.findViewById(R.id.movie_detail_voteAverage);
        TextView title = (TextView) fragmentView.findViewById(R.id.movie_detail_title);

        // load the image with picasso
        Picasso picasso = Picasso.with(getActivity());
        picasso.load(Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_URL).concat(movie.getPoster_path())).into(image_poster);

        // set the values on the graphic views
        overview.setText(movie.getOverview());
        title.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        voteAverage.setText(String.valueOf(movie.getVote_average()));

        // change title depending on the preference - we use the abstract class CustomChangeTitle
        changeTitleFragment();

        return fragmentView;
    }
}
