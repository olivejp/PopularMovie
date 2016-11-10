package com.orlanth23.popularmovie.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

public class MainFragment extends Fragment {

    private int currentPage;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> arrayListMovie;
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int NB_COLUMN_PORT = 2;
    private static final int NB_COLUMN_LAND = 4;
    private static final String BUNDLE_ARRAY_LIST = "BUNDLE_ARRAY_LIST";
    private static boolean firstTime;

    @Override
    public void onStart() {
        super.onStart();
        if (firstTime) {
            currentPage = 1;
            loadData(currentPage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_ARRAY_LIST)){
            arrayListMovie = savedInstanceState.getParcelableArrayList(BUNDLE_ARRAY_LIST);
            firstTime = false;
        } else {
            arrayListMovie = new ArrayList<>();
            firstTime = true;
        }

        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        // on récupère le recyclerVIew
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        if (recyclerView != null){
            recyclerView.setHasFixedSize(true);
        }

        int nb_column;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            nb_column = NB_COLUMN_PORT;
        }else{
            nb_column = NB_COLUMN_LAND;
        }

        // on utilise un GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), nb_column);
        recyclerView.setLayoutManager(gridLayoutManager);

        // on créé un MovieAdapter
        movieAdapter = new MovieAdapter(getActivity(), arrayListMovie);
        recyclerView.setAdapter(movieAdapter);

        // On créé un listener
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                loadData(currentPage);
            }
        });

        setHasOptionsMenu(true);

        return fragmentView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings){
            Intent intent = new Intent();
            intent.setClass(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_ARRAY_LIST, arrayListMovie);
        super.onSaveInstanceState(outState);
    }

    private Callback<ResultListMovie> callback = new Callback<ResultListMovie>() {

        @Override
        public void onResponse(Call<ResultListMovie> call, Response<ResultListMovie> response) {
            try{
                ArrayList<Movie> p_arrayList = response.body().getResults();
                if (p_arrayList != null) {
                    for (Movie movie : p_arrayList) {
                        arrayListMovie.add(movie);
                        if (!recyclerView.isComputingLayout()) {
                            movieAdapter.notifyItemInserted(arrayListMovie.indexOf(movie));
                        }
                    }
                }
            } catch (Exception e){
                Log.d("OnResponse","Big trouble in little china");
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResultListMovie> call, Throwable t) {
            Log.d("onFailure", t.toString());
        }
    };

    private void loadData(int p_currentPage) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.MOVIE_DB_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        MovieDbAPI movieDbAPI = retrofit.create(MovieDbAPI.class);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Call<ResultListMovie> callbackApi = null;
        switch (prefs.getString(getString(R.string.pref_key_list_sort), getString(R.string.pref_popular_movies_title))){
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