package com.orlanth23.popularmovie.model;


import java.util.ArrayList;

public class ResultListTrailers {
    private int id;
    private ArrayList<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
