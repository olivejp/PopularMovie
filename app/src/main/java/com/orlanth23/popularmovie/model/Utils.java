package com.orlanth23.popularmovie.model;

import android.content.ContentValues;

import com.orlanth23.popularmovie.data.MovieContract;

public class Utils {
    public static ContentValues transformMovieToContentValues(Movie movie){
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry._ID, movie.getId());
        cv.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
        cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
        cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginal_title());
        cv.put(MovieContract.MovieEntry.COLUMN_IMDB_ID, movie.getImdb_id());
        return cv;
    }
}
