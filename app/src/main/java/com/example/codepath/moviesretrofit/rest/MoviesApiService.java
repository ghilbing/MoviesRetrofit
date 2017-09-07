package com.example.codepath.moviesretrofit.rest;

import android.telecom.Call;

import com.example.codepath.moviesretrofit.data.Movie;
import com.example.codepath.moviesretrofit.data.Video;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by jose on 10/6/15.
 */
public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/popular")
    void fetchPopularMovies(@Query("api_key") String apiKey, @Query("page") int page, Callback<MovieResponse.MovieResult> cb);

    @GET("/movie/{id}/videos")
    void getVideos(@Path("id") int movieId, Callback<VideoResponse> cb);

    void getVideos(Callback<Video.VideoResult> cb);
}