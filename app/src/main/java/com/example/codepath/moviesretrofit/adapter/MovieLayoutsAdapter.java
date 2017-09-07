package com.example.codepath.moviesretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.R;
import com.example.codepath.moviesretrofit.activities.MainActivity;
import com.example.codepath.moviesretrofit.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gretel on 9/6/17.
 */

public class MovieLayoutsAdapter extends RecyclerView.Adapter<MovieLayoutsAdapter.CustomViewHolder>{

    private List<Movie> movies;
    Context context;
    private final int FAV = 2, NOFAV = 1;

    public MovieLayoutsAdapter(MainActivity mainActivity) {
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View viewONE = LayoutInflater.from(context).inflate(R.layout.layout_viewholder1, parent, false);
                RecyclerView.ViewHolder rowONE = new ViewHolder1(viewONE);
                return (CustomViewHolder) rowONE;
            case 2:
                View viewTWO = LayoutInflater.from(context).inflate(R.layout.layout_viewholder2, parent, false);
                RecyclerView.ViewHolder rowTWO = new ViewHolder2(viewTWO);
                return (CustomViewHolder) rowTWO;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String va = String.valueOf(movie.getVoteAverage());

        TextView voteAverage = holder.voteAverage;
        voteAverage.setText(va);


        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_local_movies_black_24dp)
                .into(holder.imageView);

    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getVoteAverage() > 5){
            return FAV;
        } else
            return NOFAV;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movies = new ArrayList<>();
        this.movies.addAll(movieList);
        notifyDataSetChanged();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView voteAverage;

        public CustomViewHolder(View itemView) {
            super(itemView);

            voteAverage = (TextView) itemView.findViewById(R.id.tvVoteAverage);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
