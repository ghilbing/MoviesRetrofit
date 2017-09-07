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
import com.example.codepath.moviesretrofit.data.Movie;
import com.example.codepath.moviesretrofit.data.Video;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/6/17.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MovieViewHolder> {
    private List<Video> mVideoList;
    private LayoutInflater mInflater;
    private Context mContext;


    public VideosAdapter(Context context) {
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
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mVideoList.get(position));
                mContext.startActivity(intent);


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Video video = mVideoList.get(position);




    }

    @Override
    public int getItemCount() {
        return (mVideoList == null) ? 0 : mVideoList.size();
    }

    public void setMovieList(List<Video> videoList) {
        this.mVideoList = new ArrayList<>();
        this.mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {


        public YouTubePlayerView player;

        public MovieViewHolder(View itemView) {
            super(itemView);

            player = (YouTubePlayerView) itemView.findViewById(R.id.player);


        }
    }

}
