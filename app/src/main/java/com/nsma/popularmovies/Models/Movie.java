package com.nsma.popularmovies.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {


    @PrimaryKey(autoGenerate = true)
    int id;
    private int voteCount ;
    private Double voteAVG ;
    private Double popularity ;
    private String title;
    private String posterPath;
    private String overview;
    private String release_date;

    public Movie() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Movie(int voteCount, Double voteAVG, Double popularity, String title, String posterPath, String overview, String release_date) {
        this.voteCount = voteCount;
        this.voteAVG = voteAVG;
        this.popularity = popularity;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAVG() {
        return voteAVG;
    }

    public void setVoteAVG(Double voteAVG) {
        this.voteAVG = voteAVG;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "voteCount=" + voteCount +
                ", voteAVG=" + voteAVG +
                ", popularity=" + popularity +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
