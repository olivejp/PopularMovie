package com.orlanth23.popularmovie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailMovieFragment extends Fragment {

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
        View fragmentView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        ImageView image_poster = (ImageView) fragmentView.findViewById(R.id.movie_detail_poster);
        TextView overview = (TextView) fragmentView.findViewById(R.id.movie_detail_overview);
        TextView releaseDate = (TextView) fragmentView.findViewById(R.id.movie_detail_releaseDate);
        TextView tagline = (TextView) fragmentView.findViewById(R.id.movie_detail_tagline);
        TextView title = (TextView) fragmentView.findViewById(R.id.movie_detail_title);

        Picasso picasso = Picasso.with(getActivity());
        picasso.load(Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_URL).concat(movie.getPoster_path())).into(image_poster);

        overview.setText(movie.getOverview());
        title.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        tagline.setText(movie.getTagline());

        return fragmentView;
    }
}
