package com.example.codepath.moviesretrofit.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codepath.moviesretrofit.MovieApiDB;
import com.example.codepath.moviesretrofit.Prefs;
import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.activities.Sort;
import com.example.codepath.moviesretrofit.rest.ErrorApi;
import com.example.codepath.moviesretrofit.rest.Movie;
import com.example.codepath.moviesretrofit.rest.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.rating;

/**
 * Created by gretel on 9/8/17.
 */

public class MovieFragment extends Fragment implements MovieApiDB.MovieListener {

    static final String TAG = MovieFragment.class.getSimpleName();

    public static final String EXTRA_MOVIE = "movies";

    public Movie mMovie;

    int mSorting = Sort.POP;

    @Bind(R.id.recycler_movies_fragment)
    RecyclerView mRecyclerView;

    MovieApiDB mApiDB;
    MovieFragmentAdapter mAdapter;
    RecyclerScrollListener mScrollListener;

    // communicates selection events back to listener
    OnMovieSelectedListener mListener;

    // interface to communicate movie selection events to MainActivity
    public interface OnMovieSelectedListener {
        //public void onMovieSelected(Movie selection, boolean onClick);
        public void onMovieSelected(Movie selection, boolean onClick, View view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, null);
        ButterKnife.bind(this, view);

        ArrayList<Movie> movies = new ArrayList<>();

        if(savedInstanceState != null){
            mSorting = Prefs.getCurrentSortMethod(getActivity());
            movies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIE);
        } else {
            mSorting = Prefs.getPreferredSortMethod(getActivity());
        }

        mAdapter = new MovieFragmentAdapter();
        mScrollListener = new RecyclerScrollListener();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(movies);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addOnScrollListener(mScrollListener);

        // initialize api
        mApiDB = MovieApiDB.getInstance(getString(R.string.api_key));

        // request movies
        if (mAdapter.getItemCount() == 0) {

            if(mSorting == Sort.POP){
                mApiDB.requestPopularMovies(this);
            } else {

                mApiDB.requestRatedMovies(this);

            }
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_MOVIE, mAdapter.data);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }


    @Override
    public void success(MovieResponse response) {

        if (response.getPage() == 1) {
            // initialize with results for first page
            int pageMax = (response.getTotalPages() < 25) ? response.getTotalPages() : 25;
            mScrollListener.totalPages = pageMax;
            mAdapter.setData(response.getMovies(), 20, pageMax);
        } else {
            // append results for subsequent pages
            mAdapter.appendData(response.getMovies());
        }
    }


    @Override
    public void error(ErrorApi error) {
        if (error.isNetworkError()) {
            Toast.makeText(getActivity(), "Unable to connect to remote host", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, error.toString());
    }

    class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.MovieCardViewHolder> {

        ArrayList<Movie> data = new ArrayList<>();

        int maxPages = -1;
        int pageSize = 20;

        public void setData(List<Movie> data, int pageSize, int maxPages) {
            this.maxPages = maxPages;
            this.pageSize = pageSize;
            setData(data);
        }

        public void setData(List<Movie> data) {
            this.data.clear();
            this.data.addAll(data);
            this.notifyDataSetChanged();
            notifyMovieSelectionListener();
        }
        public void appendData(List<Movie> movies) {
            this.data.addAll(movies);
            this.notifyDataSetChanged();
        }

        public void appendData(Movie movie) {
            this.data.add(movie);
            this.notifyItemChanged(this.data.size() - 1);
            if (this.data.size() == 1) {
                notifyMovieSelectionListener();
            }
        }

        public void removeData(Movie movie) {
            int index = this.data.indexOf(movie);
            if (index != -1)
                this.data.remove(index);

            this.notifyItemRemoved(index);
            notifyMovieSelectionListener();
        }

        public void clearData() {
            this.maxPages = -1;
            this.data.clear();
            this.notifyDataSetChanged();
        }


        public void notifyMovieSelectionListener() {
            if (mListener != null && !data.isEmpty()) {
                View view = mRecyclerView.getChildAt(0);
                Log.d(TAG, "Found child view: " + view);
                View posterView = null;
                if (view != null) {
                    posterView = view.findViewById(R.id.detail_poster);
                    Log.d(TAG, "Found poster view: " + posterView);
                }
                mListener.onMovieSelected(data.get(0), false, posterView);

            }
        }

        @Override
        public MovieCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_movie, parent, false);
            return new MovieCardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieFragmentAdapter.MovieCardViewHolder holder, int position) {
            if (position >= data.size()) {
                holder.title.setText("");
                holder.poster.setImageResource(R.drawable.ic_aspect_ratio_black_24dp);
                return;
            }



            // display movie details
            Movie movie = data.get(position);
            holder.title.setText(movie.getTitle());
            String va = String.valueOf(movie.getVoteAverage());

            holder.ratingBar.setRating(Float.valueOf(va));

            Picasso.with(holder.poster.getContext())
                    .load(movie.getPoster())
                    .placeholder(R.drawable.ic_local_movies_black_24dp)
                    .error(R.drawable.ic_local_movies_black_24dp)
                    .into(holder.poster);
        }



        @Override
        public int getItemCount() {
            if (maxPages == -1) {
                return  data.size();
            } else {
                return maxPages * pageSize;
            }
        }

        class MovieCardViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_item_Title)
            TextView title;

            @Bind(R.id.item_ratingBar)
            RatingBar ratingBar;

            @Nullable
            @Bind(R.id.iv_item_poster)
            ImageView poster;

            public MovieCardViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.iv_item_poster)
            public void onClick() {
                int adapterPosition = this.getAdapterPosition();
                if (adapterPosition < data.size()) {
                    Movie movie = data.get(adapterPosition);
                    if (mListener != null) {
                        mListener.onMovieSelected(movie, true, poster);
                    }
                }
            }
        }
    }

    class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        int currentPage;
        int totalPages;
        int previousTotal;
        int visibleThreshold;
        boolean loading;

        public void init() {
            currentPage = 1;
            totalPages = 1;
            previousTotal = 0;
            visibleThreshold = 5;
            loading = false;
        }

        public RecyclerScrollListener() {
            super();
            init();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = recyclerView.getChildCount();
            //int totalItemCount = gridLayoutManager.getItemCount();
            int totalItemCount = mAdapter.data.size();
            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

            // load finished
            if (loading && totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }

        }
    }

}
