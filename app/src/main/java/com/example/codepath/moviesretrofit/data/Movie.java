package com.example.codepath.moviesretrofit.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Movie implements Parcelable{
    @SerializedName("id")
    int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("popularity")
    float popularity;
    @SerializedName("video")
    boolean isVideo;
    @SerializedName("vote_average")
    float voteAverage;


    static final String IMAGE_PATH = "http://image.tmdb.org/t/p/";

    static final String SIZE_DEFAULT = "w500";

    public Movie() {}

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
        voteAverage = in.readFloat();
        popularity = in.readFloat();
        isVideo = in.readByte() !=0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return IMAGE_PATH + SIZE_DEFAULT + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return IMAGE_PATH + SIZE_DEFAULT  + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVoteAverage() {return voteAverage;}

    public void setVoteAverage(Float voteAverage){this.voteAverage = voteAverage;}

    public boolean isVideo() {return isVideo;}

    public void setVideo(Boolean isVideo) {this.isVideo = isVideo;}

    public MovieType type;

    public enum MovieType{
        Unknown, voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeFloat(popularity);
        parcel.writeFloat(voteAverage);
        parcel.writeByte(isVideo ? (byte) 1 : (byte) 0);
    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
