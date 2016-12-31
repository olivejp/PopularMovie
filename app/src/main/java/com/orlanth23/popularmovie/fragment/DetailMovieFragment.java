package com.orlanth23.popularmovie.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.adapter.ReviewAdapter;
import com.orlanth23.popularmovie.adapter.TrailerAdapter;
import com.orlanth23.popularmovie.data.MovieContract;
import com.orlanth23.popularmovie.listener.EndlessRecyclerOnScrollListener;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.model.ResultListReview;
import com.orlanth23.popularmovie.model.ResultListTrailers;
import com.orlanth23.popularmovie.model.Review;
import com.orlanth23.popularmovie.model.Trailer;
import com.orlanth23.popularmovie.model.Utils;
import com.orlanth23.popularmovie.provider.MovieProvider;
import com.orlanth23.popularmovie.retrofitservice.MovieDbAPI;
import com.orlanth23.popularmovie.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMovieFragment extends CustomChangeTitleFragment {

    public static final String TAG = DetailMovieFragment.class.getSimpleName();

    public static final String ARG_MOVIE = "ARG_MOVIE";
    private static final String BUNDLE_ARRAY_VIDEOS = "BUNDLE_ARRAY_VIDEOS";
    private static final String BUNDLE_ARRAY_REVIEWS = "BUNDLE_ARRAY_REVIEWS";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_CURRENT_PAGE";

    private Movie movie;

    private Unbinder unbind;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Trailer> arrayListTrailer;
    private ArrayList<Review> arrayListReview;

    private MovieDbAPI movieDbAPI;

    private int currentPageReview;

    @BindView(R.id.recycler_reviews) RecyclerView recyclerReview;
    @BindView(R.id.recycler_trailers) RecyclerView recyclerTrailer;
    @BindView(R.id.movie_detail_poster) ImageView image_poster;
    @BindView(R.id.movie_detail_overview) TextView overview;
    @BindView(R.id.movie_detail_releaseDate) TextView releaseDate;
    @BindView(R.id.movie_detail_voteAverage) TextView voteAverage;
    @BindView(R.id.backdrop) ImageView img_backdrop;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btn_add_favorite) Button btn_add_favorite;
    @BindView(R.id.btn_dislike) Button btn_dislike;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_MOVIE, movie);
        outState.putParcelableArrayList(BUNDLE_ARRAY_VIDEOS, arrayListTrailer);
        outState.putParcelableArrayList(BUNDLE_ARRAY_REVIEWS, arrayListReview);
        outState.putInt(BUNDLE_CURRENT_PAGE, currentPageReview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_MOVIE)) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    private Callback<ResultListReview> callbackReview = new Callback<ResultListReview>() {
        @Override
        public void onResponse(Call<ResultListReview> call, Response<ResultListReview> response) {
            try {
                if (response.body() != null){
                    ResultListReview rsp = response.body();
                    ArrayList<Review> p_arrayList = rsp.getResults();
                    if (p_arrayList != null) {
                        for (Review review : p_arrayList) {
                            arrayListReview.add(review);
                            if (!recyclerReview.isComputingLayout()) {
                                reviewAdapter.notifyItemInserted(arrayListReview.indexOf(review));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Callback - OnResponse", "Big trouble in little china");
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResultListReview> call, Throwable t) {
            Log.d("Callback - onFailure", t.toString());
        }
    };

    // CallBack to receive the trailers
    private Callback<ResultListTrailers> callbackTrailer = new Callback<ResultListTrailers>() {
        @Override
        public void onResponse(Call<ResultListTrailers> call, Response<ResultListTrailers> response) {
            try {
                if (response.body() != null){
                    ResultListTrailers rsp = response.body();
                    ArrayList<Trailer> p_arrayList = rsp.getResults();
                    if (p_arrayList != null) {
                        for (Trailer trailer : p_arrayList) {
                            arrayListTrailer.add(trailer);
                            if (!recyclerTrailer.isComputingLayout()) {
                                trailerAdapter.notifyItemInserted(arrayListTrailer.indexOf(trailer));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Callback - OnResponse", "Big trouble in little china");
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResultListTrailers> call, Throwable t) {
            Log.d("Callback - onFailure", t.toString());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        boolean firstLaunch = false;

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(ARG_MOVIE)) {
                movie = savedInstanceState.getParcelable(ARG_MOVIE);
            }
            if (savedInstanceState.containsKey(BUNDLE_ARRAY_REVIEWS)){
                arrayListReview = savedInstanceState.getParcelableArrayList(BUNDLE_ARRAY_REVIEWS);
            }
            if (savedInstanceState.containsKey(BUNDLE_ARRAY_VIDEOS)) {
                arrayListTrailer = savedInstanceState.getParcelableArrayList(BUNDLE_ARRAY_VIDEOS);
            }
            if (savedInstanceState.containsKey(BUNDLE_CURRENT_PAGE)) {
                currentPageReview = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE);
            }
        } else {
            arrayListTrailer = new ArrayList<>();
            arrayListReview = new ArrayList<>();
            currentPageReview = 1;
            firstLaunch = true;
        }

        // inflate the fragment detail movie
        View fragmentView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        // bind the views
        unbind = ButterKnife.bind(this, fragmentView);

        if (movie != null) {

            // Put Add to favorite button to invisible if the movie is already in the favorites.
            if (btn_add_favorite != null) {
                if (isMovieFavorite(movie.getId())) {
                    btn_add_favorite.setEnabled(false);
                }
            }

            Picasso picasso = Picasso.with(getActivity());
            picasso.load(Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_URL).concat(movie.getPoster_path())).into(image_poster);
            picasso.load(Constants.IMAGE_BASE_URL.concat(Constants.IMAGE_WIDTH_LARGE_URL).concat(movie.getBackdrop_path())).into(img_backdrop);

            // Change le titre qui sera affiché
            collapsingToolbarLayout.setTitle(movie.getOriginal_title());

            // call the retrofit builder
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.MOVIE_DB_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            movieDbAPI = retrofit.create(MovieDbAPI.class);

            // set the values on the graphic views
            overview.setText(movie.getOverview());
            releaseDate.setText(movie.getRelease_date());
            voteAverage.setText(String.valueOf(movie.getVote_average()));

            // change title depending on the preference - we use the abstract class CustomChangeTitle
            changeTitleFragment();

            LinearLayoutManager linearLayoutManagerTrailer = new LinearLayoutManager(getActivity());
            LinearLayoutManager linearLayoutManagerReview = new LinearLayoutManager(getActivity());
            recyclerTrailer.setLayoutManager(linearLayoutManagerTrailer);
            recyclerReview.setLayoutManager(linearLayoutManagerReview);

            trailerAdapter = new TrailerAdapter(getActivity(), arrayListTrailer);
            reviewAdapter = new ReviewAdapter(arrayListReview);

            recyclerTrailer.setAdapter(trailerAdapter);
            recyclerReview.setAdapter(reviewAdapter);

            // create a listener to scroll
            recyclerReview.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManagerReview) {
                @Override
                public void onLoadMore() {
                    loadMoreReview(currentPageReview);
                }
            });

            // Get the trailers
            if (firstLaunch) {
                Call<ResultListTrailers> callbackApiTrailer = movieDbAPI.getTrailers(movie.getId(), Constants.MOVIE_DB_API_KEY);
                if (callbackApiTrailer != null) {
                    callbackApiTrailer.enqueue(callbackTrailer);
                }
            }

            updateButtons();
        }

        return fragmentView;
    }

    private void loadMoreReview(int p_currentPage) {
        Call<ResultListReview> callbackApi;
        callbackApi = movieDbAPI.getReviews(movie.getId(), Constants.MOVIE_DB_API_KEY, p_currentPage);
        if (callbackApi != null) {
            callbackApi.enqueue(callbackReview);
            currentPageReview++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    private boolean isMovieFavorite(int movieId){
        boolean ret = false;
        String selectionArgs[] = new String[1];
        selectionArgs[0] = String.valueOf(movieId);

        // Check the movie is not yet in the favorite database
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieProvider.sSelectionMovieById,
                selectionArgs,
                null);

        if (cursor != null) {
            ret = cursor.moveToFirst();
            cursor.close();
        }

        return ret;
    }

    private void updateButtons(){
        if (isMovieFavorite(movie.getId())){
            btn_add_favorite.setVisibility(View.GONE);
            btn_dislike.setVisibility(View.VISIBLE);
        } else {
            btn_add_favorite.setVisibility(View.VISIBLE);
            btn_dislike.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_dislike)
    public void deleteFromFavorite(View view){
        String selectionArgs[] = new String[1];
        selectionArgs[0] = String.valueOf(movie.getId());

        int idDelete = getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, MovieProvider.sSelectionMovieById, selectionArgs);

        updateButtons();
    }

    @OnClick(R.id.btn_add_favorite)
    public void addToFavorite(View view) {
        ContentValues contentValues = Utils.getContentValuesFromMovie(movie);

        Uri movieUri = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        if (movieUri != null) {
            Toast.makeText(getActivity(), R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.deleted_from_favorite, Toast.LENGTH_SHORT).show();
        }
        updateButtons();
    }
}
