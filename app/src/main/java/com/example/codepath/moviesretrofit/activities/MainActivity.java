package com.example.codepath.moviesretrofit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.codepath.moviesretrofit.Config;
import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.adapter.MovieLayoutsAdapter;
import com.example.codepath.moviesretrofit.adapter.MoviesAdapter;
import com.example.codepath.moviesretrofit.data.Movie;
import com.example.codepath.moviesretrofit.data.Video;
import com.example.codepath.moviesretrofit.rest.ErrorApi;
import com.example.codepath.moviesretrofit.rest.MovieResponse;
import com.example.codepath.moviesretrofit.rest.MoviesApiService;
import com.example.codepath.moviesretrofit.rest.VideoResponse;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MainActivity";

    private MoviesAdapter mAdapter;

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayerView;

    @Nullable
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //private MovieLayoutsAdapter mAdapter;

    static final String EXTRA_MOVIES = "movies";
    OnMovieSelectedListener listener;



    public interface OnMovieSelectedListener {
        public void onMovieSelected(Movie selection, boolean onClick, View view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mAdapter = new MovieLayoutsAdapter(this);
        mAdapter = new MoviesAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        getPopularMovies();

        //youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        //youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, this);

    }

    /*@Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo("fhWaJilHsfo");
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYoutubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

        protected YouTubePlayer.Provider getYoutubePlayerProvider(){
        return youTubePlayerView;
    }

*/


    private void getPopularMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "c0ce143817426b86ae199a8ede8d8775");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
/*
    private void getVideos(){
        RestAdapter restAdapter = new RestAdapter().Builder()
                .sendEndpoint("http://api.themoviedb.org/3")

        MoviesApiService service1 = restAdapter.create(MoviesApiService.class);
        service1.getVideos(new Callback<Video.VideoResult>() {
            @Override
            public void success(Video.VideoResult videoResult, Response response) {
               Log.i(LOG_TAG, String.valueOf(response));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(LOG_TAG, "Falla");

            }
        });
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}