package com.example.codepath.moviesretrofit;

import com.example.codepath.moviesretrofit.data.Movie;
import com.example.codepath.moviesretrofit.rest.VideoResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MovieApiDB {
    @GET("/movie/popular")
    void getMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/{id}/videos")
    void getVideos(@Path("id") int movieId, @Query("api_key") String apiKey, Callback<VideoResponse> cb);

}