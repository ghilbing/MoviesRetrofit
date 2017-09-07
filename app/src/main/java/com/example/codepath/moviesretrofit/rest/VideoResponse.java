package com.example.codepath.moviesretrofit.rest;

import com.example.codepath.moviesretrofit.data.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gretel on 9/6/17.
 */

public class VideoResponse {
    @SerializedName("id")
    int id;

    @SerializedName("results")
    List<Video> videos;

    public int getId() {
        return id;
    }

    public List<Video> getVideos() {
        if (videos == null) {
            return Collections.EMPTY_LIST;
        }
        return videos;
    }

    public List<Video> getYoutubeTrailers() {
        List<Video> youtubeTrailers = new ArrayList<>();
        for (Video video : videos) {
            if (video.isTrailer()) {
                youtubeTrailers.add(video);
            }
        }
        return youtubeTrailers;
    }
}
