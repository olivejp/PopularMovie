package com.orlanth23.popularmovie.retrofitservice;


import com.orlanth23.popularmovie.model.ResultListMovie;
import com.orlanth23.popularmovie.model.ResultListReview;
import com.orlanth23.popularmovie.model.ResultListTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbAPI {
    @GET("movie/popular")
    Call<ResultListMovie> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/top_rated")
    Call<ResultListMovie> getTopRated(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/{movie_id}/videos")
    Call<ResultListTrailers> getTrailers(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ResultListReview> getReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key,  @Query("page") int page);
}
