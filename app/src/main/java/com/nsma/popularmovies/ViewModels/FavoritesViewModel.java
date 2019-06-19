package com.nsma.popularmovies.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.nsma.popularmovies.Database.AppDatabase;
import com.nsma.popularmovies.Models.Movie;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = FavoritesViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public FavoritesViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = database.taskDao().loadAllFavorates();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
