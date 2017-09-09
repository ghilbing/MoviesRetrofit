package com.example.codepath.moviesretrofit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.adapter.MoviesAdapter;
import com.example.codepath.moviesretrofit.fragments.MovieFragment;
import com.example.codepath.moviesretrofit.rest.Movie;
//import com.example.codepath.moviesretrofit.rest.ErrorApi;
//import com.example.codepath.moviesretrofit.rest.MoviesApiService;
import com.example.codepath.moviesretrofit.rest.MoviesApiService;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements MovieFragment.OnMovieSelectedListener {

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String TAG_MOVIE_FRAGMENT = "fragment_movies";
    public static final String EXTRA_MOVIE = "movie";

    private Movie mMovieSel;

    @Nullable
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    boolean mTwoPaneMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            ButterKnife.bind(this);
        } catch (Exception e){
            e.printStackTrace();
        }




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        if (savedInstanceState != null) {
            mMovieSel = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }

        if(findViewById(R.id.content_split)!= null){
            mTwoPaneMode = true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovieSel);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (mMovieSel != null && findViewById(R.id.movie_detail_title) != null){
            onMovieSelected(mMovieSel, false, null);
        }
    }

    @Override
    public void onMovieSelected(Movie selection, boolean onClick, View view) {
        mMovieSel = selection;


        if (mTwoPaneMode) {
            MovieFragment fragment = (MovieFragment)
                    getSupportFragmentManager().findFragmentById(R.id.recycler_movies_fragment);

            if (fragment != null & selection == null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment).commit();
            } else if (fragment == null || fragment.mMovie.getId() != mMovieSel.getId()) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieFragment.EXTRA_MOVIE, selection);
                fragment = MovieFragment.newInstance(selection);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (view != null) {
                    Log.d(LOG_TAG, "Fragment with transition??");
                    transaction.addSharedElement(view, getResources().getString(R.string.transition_poster));
                }
                transaction.replace(R.id.fragment_movies_land, fragment, TAG_MOVIE_FRAGMENT).commit();

            }

            String title = selection == null ? "" : selection.getTitle();
            TextView titleView = (TextView) findViewById(R.id.movie_detail_title);
            titleView.setText(title);
            mFab.show();

        } else if (onClick) {
            onMovieClicked(selection, true, view);
//            Intent intent = new Intent(this, MovieDetailActivity.class);
//            intent.putExtra(MovieDetailActivity.KEY_MOVIE, movie);
//            this.startActivity(intent);
        }
    }

    public void onMovieClicked(Movie movie, boolean onClick, View srcView) {
        Log.d(LOG_TAG, "Start Activity with transition??");
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, srcView, getResources().getString(R.string.transition_poster));
        ActivityCompat.startActivity(this, intent, options.toBundle());
        //this.startActivity(intent, options);
    }

    }

