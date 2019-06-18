package com.nsma.popularmovies.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nsma.popularmovies.Models.Movie;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM movies ORDER BY id")
    ArrayList<Movie> loadAllFavorates();

    @Insert
    void insertMovie(Movie movieEntry);

    @Delete
    void deleteMovie(Movie taskEntry);


}
