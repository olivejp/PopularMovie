package com.orlanth23.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String backdrop_path;
    private int id;
    private String imdb_id;
    private String original_title;
    private String overview;
    private String poster_path;
    private String release_date;
    private double vote_average;

    public Movie(){
        // Empty constructor
    }

    public Movie(String backdrop_path, int id, String imdb_id, String original_title, String overview, String poster_path, String release_date, double vote_average) {
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.imdb_id = imdb_id;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    private Movie(Parcel in) {
        backdrop_path = in.readString();
        id = in.readInt();
        imdb_id = in.readString();
        original_title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }


    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }


    public double getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.backdrop_path);
        parcel.writeInt(this.id);
        parcel.writeString(this.imdb_id);
        parcel.writeString(this.original_title);
        parcel.writeString(this.overview);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.release_date);
        parcel.writeDouble(this.vote_average);
    }
}
