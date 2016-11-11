package com.orlanth23.popularmovie.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.orlanth23.popularmovie.activity.MainActivity;
import com.orlanth23.popularmovie.activity.SettingsActivity;
import com.orlanth23.popularmovie.adapter.MovieAdapter;
import com.orlanth23.popularmovie.listener.EndlessRecyclerOnScrollListener;
import com.orlanth23.popularmovie.model.Movie;
import com.orlanth23.popularmovie.model.ResultListMovie;
import com.orlanth23.popularmovie.retrofitservice.MovieDbAPI;
import com.orlanth23.popularmovie.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends CustomChangeTitleFragment {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String BUNDLE_ARRAY_LIST = "BUNDLE_ARRAY_LIST";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_CURRENT_PAGE";

    private static final int NB_COLUMN_PORT = 2;
    private static final int NB_COLUMN_LAND = 4;

    private int currentPage;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> arrayListMovie;
    private MovieDbAPI movieDbAPI;
    private SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();
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

        // get the recyclerVIew
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        if (recyclerView != null){
            recyclerView.setHasFixedSize(true);
        }

        // change title depending on the preference - we use the abstract class CustomChangeTitle
        changeTitleFragment();

        // change the number of column depending on the screen orientation
        Configuration config = getActivity().getResources().getConfiguration();
        int nb_column = (config.orientation == Configuration.ORIENTATION_PORTRAIT) ? NB_COLUMN_PORT : NB_COLUMN_LAND;

        // we use a GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), nb_column);
        recyclerView.setLayoutManager(gridLayoutManager);

        // create a MovieAdapter
        movieAdapter = new MovieAdapter(getActivity(), arrayListMovie);
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
        // we save the arrayListMovie and the currentPage
        outState.putParcelableArrayList(BUNDLE_ARRAY_LIST, arrayListMovie);
        outState.putInt(BUNDLE_CURRENT_PAGE, currentPage);
        super.onSaveInstanceState(outState);
    }

    private Callback<ResultListMovie> callback = new Callback<ResultListMovie>() {
        @Override
        public void onResponse(Call<ResultListMovie> call, Response<ResultListMovie> response) {
            try {
                ArrayList<Movie> p_arrayList = response.body().getResults();
                if (p_arrayList != null) {
                    for (Movie movie : p_arrayList) {
                        arrayListMovie.add(movie);
                        if (!recyclerView.isComputingLayout()) {
                            movieAdapter.notifyItemInserted(arrayListMovie.indexOf(movie));
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
        }
        if (callbackApi != null) {
            callbackApi.enqueue(callback);
            currentPage++;
        }
    }
}