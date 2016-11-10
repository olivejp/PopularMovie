package com.orlanth23.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String backdrop_path;
    private int budget;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private int revenue;
    private String tagline;
    private double vote_average;
    private int vote_count;

    public Movie() {
    }

    public Movie(String backdrop_path, int budget, String homepage, int id, String imdb_id, String original_title, String overview, double popularity, String poster_path, String release_date, int revenue, String tagline, double vote_average, int vote_count) {
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.revenue = revenue;
        this.tagline = tagline;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    private Movie(Parcel in) {
        backdrop_path = in.readString();
        budget = in.readInt();
        homepage = in.readString();
        id = in.readInt();
        imdb_id = in.readString();
        original_title = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readInt();
        tagline = in.readString();
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
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
        parcel.writeInt(this.budget);
        parcel.writeString(this.homepage);
        parcel.writeInt(this.id);
        parcel.writeString(this.imdb_id);
        parcel.writeString(this.original_title);
        parcel.writeString(this.overview);
        parcel.writeDouble(this.popularity);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.release_date);
        parcel.writeInt(this.revenue);
        parcel.writeString(this.tagline);
        parcel.writeDouble(this.vote_average);
        parcel.writeInt(this.vote_count);
    }
}
