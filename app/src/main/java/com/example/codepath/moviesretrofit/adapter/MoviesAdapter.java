package com.example.codepath.moviesretrofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.activities.MovieDetailActivity;
import com.example.codepath.moviesretrofit.rest.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/6/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;


    public MoviesAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                mContext.startActivity(intent);


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        String va = String.valueOf(movie.getVoteAverage());
        //String pop = String.valueOf(movie.getPopularity());

        //TextView voteAverage = holder.voteAverage;
       // voteAverage.setText(va);

       // TextView popularity = holder.popularity;
       // popularity.setText(pop);

        holder.title.setText(movie.getTitle());


        RatingBar ratingBar = holder.ratingBar;
        ratingBar.setRating(Float.valueOf(va));



        Picasso.with(mContext)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_local_movies_black_24dp)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.mMovieList = new ArrayList<>();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_VoteAverage)
        TextView voteAverage;

        @Bind(R.id.iv_item_poster)
        ImageView imageView;

        @Bind(R.id.tv_item_Title)
        TextView title;

        @Bind(R.id.item_ratingBar)
        RatingBar ratingBar;


        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
