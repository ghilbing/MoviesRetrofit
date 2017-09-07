package com.example.codepath.moviesretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.codepath.moviesretrofit.R;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/6/17.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder{

    Context context;

    @Bind(R.id.vh2_view_play_button)
    ImageButton vh2_view_play_button;
    @Bind(R.id.vh2_player)
    YouTubePlayerView vh2_player;

    public ViewHolder2(View itemView) {
        super(itemView);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_viewholder2,null);
        ButterKnife.bind(this, v);

    }

    public ImageButton getVh2_view_play_button() {
        return vh2_view_play_button;
    }

    public void setVh2_view_play_button(ImageButton vh2_view_play_button) {
        this.vh2_view_play_button = vh2_view_play_button;
    }

    public YouTubePlayerView getVh2_player() {
        return vh2_player;
    }

    public void setVh2_player(YouTubePlayerView vh2_player) {
        this.vh2_player = vh2_player;
    }
}
