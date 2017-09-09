package com.example.codepath.moviesretrofit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.fragments.VideoReviewFragment;
import com.example.codepath.moviesretrofit.rest.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.os.Build.VERSION_CODES.M;

public class MovieDetailActivity extends AppCompatActivity implements VideoReviewFragment.OnFragmentInteractionListener {

    static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    public static final String EXTRA_MOVIE = "movie";

    public Movie mMovie;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.backdrop)
    ImageView backdrop;

    @Bind(R.id.detail_poster)
    ImageView poster;

    @Bind(R.id.detail_title)
    TextView title;

    @Bind(R.id.detail_description)
    TextView description;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        try{
            ButterKnife.bind(this);
        }catch (Exception e){
            e.printStackTrace();
        }

       // Intent intent = getIntent();
     //   mMovie = intent.getParcelableExtra(EXTRA_MOVIE);

        barTransparent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, mMovie);

        if (savedInstanceState == null){
            Fragment fragment = VideoReviewFragment.newInstance(mMovie);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recycler_container_fragment, fragment).commit();
        }


        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }


        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void barTransparent() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

