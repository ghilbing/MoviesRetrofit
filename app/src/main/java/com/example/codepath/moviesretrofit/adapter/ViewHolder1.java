package com.example.codepath.moviesretrofit.adapter;

import android.content.ContentProvider;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codepath.moviesretrofit.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/6/17.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder{

    Context context;

    @Bind(R.id.vh1_imageView)
    ImageView vh1_imageView;

    @Bind(R.id.vh1_tvVoteAverage)
    TextView vh1_tvVoteAverage;

    public ViewHolder1(View itemView) {
        super(itemView);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_viewholder1,null);
        ButterKnife.bind(this, v);

    }

    public ImageView getVh1_imageView() {
        return vh1_imageView;
    }

    public void setVh1_imageView(ImageView vh1_imageView) {
        this.vh1_imageView = vh1_imageView;
    }

    public TextView getVh1_tvVoteAverage() {
        return vh1_tvVoteAverage;
    }

    public void setVh1_tvVoteAverage(TextView vh1_tvVoteAverage) {
        this.vh1_tvVoteAverage = vh1_tvVoteAverage;
    }
}
