package com.orlanth23.popularmovie.retrofitservice;


import com.orlanth23.popularmovie.model.ResultListMovie;
import com.orlanth23.popularmovie.model.ResultListReview;
import com.orlanth23.popularmovie.model.ResultListVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbAPI {
    @GET("movie/popular")
    Call<ResultListMovie> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/top_rated")
    Call<ResultListMovie> getTopRated(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/{id}/videos")
    Call<ResultListVideos> getVideos(@Query("api_key") String api_key, @Path("movie_id") int movie_id);

    @GET("movie/{id}/reviews")
    Call<ResultListReview> getReviews(@Query("api_key") String api_key, @Path("movie_id") int movie_id);
}
