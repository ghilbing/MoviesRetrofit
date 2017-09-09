package com.example.codepath.moviesretrofit.rest;

import retrofit.Callback;


import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by jose on 10/6/15.
 */


public interface MoviesApiService {
    @GET("/movie/popular")
    void getMovies(@Query("api_key") String apiKey, Callback<MovieResponse> cb);

    @GET("/movie/top_rated")
    void getRatedMovies(@Query("api_key") String apiKey, Callback<MovieResponse> cb);

    @GET("/movie/{id}/videos")
    void getVideos(@Path("id") int movieId, @Query("api_key") String apiKey, Callback<VideoResponse> cb);

    @GET("/movie/{id}/reviews")
    void fetchReviews(@Path("id") int movieId, @Query("api_key") String apiKey, Callback<ReviewResponse> cb);

}