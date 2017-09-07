package com.example.codepath.moviesretrofit.rest;

import com.example.codepath.moviesretrofit.data.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gretel on 9/6/17.
 */

public class MovieResponse {

    @SerializedName("page")
    int page;

    @SerializedName("results")
    List<Movie> movies;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("total_results")
    int totalResults;

    public int getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
