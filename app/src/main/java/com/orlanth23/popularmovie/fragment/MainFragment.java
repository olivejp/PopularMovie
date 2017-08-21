package com.orlanth23.popularmovie.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.activity.SettingsActivity;
import com.orlanth23.popularmovie.adapter.MovieAdapter;
import com.orlanth23.popularmovie.data.MovieContract;
import com.orlanth23.popularmovie.listener.EndlessRecyclerOnScrollListener;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.model.ResultListMovie;
import com.orlanth23.popularmovie.retrofitservice.MovieDbAPI;
import com.orlanth23.popularmovie.utils.ConfSingleton;
import com.orlanth23.popularmovie.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends CustomFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String BUNDLE_ARRAY_LIST = "BUNDLE_ARRAY_LIST";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_CURRENT_PAGE";

    private static final int NB_COLUMN_TWO_PANE_PORT = 1;
    private static final int NB_COLUMN_TWO_PANE_LAND = 3;
    private static final int NB_COLUMN_PORT = 2;
    private static final int NB_COLUMN_LAND = 4;

    private int currentPage;
    private MovieAdapter movieAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Unbinder unbind;

    private ArrayList<Movie> arrayListMovie;
    private MovieDbAPI movieDbAPI;
    private SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();

        // change title depending on the preference - we use the abstract class CustomChangeTitle
        changeTitleFragment();

        loadData(currentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_ARRAY_LIST)){
            arrayListMovie = savedInstanceState.getParcelableArrayList(BUNDLE_ARRAY_LIST);
            currentPage = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE);
        } else {
            arrayListMovie = new ArrayList<>();
            currentPage = 1;
        }

        // inflate the fragment_main
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        // get the recyclerVIews
        unbind = ButterKnife.bind(this, fragmentView);
        if (recyclerView != null){
            // http://stackoverflow.com/questions/28709220/understanding-recyclerview-sethasfixedsize
            recyclerView.setHasFixedSize(true);
        }

        // change the number of column depending on the screen orientation and if we have two panels or not
        Configuration config = getActivity().getResources().getConfiguration();
        ConfSingleton.getInstance();
        int nb_column;
        if (ConfSingleton.isTwoPane()){
            nb_column = (config.orientation == Configuration.ORIENTATION_PORTRAIT) ? NB_COLUMN_TWO_PANE_PORT : NB_COLUMN_TWO_PANE_LAND;
        }else{
            nb_column = (config.orientation == Configuration.ORIENTATION_PORTRAIT) ? NB_COLUMN_PORT : NB_COLUMN_LAND;
        }

        // we use a GridLayoutManager
        Context context = getActivity();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, nb_column);
        recyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter((AppCompatActivity) getActivity(), arrayListMovie);
        recyclerView.setAdapter(movieAdapter);

        // call the retrofit builder
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.MOVIE_DB_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        movieDbAPI = retrofit.create(MovieDbAPI.class);

        // load the preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // create a listener to scroll
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                loadData(currentPage);
            }
        });

        // we want to see the options menu
        setHasOptionsMenu(true);

        return fragmentView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                // launch the settings activity
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflate the menu
        menu.clear();
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // we save the arrayListMovie and the currentPage
        outState.putParcelableArrayList(BUNDLE_ARRAY_LIST, arrayListMovie);
        outState.putInt(BUNDLE_CURRENT_PAGE, currentPage);
    }

    private Callback<ResultListMovie> callbackMovie = new Callback<ResultListMovie>() {
        @Override
        public void onResponse(Call<ResultListMovie> call, Response<ResultListMovie> response) {
            try {
                if (response.body() != null){
                    ResultListMovie rsp = response.body();
                    ArrayList<Movie> p_arrayList = rsp.getResults();
                    if (p_arrayList != null) {
                        for (Movie movie : p_arrayList) {
                            arrayListMovie.add(movie);
                            if (!recyclerView.isComputingLayout()) {
                                movieAdapter.notifyItemInserted(arrayListMovie.indexOf(movie));
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
        public void onFailure(Call<ResultListMovie> call, Throwable t) {
            Log.d("Callback - onFailure", t.toString());
        }
    };

    private void loadData(int p_currentPage) {
        Call<ResultListMovie> callbackApi = null;
        switch (sharedPreferences.getString(getString(R.string.pref_key_list_sort), getString(R.string.popularMovies))){
            case "popularMovies":
                callbackApi = movieDbAPI.getPopularMovies(Constants.MOVIE_DB_API_KEY, p_currentPage);
                break;
            case "topRated":
                callbackApi = movieDbAPI.getTopRated(Constants.MOVIE_DB_API_KEY, p_currentPage);
                break;
            case "favorite":
                getActivity().getSupportLoaderManager().initLoader(1, null, this);
                break;
        }
        if (callbackApi != null) {
            callbackApi.enqueue(callbackMovie);
            currentPage++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    private Movie getMovieFromCursor(Cursor cursor, int position) {
        if (cursor.moveToPosition(position)) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry._ID)));
            movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
            movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
            movie.setImdb_id(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMDB_ID)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setVote_average(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            return movie;
        } else {
            return null;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTENT_URI = MovieContract.MovieEntry.CONTENT_URI;
        return new CursorLoader(getActivity(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        arrayListMovie.clear();

        int count = cursor.getCount();
        for(int i = 0; i < count; i ++){
            arrayListMovie.add(getMovieFromCursor(cursor, i));
        }

        if (!recyclerView.isComputingLayout()) {
            movieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}