package com.nsma.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Models.Review;
import com.nsma.popularmovies.Models.Trailer;

@Database(entities = {Movie.class }, version = 1, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {

        private static final String LOG_TAG = AppDatabase.class.getSimpleName();
        private static final Object LOCK = new Object();
        private static final String DATABASE_NAME = "popularMovies";
        private static AppDatabase sInstance;

        public static AppDatabase getInstance(Context context) {
            if (sInstance == null) {
                synchronized (LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            .build();
                }
            }
            Log.d(LOG_TAG, "Getting the database instance");
            return sInstance;
        }

        public abstract TaskDao taskDao();

    }
